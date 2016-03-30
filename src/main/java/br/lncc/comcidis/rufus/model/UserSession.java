/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lncc.comcidis.rufus.model;


import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author jonatan
 */
@SessionScoped
@Named("userSession")
public class UserSession implements Serializable {

    private Me me;
    
    public void authenticate(Me me) {
        if (me == null) {
            
        }
        this.me = me;
    }

    public void logout() {
        this.me = null;
    }

    public boolean isLogged() {
        return me != null;
    }

    public Me currentUser() {
        return me;
    }

}
