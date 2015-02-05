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
                if (lxc.getName().equalsIgnoreCase("input")) {
                    lxcs.add(lxc);
                }
            }
        }

        return lxcs;
    }

    public List<LxcInput> getAdditions() {
        List<LxcInput> lxcs = new ArrayList<>();
        for (LxcInput lxc : cells) {
            if (lxc.getName() != null) {
                if (lxc.getName().equalsIgnoreCase("addition")) {
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

}
