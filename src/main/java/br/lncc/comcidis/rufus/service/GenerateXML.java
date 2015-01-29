/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lncc.comcidis.rufus.service;


import br.lncc.comcidis.rufus.model.Cells;
import br.lncc.comcidis.rufus.model.LxcInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jonatan
 */
public class GenerateXML {
    private static final Logger logger = LoggerFactory.getLogger(GenerateXML.class);
    public static String generateXML(Cells cells) {
        int count = 0;
        String myXML = "";
        myXML = "<WorkflowProcess>"
                + "<Activities>";

        for (LxcInput lxc : cells.getInputs()) {
            myXML += "<Activity id=\"" + lxc.getId() + "\" name=\"" + lxc.getName() + "\">"
                    + "<Implementation>" + lxc.getActivity() + "</Implementation>"
                    + "</Activity>";
            

        }
        myXML += "</Activities><Transitions>";
        for (LxcInput link : cells.getLinks()) {
            myXML += "<Transition id=\"" + count + "\" from=\"" + link.getSource().getId() + "\"  "
                    + "to=\"" + link.getTarget().getId() + "\"/>";
            count++;
        }
        myXML += "</Transitions></WorkflowProcess>";
       
        return myXML;
    }
}
