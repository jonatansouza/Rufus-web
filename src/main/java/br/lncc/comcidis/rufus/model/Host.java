/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lncc.comcidis.rufus.model;

/**
 *
 * @author jonatan
 */
public class Host {
    private String name;
    private String ip;

    public Host() {
    
    }

    public Host(String name, String ip) {
        this.name = name;
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "Host{" + "name=" + name + ", ip=" + ip + '}';
    }
    
    
}
