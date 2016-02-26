/*
 *  RufusController 1.0
 * (c) 2015 Jonatan Souza
 * RufusController may be freely distributed under the GNU GPL.o change this license header, choose License Headers in Project Properties.
 */
package br.lncc.comcidis.rufus.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.environment.Environment;
import br.com.caelum.vraptor.observer.download.Download;
import br.com.caelum.vraptor.observer.download.FileDownload;
import br.com.caelum.vraptor.observer.upload.UploadSizeLimit;
import br.com.caelum.vraptor.observer.upload.UploadedFile;

import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.view.Results;
import br.lncc.comcidis.rufus.intercepts.NeedLogin;
import br.lncc.comcidis.rufus.model.Cells;
import br.lncc.comcidis.rufus.model.FileModel;
import br.lncc.comcidis.rufus.model.Hosts;
import br.lncc.comcidis.rufus.model.LxcInput;
import br.lncc.comcidis.rufus.model.LxcModel;
import br.lncc.comcidis.rufus.model.UserSession;
import br.lncc.comcidis.rufus.service.RufusService;

import br.lncc.comcidis.rufus.service.WorkflowService;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jonatan
 */
@Controller
@NeedLogin
public class RufusController {

    private static final Logger logger = LoggerFactory.getLogger(RufusController.class);
    private UserSession userSession;
    private Result result;
    private RufusService rufusService;
    private Validator validator;
    private WorkflowService workflowService;
    private Environment environment;

    @Deprecated
    public RufusController() {

    }

    @Inject
    public RufusController(Result result, RufusService rufusService, Validator validator, WorkflowService workflowService, UserSession userSession, Environment environment) {
        this.result = result;
        this.rufusService = rufusService;
        this.validator = validator;
        this.workflowService = workflowService;
        this.userSession = userSession;
        this.environment = environment;
    }

    /**
     * Initial project page
     */
    @Path("/")
    public void index() {

    }

    public void login() {
        result.redirectTo(this).index();
    }

    /**
     * view which show user the personal information, include a option to sign
     * out
     */
    @Path("/account")
    public void account() {

    }

    /**
     * List containers, the user can manage then
     */
    @Path("/dashboard")
    public void dashboard() {
        result.include("list", rufusService.list());
    }

    @Path("/containers")
    public void containers() {
        result.include("containers", rufusService.list());
    }

    @Get("/rufus/containerEdit/{lxc.name}")
    public void containerEdit(LxcModel lxc) {
        result.include("lxc", rufusService.getContainerByName(lxc.getName()));
    }

    @UploadSizeLimit(sizeLimit = 1024 * 1024 * 1024, fileSizeLimit = 1024 * 1024 * 1024)
    public void uploadIcon(UploadedFile uploadedFile, String name, ServletContext servletContext, LxcModel lxcModel) {
        String path = servletContext.getRealPath("/icons");

        File file = new File(path + "/" + name + ".png");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(RufusController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            IOUtils.copy(uploadedFile.getFile(), new FileOutputStream(file));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(RufusController.class.getName()).log(Level.SEVERE, null, ex);
        }

        lxcModel.setName(name);
        result.redirectTo(this).containerEdit(lxcModel);

    }

    /**
     * the can create a container with a list of templates
     */
    @Get("/create")
    public void create() {
        result.include("listTemplates", rufusService.getLxcTemplates());
    }

    /**
     * this method will save the container previously created at create(), this
     * method include a validator, that one will check about blank spaces, os
     * null
     *
     * @param template
     * @param name
     */
    @Post
    public void save(String template, String name) {
        if (!Strings.isNullOrEmpty(name)) {
            validator.addIf(name.contains(" "), new SimpleMessage("Error", "Name cannot have white space."));

        }
        validator.addIf(Strings.isNullOrEmpty(name), new SimpleMessage("Error", "Name cannot be blank."));
        validator.onErrorForwardTo(this).create();

        rufusService.createLxc(name, template);
        result.redirectTo(this).dashboard();
    }

    /**
     * this method has uri -> "/rufus/{lxc.name}/update/{lxc.state}" update will
     * change the containers state, to running or stopped
     *
     * @param lxc
     */
    @Get("/rufus/{lxc.name}/update/{lxc.state}")
    public void update(LxcModel lxc) {
        rufusService.changeState(lxc.getName(), lxc.getState());
        result.redirectTo(this).containerEdit(lxc);
    }

    /**
     * get uri "/rufus/{name}/delete" this method delete the container
     *
     * @param name
     */
    @Get("/rufus/{name}/delete")
    public void delete(String name) {
        rufusService.deleteLxc(name);
        result.redirectTo(this).containers();
    }

    /**
     * view for upload page
     */
    @Get("/upload")
    public void upload() {

    }

    /**
     * view for fileList
     */
    @Get("/file")
    public void fileList() {
        result.include("fileList", rufusService.myFileList());
    }

