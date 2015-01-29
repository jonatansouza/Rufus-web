/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lncc.comcidis.rufus.service;

/**
 *
 * @author jonatan
 */
public class NaoAutenticadoException extends IllegalStateException{

    @Override
    public String getMessage() {
        return "Usuário não autenticado!"; //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
