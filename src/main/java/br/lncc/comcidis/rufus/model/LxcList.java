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
public class LxcList {
    private List<LxcModel> lxcModels;

    public LxcList() {
    }

    public LxcList(List<LxcModel> lxcModels) {
        this.lxcModels = lxcModels;
    }

    public List<LxcModel> getLxcModels() {
        return lxcModels;
    }

    public void setLxcModels(List<LxcModel> lxcModels) {
        this.lxcModels = lxcModels;
    }
    
    
}
