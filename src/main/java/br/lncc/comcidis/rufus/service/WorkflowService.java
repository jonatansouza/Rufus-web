/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lncc.comcidis.rufus.service;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.environment.Property;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.lncc.comcidis.rufus.model.Cells;
import br.lncc.comcidis.rufus.model.FileModel;
import br.lncc.comcidis.rufus.model.LxcInput;
import br.lncc.comcidis.rufus.model.UserSession;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    @Inject
    UserSession userSession;

    @Inject
    Result result;

    @Inject
    Validator validator;

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
        logger.info(containers.size() + " tamanho da lista");
        int qtdSteps = 0;
        for (LxcInput container : containers) {
            if (qtdSteps < container.getStep()) {
                qtdSteps = container.getStep();
            }
        }
        int countContainers = 0;
        for (int i = 1; i <= qtdSteps; i++) {
            for (LxcInput container : containers) {

                if (container.isContainer()) {
                    if (container.getStep() == i) {
                        linksSourceInput = Cells.getLinkByTarget(container.getId(), links);

                        for (LxcInput lxcInput : inputs) {
                            for (String sourceId : linksSourceInput) {
                                if (lxcInput.getId().equals(sourceId)) {
                                    listInputs.add(lxcInput.getActivity());
                                }
                            }
                        }

                        executeWorkflow(user, container.getId(), workflow, listInputs, container.getName());
                        countContainers++;
                        listInputs.clear();
                    }
                }
            }
        }

    }

    public void executeWorkflow(String user, String container, String workflow, List<String> inputs, String containerName) {
        logger.info("enviando requisicao");
        httpClient = HttpClients.createDefault();

        String jsonFile = new Gson().toJson(inputs);
        String order = "{\"username\":\"" + user + "\",\"workflow_id\":\"" + workflow + "\" ,\"app_id\":\"" + container + "\", \"args\":" + jsonFile + "}";

        HttpPost hp = new HttpPost("http://" + ip + ":" + port + "/" + version + "/containers/" + containerName + "/run");
        StringEntity st = new StringEntity(order, "utf-8");
        st.setContentType("application/json");
        hp.setEntity(st);
        String json = "";

        HttpResponse answer;
        try {
            answer = httpClient.execute(hp);
            String testStatus = answer.getStatusLine().getStatusCode() + "";

            BufferedReader br = new BufferedReader(new InputStreamReader((answer.getEntity().getContent())));
            String output = "";
            while ((output = br.readLine()) != null) {
                json += output;
            }
            logger.info(json);
            validator.addIf(testStatus.equals("500"), new SimpleMessage("message", "<h4 class='text-danger'>Server error</h4><p><strong>Reason: </strong>" + json + "</p>"));
            validator.onErrorSendBadRequest();

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
    public List<File> getAllWorkflowResults() {

        File raiz = new File(pathNfsDirectory + "/" + userSession.currentUser().getEmail());
        FilenameFilter filter = new FileFileFilter() {
            public boolean accept(File dir, String name) {
                String lowercaseName = name.toLowerCase();
                if (!lowercaseName.contains("files")) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        List<File> list = new ArrayList<>();

        if (!raiz.exists()) {
            raiz.mkdir();
        }

        for (File file : raiz.listFiles(filter)) {
            list.add(file);
        }

        return list;
    }

    public List<File> getFilesFromWorkflow(String folder) {

        File raiz = new File(pathNfsDirectory + "/" + userSession.currentUser().getEmail() + "/" + folder);

        List<File> list = new ArrayList<>();

        if (!raiz.exists()) {
            raiz.mkdir();
        }
        FilenameFilter filter = new FileFileFilter() {
            public boolean accept(File dir, String name) {
                String lowercaseName = name.toLowerCase();
                if (!lowercaseName.startsWith(".")) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        for (File file : raiz.listFiles(filter)) {
            list.add(file);
        }

        return list;
    }

    public void saveJSON(String jsonWorkflow, String workflow) {
        String path = "" + pathNfsDirectory + "/" + userSession.currentUser().getEmail() + "/" + workflow + "/." + workflow;
        logger.info(path + " path do file");
        File raiz = new File(path);

        try {
            if (!raiz.exists()) {
                raiz.createNewFile();
            }
            FileWriter fw;
            fw = new FileWriter(raiz);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(jsonWorkflow);
            bw.close();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(WorkflowService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void saveXML(String xmlWorkflow, String workflow) {
        String path = "" + pathNfsDirectory + "/" + userSession.currentUser().getEmail() + "/" + workflow + "/" + workflow + ".xml";
        logger.info(path + " path do file");
        File raiz = new File(path);

        try {
            if (!raiz.exists()) {
                raiz.createNewFile();
            }
            FileWriter fw;
            fw = new FileWriter(raiz);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(xmlWorkflow);
            bw.close();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(WorkflowService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String loadWorkflow(String workflow) {
        String path = "" + pathNfsDirectory + "/" + userSession.currentUser().getEmail() + "/" + workflow + "/." + workflow;
        File file = new File(path);
        FileReader fr;
        String result = "";
        String flux = "";
        try {
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            result = br.readLine();
            flux = br.readLine();
            while (flux != null) {
                result += br.readLine() + "";
                flux = br.readLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(WorkflowService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public List<FileModel> listWorkflowsToLoad() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        File raiz = new File(pathNfsDirectory + "/" + userSession.currentUser().getEmail());
        FilenameFilter filter = new FileFileFilter() {
            public boolean accept(File dir, String name) {
                String lowercaseName = name.toLowerCase();
                if (!lowercaseName.equals("files")) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        List<FileModel> list = new ArrayList<>();

        if (!raiz.exists()) {
            raiz.mkdir();
        }

        for (File file : raiz.listFiles(filter)) {
            FileModel fm = new FileModel();
            fm.setName(file.getName());
            fm.setDate(sdf.format(file.lastModified()));
            list.add(fm);
        }

        return list;
    }

}
