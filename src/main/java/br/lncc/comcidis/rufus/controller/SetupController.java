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
import br.lncc.comcidis.rufus.factory.HostInterface;
import br.lncc.comcidis.rufus.factory.SetupRufus;
import br.lncc.comcidis.rufus.model.Host;
import br.lncc.comcidis.rufus.model.Hosts;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jonatan
 */
@Controller
public class SetupController {

    /*private static final Logger logger = LoggerFactory.getLogger(SetupController.class);
    private Result result;
    private Hosts hosts;
    private SetupRufus sr;

    @Deprecated
    public SetupController() {

    }

    @Inject
    public SetupController(Result result, SetupRufus sr, @HostInterface Hosts hosts) {
        this.result = result;
        this.sr = sr;
        this.hosts = hosts;

    }

    @Get("/setup")
    public void rufusSetup() {
        result.include("hosts", hosts);
    }

    @Post
    public void saveAddress(String web, String core, String auth) {
        hosts.get("web").setUrl(web);
        hosts.get("core").setUrl(core);
        hosts.get("auth").setUrl(auth);
        sr.setHost(hosts);
        result.redirectTo(RufusController.class).index();
    }*/

}
