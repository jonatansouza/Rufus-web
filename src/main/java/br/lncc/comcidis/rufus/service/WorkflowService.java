/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lncc.comcidis.rufus.service;

import br.com.caelum.vraptor.environment.Property;
import br.lncc.comcidis.rufus.controller.RufusController;
import br.lncc.comcidis.rufus.model.Cells;
import br.lncc.comcidis.rufus.model.LxcInput;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.compiler.TokenId;
import javax.inject.Inject;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jonatan
 */
public class WorkflowService {

    @Inject
    @Property
    private String ip;

    @Inject
    @Property
    private String port;

    @Inject
    @Property
    private String version;

    @Inject
    @Property
    private String pathNfsDirectory;

    private HttpClient httpClient;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(WorkflowService.class);

    @Deprecated
    public WorkflowService() {

    }

 

    public String workflowValidate(Cells cells) {
        //check if links exists
        if (cells.getLinks().isEmpty()) {
            return "<div class='text-center'>\n"
                    + "<h4>No association have been found</h4>\n"
                    + "<p><strong>Tip: </strong>Click on name of input or file and than drag<br>until the desired component.</p>\n"
                    + "<img src='/rufus/assets/images/linkTip.png' style='height:300px; width:300px'>\n"
                    + "</div>";
        }

        
        //check if input are pointed to other input or file
        boolean inputTarget = false;
        for (LxcInput link : cells.getLinks()) {
            for (LxcInput input : cells.getInputs()) {
                if (link.getTarget().getId().equals(input.getId())) {
                    inputTarget = true;
                }
            }
        }

        if (inputTarget) {
            return "<div class='text-center'>\n"
                    + "<h4>A file or input could not be associated<br>to other file or input</h4>\n"
                    + "<p><strong>Tip: </strong>Select an <span class='text-warning'>ACTION</span> on side-menu to associate<br>your inputs or files.</p>\n"
                    + "<img src='/rufus/assets/images/linkTip.png' style='height:300px; width:300px'>\n"
                    + "</div>";
        }

        
        //check if exists one container at least
        

        if (cells.getContainers().isEmpty()) {
            return "<div class='text-center'>\n"
                    + "<h4>No containers have been found</h4>\n"
                    + "<p><strong>Tip: </strong>make sure to insert a blast or other container on workflow at least.</p>\n"
                    + "</div>";
        }

        return "";

    }

    /**
     * organiza os nivels para serem executados
     *
     * @param cells
     */
    public List<LxcInput> organizeToRun(Cells cells) {
        List<LxcInput> containers = cells.getContainers();

        int sourceIndex = 0;
        int targetIndex = 0;
        LxcInput source = null;
        LxcInput target = null;
        boolean sourceExist = false;

        for (LxcInput lxcLink : cells.getLinks()) {
            if (cells.getContainersById(lxcLink.getSource().getId()) != null) {
                source = cells.getContainersById(lxcLink.getSource().getId());
                cells.getContainersById(lxcLink.getSource().getId());
                sourceExist = true;
            }

            target = cells.getContainersById(lxcLink.getTarget().getId());
            targetIndex = containers.indexOf(target);

            if (containers.get(targetIndex).getStep() <= containers.get(sourceIndex).getStep() && sourceExist) {
                containers.get(targetIndex).setStep(containers.get(sourceIndex).getStep() + 1);
                sourceExist = false;
            }
        }
        return containers;
    }

    
    public void saveFilesOnDirectory(List<LxcInput> containers, String resultId) {
        File workflow = new File("/var/rufus/users/jonatan/" + resultId);
        workflow.mkdir();
        File directoryFile = new File("/var/rufus/users/jonatan/files/");
        directoryFile.mkdir();
        for (LxcInput lxcInput : containers) {
            if (lxcInput.getName().equalsIgnoreCase("input")) {
                File input = new File(directoryFile.getPath() + "/" + lxcInput.getId());

                FileWriter fw;
                try {
                    fw = new FileWriter(input);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(lxcInput.getActivity());
                    bw.close();
                } catch (IOException ex) {
                    Logger.getLogger(WorkflowService.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    public void runContainers(List<LxcInput> containers, List<LxcInput> inputs, List<LxcInput> links, String workflow, String user) {
        List<String> listInputs = new ArrayList<>();
        List<String> linksSourceInput = new ArrayList<>();

        int qtdSteps = 0;
        for (LxcInput container : containers) {
            if (qtdSteps < container.getStep()) {
                qtdSteps = container.getStep();
            }
        }
        int countContainers = 0;
        for (int i = 0; i <= qtdSteps; i++) {
            for (LxcInput container : containers) {
                if (container.getStep() == i) {
                    linksSourceInput = Cells.getLinkByTarget(container.getId(), links);
                }
                for (LxcInput lxcInput : inputs) {
                    for (String sourceId : linksSourceInput) {
                        if (lxcInput.getId().equals(sourceId)) {
                            listInputs.add(lxcInput.getActivity());
                        }
                    }
                }
                executeWorkflow(user, container.getName() + "-" + countContainers, workflow, listInputs);
                countContainers++;
            }
        }

    }

    public void executeWorkflow(String user, String container, String workflow, List<String> inputs) {

        httpClient = HttpClients.createDefault();
        for (String in : inputs) {
            logger.info(in + " args");
        }
        String jsonFile = new Gson().toJson(inputs);
        String order = "{\"username\":\"" + user + "\",\"workflow_id\":\"" + workflow + "\" ,\"app_id\":\"" + container + "\", \"args\":" + jsonFile + "}";

        HttpPost hp = new HttpPost("http://" + ip + ":" + port + "/" + version + "/containers/blast/run");
        StringEntity st = new StringEntity(order, "utf-8");
        st.setContentType("application/json");
        hp.setEntity(st);
        String json = "";

        HttpResponse answer;
        try {
            answer = httpClient.execute(hp);
            BufferedReader br = new BufferedReader(new InputStreamReader((answer.getEntity().getContent())));
            String output = "";
            while ((output = br.readLine()) != null) {
                json += output;
            }
            logger.info(json);

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(RufusService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Implementar o template para leitura do resultado do workflow
    /*public File workflowResultRead(String workflow){
     String path = "/var/rufus/users/jonatan/"+workflow;
     String errorFile = "";
     String successFile = "";
     BufferedReader resultWorkflow = new BufferedReader(new FileReader(path));
     resultWorkflow
     }*/
}
