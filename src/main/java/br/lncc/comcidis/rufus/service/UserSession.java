/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lncc.comcidis.rufus.service;

import br.lncc.comcidis.rufus.model.Me;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 *
 * @author jonatan
 */

@SessionScoped
public class UserSession{
    boolean logged = false;

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }
    
    
   
}
