/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lncc.comcidis.rufus.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.environment.Property;
import br.lncc.comcidis.rufus.model.Host;
import br.lncc.comcidis.rufus.service.EditInitFile;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jonatan
 */
@Controller
public class SetupController {

    private static final Logger logger = LoggerFactory.getLogger(RufusController.class);
    private Result result;

    @Inject
    @Property
    private String INIT_FILE_LOCATION;
   
    private EditInitFile eif;
    
    @Deprecated
    public SetupController() {

    }
    
    @Inject
    public SetupController(Result result) {
        this.result = result;
        this.eif = new EditInitFile();
    }
    
   

    @Get("/setup/")
    public void rufusSetup() {
        
        result.include("hosts", eif.readInitFile());
    }

    @Post
    public void saveAddress(String web, String core, String auth) {
        
        List<Host> hosts = new ArrayList<Host>();
        hosts.add(new Host("web", web));
        hosts.add(new Host("core", core));
        hosts.add(new Host("auth", auth));
        eif.writeInitFile(new Gson().toJson(hosts));
        result.redirectTo(RufusController.class).index();
    }

}
