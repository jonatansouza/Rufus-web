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

    /**
     * esta funcao organiza os inputs nos diretorios correnspondentes aos
     * containers
     *
     * @param cells
     * @return
     */
    public List<String> prepareFiles(Cells cells) {

        List<File> app_ids = new ArrayList<>();

        for (LxcInput lxc : cells.getAdditions()) {
            File app_id = new File("/var/rufus/users/jonatan/" + lxc.getName() + "-" + lxc.getId());
            if (!app_id.exists()) {
                new File("/var/rufus/users/jonatan/" + lxc.getName() + "-" + lxc.getId()).mkdirs();
            }
            new File(app_id.getPath() + "/in").mkdirs();
            new File(app_id.getPath() + "/out").mkdirs();

            File stdin = new File(app_id.getPath() + "/in/stdin");
            File stdout = new File(app_id.getPath() + "/out/stdout");
            File stderr = new File(app_id.getPath() + "/out/stderr");
            try {
                stdin.createNewFile();
                stdout.createNewFile();
                stderr.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(WorkflowService.class.getName()).log(Level.SEVERE, null, ex);
            }
            app_ids.add(app_id);
        }
        for (LxcInput app_id : cells.getAdditions()) {
            for (LxcInput lxcInput : cells.getInputs()) {
                for (LxcInput link : cells.getLinks()) {
                    if (link.getSource().getId().equals(lxcInput.getId())
                            && link.getTarget().getId().equals(app_id.getId())) {
                        File input = new File(pathNfsDirectory + "jonatan/"
                                + app_id.getName() + "-" + app_id.getId()
                                + "/in/" + lxcInput.getName() + "-" + lxcInput.getId());
                        FileWriter fw;
                        try {
                            fw = new FileWriter(input);
                            BufferedWriter bw = new BufferedWriter(fw);
                            bw.write(lxcInput.getActivity());
                            bw.close();

                        } catch (IOException ex) {
                            Logger.getLogger(WorkflowService.class
                                    .getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }
            }
        }
        List<String> returnIds = new ArrayList<>();
        for (File app_idName : app_ids) {

            returnIds.add(app_idName.getName());
        }
        return returnIds;
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

        for (LxcInput lxcLink : cells.getLinks()) {
            source = cells.getById(lxcLink.getSource().getId());
            sourceIndex = containers.indexOf(source);
            target = cells.getById(lxcLink.getTarget().getId());
            targetIndex = containers.indexOf(target);

            if (containers.get(targetIndex).getStep() <= containers.get(sourceIndex).getStep()) {
                containers.get(targetIndex).setStep(containers.get(sourceIndex).getStep() + 1);
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

    public void runContainers(List<LxcInput> containers, List<LxcInput> links, String workflow, String user) {
        List<String> inputs = new ArrayList<>();

        
        int qtdSteps = 0;
        for (LxcInput container : containers) {
            if (qtdSteps < container.getStep()) {
                qtdSteps = container.getStep();
            }
        }
        
        for (int i = 0; i < qtdSteps; i++) {
                inputs = Cells.getLinkByTarget(containers.get(i).getId(), links);

            executeWorkflow(user, containers.get(i), workflow, inputs);
        }

    }

    public void executeWorkflow(String user, LxcInput container, String workflow, List<String> inputs) {

        httpClient = HttpClients.createDefault();
        for (String in : inputs) {
            logger.info(in + " args");
        }
        String jsonFile = new Gson().toJson(inputs);
        String order = "{\"username\":\"" + user + "\",\"workflow_id\":\"" + workflow + "\" ,\"app_id\":\"" + container.getId() + "\", \"args\":" + jsonFile + "}";

        HttpPost hp = new HttpPost("http://" + ip + ":" + port + "/" + version + "/containers/addition/run");
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

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(RufusService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
