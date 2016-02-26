/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lncc.comcidis.rufus.service;

import br.com.caelum.vraptor.environment.Property;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

public class OauthService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(OauthService.class);
    @Property("dir.admin")
    @Inject
    String USERS_NFS_DIR;
    
    @Deprecated
    public OauthService() {
        
    }
   
    public List<String> getRootUsers() {
        List<String> users = new ArrayList<>();
        File file = new File(USERS_NFS_DIR);
        FileReader fr;
        try {
            fr = new FileReader(file);
            Scanner sc = new Scanner(fr);
            while (sc.hasNext()) {
                users.add(sc.nextLine());
            }
            fr.close();

        } catch (IOException ex) {
            Logger.getLogger(OauthService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return users;
    }

    public synchronized void registerNewRootUser(String email) {
        File file = new File(USERS_NFS_DIR);
        FileWriter fw;
        try {
            fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(email+"\n");
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(OauthService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public synchronized void rewriteListRootUsers(List<String> users){
        File file = new File(USERS_NFS_DIR);
        FileWriter fw;
        try {
            fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for(String user : users){
                bw.write(user+"\n");
               
            }
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(OauthService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void deleteRootUser(String name) {
        List<String> rootUsers = getRootUsers();
        if(rootUsers.contains(name)){
            rootUsers.remove(name);
        }
        rewriteListRootUsers(rootUsers);
        
    }

}
