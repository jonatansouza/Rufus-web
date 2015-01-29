/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lncc.comcidis.rufus.service;

import br.lncc.comcidis.rufus.model.Me;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author jonatan
 */

@SessionScoped
@Named("userSession")
public class UserSession{
    
    private Me me;
    
    public void authenticate(Me me){
        if(me == null){
            throw  new NaoAutenticadoException();
        }
        this.me = me;
    }
    
    public void logout(){
        this.me = null;
    }
    
    public boolean isLogged(){
        return me != null;
    }
    
    public Me currentUser(){
        return me;
    }
    
    
   
}
