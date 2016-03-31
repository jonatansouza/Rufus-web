/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lncc.comcidis.rufus.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;

/**
 *
 * @author jonatan
 */
public class UtilsService {
    public static String readBodyHttpResponse(HttpResponse httpResponse){
        String output = "";       
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader((httpResponse.getEntity().getContent())));
            while ((output = br.readLine()) != null){
                result += output;
            };
        } catch (IOException ex) {
            Logger.getLogger(UtilsService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
}