    /**
     * its a peace of html to be used on bootbox, to show the file list
     */
    @Path("/modalFileList")
    public void modalFileList() {
        result.include("fileList", rufusService.myFileList());
    }

    @Get("/rufus/remoteAccess/{lxc.ips}")
    public void remoteAccess(LxcModel lxc) {

        String url = rufusService.remoteAccess(lxc.getIps().get(0).substring(1, lxc.getIps().get(0).length() - 1));
        result.redirectTo(url.replaceAll("\"", ""));
    }

    /**
     * TODO
     */
    @Path("/modalProgressBar")
    public void modalProgressBar() {

    }

    @Get("/modalWorkflowName")
    public void modalWorkflowName() {
        /*if (!Strings.isNullOrEmpty(workflowName)) {
         validator.addIf(workflowName.contains(" "), new SimpleMessage("Error", "Name cannot have white space."));

         }
         validator.addIf(Strings.isNullOrEmpty(workflowName), new SimpleMessage("Error", "Name cannot be blank."));
        
         List<File> check = workflowService.getAllWorkflowResults();
         for (File file : check) {
         if (file.getName().equalsIgnoreCase(workflowName)) {
         validator.add(new SimpleMessage("nameError", "The Workflow name:\"" + workflowName + "\" already exists"));
         }
         }
         */
        List<String> folders = new ArrayList<>();
        for (File file : workflowService.getAllWorkflowResults()) {
            folders.add(file.getName());
        }
        result.include("folders", new Gson().toJson(folders));

    }

