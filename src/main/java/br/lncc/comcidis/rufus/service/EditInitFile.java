/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lncc.comcidis.rufus.service;

import br.com.caelum.vraptor.environment.Property;
import br.lncc.comcidis.rufus.controller.RufusController;
import br.lncc.comcidis.rufus.model.Host;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jonatan
 */

public class EditInitFile {

    private String initFileLocation = "/var/rufus/setup/initFile";

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RufusController.class);
       
   
    public List<Host> readInitFile() {
        String content = "";
        File file = new File(initFileLocation);
        FileReader fr;
        try {
            fr = new FileReader(file);
            Scanner sc = new Scanner(fr);
            while (sc.hasNext()) {
                content += sc.nextLine();
            }
            fr.close();

        } catch (IOException ex) {
            Logger.getLogger(OauthService.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Host> hosts = null;
        return new Gson().fromJson(content, new TypeToken<List<Host>>(){}.getType());
    }

    public void writeInitFile(String content) {
        File file = new File(initFileLocation);
        FileWriter fw;
        try {
            fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(OauthService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Host getHost(String name){
        List<Host> hosts = readInitFile();
        for(Host host : hosts){
            if(host.getName().equals(name)){
                return host;
            }
        }
        return null;
    }
    
    

}
