/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lncc.comcidis.rufus.model;

import br.lncc.comcidis.rufus.service.WorkflowService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jonatan
 */
public class Cells {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Cells.class);

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
    
    public List<LxcInput> getLinks() {
        List<LxcInput> lxcs = new ArrayList<>();
        for (LxcInput lxc : cells) {
            if (lxc.getType().equals("link")) {
                lxcs.add(lxc);
            }
        }

        return lxcs;
    }

    public List<LxcInput> getInputs() {
        List<LxcInput> lxcs = new ArrayList<>();
        for (LxcInput lxc : cells) {
            if (lxc.getName() != null) {
                if (!lxc.isContainer()) {
                    lxcs.add(lxc);
                }
            }
        }

        return lxcs;
    }

    public List<LxcInput> getContainers() {
        List<LxcInput> lxcs = new ArrayList<>();
        for (LxcInput lxc : cells) {
            if (lxc.getName() != null) {
                if (lxc.isContainer()) {
                    lxcs.add(lxc);
                }
            }
        }
        return lxcs;
    }

    public List<LxcInput> getAllActions() {
        List<LxcInput> lxcs = new ArrayList<>();
        for (LxcInput lxc : cells) {
            lxcs.add(lxc);
        }
        return lxcs;

    }

    public LxcInput getResult() {
        List<LxcInput> lxcs = new ArrayList<>();
        for (LxcInput lxc : cells) {
            if (lxc.getName() != null) {
                if (lxc.getName().equalsIgnoreCase("result")) {
                    lxcs.add(lxc);
                }
            }
        }

        if (lxcs.size() > 1) {
            return null;
        } else {
            return lxcs.get(0);
        }
    }
    
    public LxcInput getById(String id){
        LxcInput found = new LxcInput();
        for(LxcInput lxc : getContainersAndInputs()){
            logger.info(id+" inside getByid id to found");
            
            logger.info(lxc.getId()+" inside getByid lxc id");
            if(lxc.getId().equalsIgnoreCase(id)){
                logger.info("inside if getbyid");
                found = lxc;
            }
        }
        return found;
    }
    
    public LxcInput getContainersById(String id){
        LxcInput found = new LxcInput();
        for(LxcInput lxc : getContainersAndInputs()){
            if(lxc.getId().equalsIgnoreCase(id)){
                found = lxc;
            }
        }
        return found;
    }
    
    
    public List<LxcInput> getContainersAndInputs(){
        List<LxcInput> lxcs = new ArrayList<>();
        for (LxcInput lxc : cells) {
            if (!lxc.getType().equals("link")) {
                lxcs.add(lxc);
            }
        }

        return lxcs;
    }
    
    public static List<String> getLinkByTarget(String id, List<LxcInput> links){
        List<String> found = new ArrayList();
        for(LxcInput lxc : links){
            if(lxc.getTarget().getId().equals(id)){
                found.add(lxc.getSource().getId());
            }
        }
        return found;
    }
    
    public static List<LxcInput> getLxcInputsByStep(int step, List<LxcInput> lxcs){
         List<LxcInput> found = new ArrayList();
         for(LxcInput lxc : lxcs){
             if(lxc.getStep() == step){
                 found.add(lxc);
             }
         }
         return found;
    }
    
    public static List<LxcInput> getLxcContainersByStep(int step, List<LxcInput> lxcs){
         List<LxcInput> found = new ArrayList();
         for(LxcInput lxc : lxcs){
             if(lxc.getStep() == step){
                 if(lxc.isContainer()){
                    found.add(lxc);
                 }
             }
         }
         return found;
    }
    
    
    
    
}
