/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lncc.comcidis.rufus.model;

import org.apache.http.HttpResponse;

/**
 *
 * @author jonatan
 */
public class WorkflowNodeResult {
    private String containerName;
    private int statusCode;
    private String reason;
    private String result;

    public WorkflowNodeResult(String containerName, int statusCode, String reason, String result) {
        this.containerName = containerName;
        this.statusCode = statusCode;
        this.reason = reason;
        this.result = result;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "WorkflowNodeResult{" + "containerName=" + containerName + ", statusCode=" + statusCode + ", reason=" + reason + ", result=" + result + '}';
    }

    
    
}
