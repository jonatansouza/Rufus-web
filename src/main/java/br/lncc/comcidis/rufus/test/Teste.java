/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lncc.comcidis.rufus.test;

import br.lncc.comcidis.rufus.factory.SetupRufus;
import br.lncc.comcidis.rufus.model.AuthSetup;
import br.lncc.comcidis.rufus.model.Host;
import br.lncc.comcidis.rufus.model.Hosts;
import br.lncc.comcidis.rufus.model.Workflow;
import com.google.gson.Gson;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jonatan
 */
public class Teste {
    
    public void updateHosts(Hosts hosts) {
        
        File file = new File("/var/rufus/setup/initFile");
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
    
    public void read(){
        Hosts hosts = new Hosts();
        String content = "";
        File file = new File("/var/rufus/setup/initFile");
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

        hosts = new Gson().fromJson(content, Hosts.class);
        System.out.println(hosts.toString());
        System.out.println(hosts.get("core").toString());
    }
    
    public static void main(String[] as) {
        Teste t = new Teste();
        Host w = new Host("web", "a", null);
        Host c = new Host("core", "a", null);
        Host a = new Host("auth", "a", new AuthSetup("123", "123", "x", "x"));
        
        List<Host> l = Arrays.asList(w,c,a);
        Hosts h = new Hosts();
        h.setHosts(l);
        
        t.updateHosts(h);

       
    }
}
