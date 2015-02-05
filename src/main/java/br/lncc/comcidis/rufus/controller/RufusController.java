/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lncc.comcidis.rufus.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.environment.Property;
import br.com.caelum.vraptor.observer.upload.UploadSizeLimit;
import br.com.caelum.vraptor.observer.upload.UploadedFile;

import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.view.Results;
import br.lncc.comcidis.rufus.intercepts.NeedLogin;
import br.lncc.comcidis.rufus.model.Cells;
import br.lncc.comcidis.rufus.model.LxcInput;
import br.lncc.comcidis.rufus.model.LxcModel;
import br.lncc.comcidis.rufus.model.UserSession;
import br.lncc.comcidis.rufus.service.GenerateXML;
import br.lncc.comcidis.rufus.service.RufusService;

import br.lncc.comcidis.rufus.service.WorkflowService;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.inject.Inject;
import javax.swing.text.html.HTML;
import javax.ws.rs.GET;
import org.apache.commons.io.IOUtils;
import org.json.XML;

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

    @Inject
    @Property
    private String pathNfsDirectory;

    @Deprecated
    public RufusController() {
       
    }

    @Inject
    public RufusController(Result result, RufusService rufusService, Validator validator, WorkflowService workflowService) {
        this.result = result;
        this.rufusService = rufusService;
        this.validator = validator;
        this.workflowService = workflowService;
        
    }

    @Path("/index")
    public void index() {

    }

    @Path("/dashboard")
    public void dashboard() {
        result.include("list", rufusService.list());

    }

    @Post
    public void runWorkflow(String xmlTextArea) {

        Cells cells = new Cells();
        cells = new Gson().fromJson(xmlTextArea, cells.getClass());
        String myXML = "teste";

        //workflowService.prepareFiles(cells);
        workflowService.organizeToRun(cells);
        /*List<String> app_ids = workflowService.prepareFiles(cells);
         for(String id : app_ids){
         rufusService.runOperations("jonatan", id);
         }*/

        myXML = GenerateXML.generateXML(cells);
        /*logger.info("***************************");
         logger.info(xmlTextArea);
         result.use(Results.xml()).from(myXML).serialize();*/

    }

    @Get("/create")
    public void create() {
        result.include("listTemplates", rufusService.getLxcTemplates());
    }

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

    @Get("/rufus/{lxc.name}/update/{lxc.state}")
    public void update(LxcModel lxc) {
        rufusService.changeState(lxc.getName(), lxc.getState());
        result.redirectTo(this).dashboard();
    }

    @Get("/rufus/{name}/delete")
    public void delete(String name) {
        rufusService.deleteLxc(name);
        result.redirectTo(this).dashboard();
    }

    @Path("/workflow")
    public void workflow() {

    }

    @Get("/upload")
    public void upload() {

    }

    @Get("/files")
    public void fileList() {
        result.include("fileList", rufusService.myFileList());

    }

    @UploadSizeLimit(sizeLimit = 1024 * 1024 * 1024, fileSizeLimit = 1024 * 1024 * 1024)
    public void saveFile(UploadedFile file) {
        File destino = new File("" + pathNfsDirectory + file.getFileName());
        try {
            destino.createNewFile();
            InputStream stream = file.getFile();
            IOUtils.copy(stream, new FileOutputStream(destino));

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(RufusController.class.getName()).log(Level.SEVERE, null, ex);
        }

        result.use(Results.status()).ok();

    }

    @Get("/rufus/{name}/deleteFile")
    public void deleteFile(String name) {
        File tmpFile = new File(pathNfsDirectory + name);
        tmpFile.delete();
        result.include("file-info", "File Deleted!");
        result.redirectTo(this).fileList();
    }

    //*************
    //TESTES
    //*************
    public void saveFileTest(UploadedFile file) {

        //logger.debug(file.getFileName());
        //logger.debug("**********************************");
        File destino = new File("" + pathNfsDirectory + file.getFileName());
        try {
            destino.createNewFile();
            InputStream stream = file.getFile();
            IOUtils.copy(stream, new FileOutputStream(destino));

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(RufusController.class.getName()).log(Level.SEVERE, null, ex);
        }

        result.use(Results.status()).ok();

    }

}
