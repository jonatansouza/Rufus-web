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
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.environment.Property;
import br.com.caelum.vraptor.observer.upload.UploadedFile;

import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.view.Results;
import br.lncc.comcidis.rufus.model.LxcModel;
import br.lncc.comcidis.rufus.service.RufusService;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.inject.Inject;
import org.apache.commons.io.IOUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jonatan
 */
@Controller
public class RufusController {

    private static final Logger logger = LoggerFactory.getLogger(RufusController.class);
    private Result result;
    private RufusService rufusService;
    private Validator validator;

    @Inject
    @Property
    private String pathNfsDirectory;

    @Deprecated
    public RufusController() {

    }

    @Inject
    public RufusController(Result result, RufusService rufusService, Validator validator) {
        this.result = result;
        this.rufusService = rufusService;
        this.validator = validator;
    }

    @Path("")
    public void index() {

        result.include("list", rufusService.list());
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
        result.redirectTo(this).index();
    }

    @Get("/rufus/{lxc.name}/update/{lxc.state}")
    public void update(LxcModel lxc) {
        rufusService.changeState(lxc.getName(), lxc.getState());
        result.redirectTo(this).index();
    }

    @Get("/rufus/{name}/delete")
    public void delete(String name) {
        rufusService.deleteLxc(name);
        result.redirectTo(this).index();
    }

    @Get("/upload")
    public void upload() {

    }

    @Get("/files")
    public void fileList() {
        result.include("fileList", rufusService.myFileList());
    }

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

    //**********
    //TESTES
    //*************
    public void testes() {

    }

    public void saveFileTest(UploadedFile file) {

        logger.debug(file.getFileName());
        logger.debug("**********************************");
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
