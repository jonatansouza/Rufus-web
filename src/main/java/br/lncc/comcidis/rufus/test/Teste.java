/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lncc.comcidis.rufus.test;

import br.lncc.comcidis.rufus.model.Workflow;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jonatan
 */
public class Teste {
    public static void main(String[] as) {
        List<String> args = new ArrayList<>();
        List<String> resources = new ArrayList<>();
        
        
        resources.add("file01");
        resources.add("file02");
        
        
       /*Workflow w = new Workflow("Jonatan", "TESTE001", "Blast01", resources,"-o dkjsafhdkj -r lksdajlakd");
        
        String json = new Gson().toJson(w);
        
        System.out.println(json);*/
    }
}
