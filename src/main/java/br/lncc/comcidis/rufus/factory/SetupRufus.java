/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lncc.comcidis.rufus.factory;

import br.com.caelum.vraptor.environment.Environment;
import br.lncc.comcidis.rufus.controller.RufusController;
import br.lncc.comcidis.rufus.model.GenericHosts;
import br.lncc.comcidis.rufus.model.Host;
import br.lncc.comcidis.rufus.model.Hosts;
import com.google.gson.Gson;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Qualifier;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jonatan
 */
@RequestScoped
public class SetupRufus {

    private Environment environment;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RufusController.class);

    public SetupRufus() {
    }

    @Inject
    public SetupRufus(Environment environment) {
        this.environment = environment;

    }

    

    public Hosts initHosts() {
        String content = "";
        File file = new File(environment.get("initFile.location"));
        FileReader fr;

        try {
            fr = new FileReader(file);
            Scanner sc = new Scanner(fr);
            while (sc.hasNext()) {
                content += sc.nextLine();
            }
            fr.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SetupRufus.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SetupRufus.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Gson().fromJson(content, Hosts.class);
    }

    public Hosts getHosts() {
        return initHosts();
    }

    public void setHost(Hosts hosts) {
        updateHosts(hosts);
    }

    private synchronized void updateHosts(Hosts hosts) {
        File file = new File(environment.get("initFile.location"));
        FileWriter fw;
        String content = new Gson().toJson(hosts);
        try {
            fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(SetupRufus.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
