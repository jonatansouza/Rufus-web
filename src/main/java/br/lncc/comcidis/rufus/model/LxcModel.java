/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lncc.comcidis.rufus.model;

import java.util.List;


/**
 *
 * @author jonatan
 */


public class LxcModel {
    
    
    private String name;
    private String state;
    private List<String> ips;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<String> getIps() {
        return ips;
    }

    public void setIps(List<String> ips) {
        this.ips = ips;
    }

    @Override
    public String toString() {
        return "LxcModel{" + "name=" + name + ", state=" + state + ", ips=" + ips + '}';
    }
    
    
    
    
    
}
