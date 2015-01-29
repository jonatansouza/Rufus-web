/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lncc.comcidis.rufus.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jonatan
 */
public class Cells {
    private List<LxcInput> cells;

    public List<LxcInput> getCells() {
        return cells;
    }

    public void setCells(List<LxcInput> cells) {
        this.cells = cells;
    }

    @Override
    public String toString() {
        return "Cells{" + "cells=" + cells + '}';
    }

    public List<LxcInput> getLinks(){
        List<LxcInput> lxcs = new ArrayList<>();
        for(LxcInput lxc : cells){
            if(lxc.getType().equals("link")){
                lxcs.add(lxc);
            }
        }
        
        return lxcs;
    }
    
    public List<LxcInput> getInputs(){
        List<LxcInput> lxcs = new ArrayList<>();
        for(LxcInput lxc : cells){
            if(!lxc.getType().equals("link")){
                lxcs.add(lxc);
            }
        }
        
        return lxcs;
    }
    
    
}
