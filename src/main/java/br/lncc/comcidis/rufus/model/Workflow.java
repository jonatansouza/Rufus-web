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
public class Workflow {
    private String username;
    private String workflow_id;
    private String app_id;
    private List<String> files;
    private String args;
    private String nodes;

    public Workflow(String username, String workflow_id, String app_id, List<String> files, String args, String nodes) {
        this.username = username;
        this.workflow_id = workflow_id;
        this.app_id = app_id;
        this.files = files;
        this.args = args;
        this.nodes = nodes;
    }

    public Workflow() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWorkflow_id() {
        return workflow_id;
    }

    public void setWorkflow_id(String workflow_id) {
        this.workflow_id = workflow_id;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public String getNodes() {
        return nodes;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }
    
    
}
