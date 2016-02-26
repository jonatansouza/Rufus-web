/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lncc.comcidis.rufus.model;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author jonatan
 */

public class Host {
    private String name;
    private String url;
    private AuthSetup authSetup;

    public Host(String name, String url, AuthSetup authSetup) {
        this.name = name;
        this.url = url;
        this.authSetup = authSetup;
    }

    public Host() {
    }

    public AuthSetup getAuthSetup() {
        return authSetup;
    }

    public void setAuthSetup(AuthSetup authSetup) {
        this.authSetup = authSetup;
    }
    
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    } 
    public URI getUrl() {
        try {
            return new URI(url);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Host.class.getName()).log(Level.SEVERE, null, ex);
        }
            return null;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Host{" + "name=" + name + ", url=" + url + ", authSetup=" + authSetup + '}';
    }

    

     
}
