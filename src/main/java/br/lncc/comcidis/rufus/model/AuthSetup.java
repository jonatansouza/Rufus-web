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
public class AuthSetup {
    private String appID;
    private String appSecret;
    private String clientEndPoint;
    private String appEndPoint;

    public AuthSetup(String appID, String appSecret, String clientEndPoint, String appEndPoint) {
        this.appID = appID;
        this.appSecret = appSecret;
        this.clientEndPoint = clientEndPoint;
        this.appEndPoint = appEndPoint;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getClientEndPoint() {
        return clientEndPoint;
    }

    public void setClientEndPoint(String clientEndPoint) {
        this.clientEndPoint = clientEndPoint;
    }

    public String getAppEndPoint() {
        return appEndPoint;
    }

    public void setAppEndPoint(String appEndPoint) {
        this.appEndPoint = appEndPoint;
    }

    @Override
    public String toString() {
        return "AuthSetup{" + "appID=" + appID + ", appSecret=" + appSecret + ", clientEndPoint=" + clientEndPoint + ", appEndPoint=" + appEndPoint + '}';
    }

    
    
}