    /**
     * on upload view, will send a file to be save on this method
     *
     * @param file
     */
    @UploadSizeLimit(sizeLimit = 1024 * 1024 * 1024, fileSizeLimit = 1024 * 1024 * 1024)
    public void saveFile(UploadedFile file) {
        File firstUse = new File(environment.get("dir.nfs") + "/" + userSession.currentUser().getEmail() + "/files");
        if (!firstUse.exists()) {
            firstUse.mkdirs();
        }
        File destino = new File(environment.get("dir.nfs") + "/" + userSession.currentUser().getEmail() + "/files/" + file.getFileName());
        try {

            destino.createNewFile();
            InputStream stream = file.getFile();
            IOUtils.copy(stream, new FileOutputStream(destino));

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(RufusController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        result.use(Results.status()).ok();

    }

    /**
     * @Get("/rufus/{name}/deleteFile") this method will delete a file, has a
     * name for param
     * @param name
     */
    @Get("/rufus/{name}/deleteFile")
    public void deleteFile(String name) {
        File tmpFile = new File(environment.get("dir.nfs") + "/" + userSession.currentUser().getEmail() + "/files/" + name);
        tmpFile.delete();
        result.include("file-info", "File Deleted!");
        result.redirectTo(this).fileList();
    }

    //*********************************//
    //         WORKFLOW AREA           //
    //*********************************//
    /**
     * this method will display the workflow dashboard
     */
    @Path("/workflow")
    public void workflow() {
        result.include("containers", rufusService.list());
    }

    /**
     * delete a workflow folder
     *
     * @param workflowFolderName
     */
    @Get("/deleteWorkflow/{name}")
    public void deleteWorkflow(String name) {
        logger.info(name + " workflow para deletar");
        File tmpFile = new File(environment.get("dir.nfs") + "/" + userSession.currentUser().getEmail() + "/" + name);
        try {
            FileUtils.deleteDirectory(tmpFile);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(RufusController.class.getName()).log(Level.SEVERE, null, ex);
        }
        result.include("file-info", "File Deleted!");
        result.redirectTo(this).workflowResult();
    }

    /**
     * TODO -> add parameters to get result
     *
     * this method should be used on bootbox to show a live workflow result
     */
    public void workflowLiveResult() {
        result.use(Results.http()).body(" Processed");
    }

    @Get("/loadWorkflow/{workflowToLoad}")
    public void loadWorkflow(String workflowToLoad) {
        result.use(Results.http()).body(workflowService.loadWorkflow(workflowToLoad));
    }

    /**
     * receive a string with json from workflow this method will prepare a
     * workflow and validate it
     *
     * @param xmlTextArea
     */
    @Post
    public void runWorkflow(String xmlTextArea, String workflowName, String xmlWorkflow, String jsonWorkflow) {

        String user = userSession.currentUser().getEmail();
        File fileWorkflow = new File(environment.get("dir.nfs") + "/" + user + "/" + workflowName);
        fileWorkflow.mkdir();
        workflowService.saveXML(xmlWorkflow, workflowName);
        workflowService.saveJSON(jsonWorkflow, workflowName);
        Cells cells = new Gson().fromJson(xmlTextArea, new Cells().getClass());
        List<LxcInput> containers = new ArrayList<>();

        String validate = workflowService.workflowValidate(cells);
        if (validate.isEmpty()) {
            containers = workflowService.organizeToRun(cells);
            workflowService.runContainers(containers, cells.getInputs(), cells.getLinks(), workflowName, user);
            result.use(Results.status()).ok();
        } else {
            validator.add(new SimpleMessage("error", validate));
            validator.onErrorSendBadRequest();
        }
    }

    @Post
    public void saveWorkflow(String xmlTextArea, String workflowName, String xmlWorkflow, String jsonWorkflow) {
        String user = userSession.currentUser().getEmail();
        File fileWorkflow = new File(environment.get("dir.nfs") + "/" + user + "/" + workflowName);
        fileWorkflow.mkdir();
        workflowService.saveXML(xmlWorkflow, workflowName);
        workflowService.saveJSON(jsonWorkflow, workflowName);
        result.use(Results.http()).setStatusCode(200);
    }

    /**
     * TODO display a download
     *
     * this method will show all folders that contains a workflow result
     */
    @Path("/workflowResults")
    public void workflowResult() {
        List<File> workflowsFiles = workflowService.getAllWorkflowResults();
        List<FileModel> workflows = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        FileModel fileModel = null;
        for (File file : workflowsFiles) {
            fileModel = new FileModel();
            fileModel.setName(file.getName());
            fileModel.setDate(sdf.format(file.lastModified()));
            workflows.add(fileModel);
        }
        result.include("workflows", workflows);
    }

    @Get("/displayResults/{folder}")
    public void displayResults(String folder) {
        List<File> workflowFiles = workflowService.getFilesFromWorkflow(folder);
        result.include("workflowFiles", workflowFiles);
    }

    public Download downloadWorkflowFile(String requiredFile) {
        File file = new File(environment.get("dir.nfs"), requiredFile);
        try {
            return new FileDownload(file, "application/text");
        } catch (IOException ex) {
            logger.info("nao foi possivel downlaod");
        }
        return null;
    }

    @Get("/rufus/{name}/download")
    public Download downloadFile(String name) {
        File file = new File(environment.get("dir.nfs") + "/" + userSession.currentUser().getEmail() + "/files/" + name);
        try {
            return new FileDownload(file, "application/text");
        } catch (IOException ex) {
            logger.info("nao foi possivel downlaod");
        }
        return null;
    }

    @Get("/workflowsToLoad")
    public void workflowsToLoad() {
        result.include("workflows", workflowService.listWorkflowsToLoad());
    }

    //End of workflow Area
    //*************
    //TESTES
    //*************
    // teste session
    public void saveFileTest(UploadedFile file) {

        //logger.debug(file.getFileName());
        //logger.debug("**********************************");
        File destino = new File("" + environment.get("dir.nfs") + "/" + userSession.currentUser().getEmail() + "/" + file.getFileName());
        try {
            destino.createNewFile();
            InputStream stream = file.getFile();
            IOUtils.copy(stream, new FileOutputStream(destino));

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(RufusController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        result.use(Results.status()).ok();

    }

    @Path("/cluster")
    public void clusterOptions() {
        //workflowService.getPylxcResources();
        int cpus = Integer.parseInt(workflowService.getPylxcResources().getCpus());
        ArrayList<String> list = new ArrayList<>();
        result.include("nodes", workflowService.getListCpuAvailables(cpus));
    }

    @Post
    @UploadSizeLimit(sizeLimit = 1024 * 1024 * 1024, fileSizeLimit = 1024 * 1024 * 1024)
    public void loadWorkflow(UploadedFile file) {
        File f = new File(file.getFileName());
        try {
            IOUtils.copy(file.getFile(), new FileOutputStream(f));
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(RufusController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(RufusController.class.getName()).log(Level.SEVERE, null, ex);
        }

        result.use(Results.http()).body(workflowService.loadWorkflowFile(f));

    }

    @Get("/rufus/{workflowRequired}/downloadWorkflowProvider/{type}")
    public Download downloadWorkflowProvider(String workflowRequired, String type) {
        try {
            File f = null;
            if (type.equals("json")) {
                f = new File("" + environment.get("dir.nfs") + "/" + userSession.currentUser().getEmail() + "/"
                        + "" + workflowRequired + "/." + workflowRequired);
                return new FileDownload(f, "application/text", workflowRequired + ".flow");
            } else {
                f = new File("" + environment.get("dir.nfs") + "/" + userSession.currentUser().getEmail() + "/"
                        + "" + workflowRequired + "/" + workflowRequired + ".xml");

                return new FileDownload(f, "application/text", workflowRequired + ".xml");
            }

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(RufusController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Get("/modalUpload")
    public void modalUpload() {
        List<String> folders = new ArrayList<>();
        for (File file : workflowService.getAllWorkflowResults()) {
            folders.add(file.getName());
        }

        result.include("folders", new Gson().toJson(folders));

    }
}
