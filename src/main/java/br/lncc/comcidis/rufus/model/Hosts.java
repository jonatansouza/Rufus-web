/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lncc.comcidis.rufus.model;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author jonatan
 */

public class Hosts {
    private List<Host> hosts;

    public Hosts() {
        
    }
    
    public List<Host> getHosts() {
        return hosts;
    }

    public void setHosts(List<Host> hosts) {
        this.hosts = hosts;
    }
    
    public Host get(String name){
        for(Host h: hosts){
            
            if(h.getName().equals(name)){
                return h;
            }
        }
        return null;
    } 

    @Override
    public String toString() {
        return "Hosts{" + "hosts=" + hosts + '}';
    }
    
}
