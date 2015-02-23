/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lncc.comcidis.rufus.model;

import java.io.Serializable;
import java.util.List;


/**
 *
 * @author jonatan
 */


public class LxcModel implements Serializable{
    
    private List<String> ips;
    private String name;
    private String state;

    public LxcModel() {
    }

    public LxcModel(List<String> ips, String name, String state) {
        this.ips = ips;
        this.name = name;
        this.state = state;
    }

    
    
    public List<String> getIps() {
        return ips;
    }

    public void setIps(List<String> ips) {
        this.ips = ips;
    }

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

    @Override
    public String toString() {
        return "LxcModel{" + "ips=" + ips + ", name=" + name + ", state=" + state + '}';
    }
    

    
    
}
