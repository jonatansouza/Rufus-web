/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lncc.comcidis.rufus.service;

import br.com.caelum.vraptor.environment.Property;
import br.lncc.comcidis.rufus.controller.RufusController;
import br.lncc.comcidis.rufus.model.FileModel;
import br.lncc.comcidis.rufus.model.LxcInput;
import br.lncc.comcidis.rufus.model.LxcList;
import br.lncc.comcidis.rufus.model.LxcModel;
import br.lncc.comcidis.rufus.model.UserSession;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.inject.Inject;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jonatan
 */
public class RufusService {

    private static final Logger logger = LoggerFactory.getLogger(RufusService.class);

    @Inject
    @Property
    private String ip;

    @Inject
    @Property
    private String port;

    @Inject
    @Property
    private String version;
    
    @Inject
    UserSession userSession;

    @Inject
    @Property
    private String pathNfsDirectory;

    private HttpClient httpClient;

    @Deprecated
    public RufusService() {
        httpClient = HttpClients.createDefault();
    }

    public List<LxcModel> list() {
        
        HttpGet hg = new HttpGet("http://" + ip + ":" + port + "/" + version + "/containers");
        try {
            HttpResponse answer = httpClient.execute(hg);
            BufferedReader br = new BufferedReader(new InputStreamReader((answer.getEntity().getContent())));
            String output = "";
            String json = "";
            while ((output = br.readLine()) != null) {
                json += output;
            }
            logger.info(json);
            LxcModel[] lxcs = new Gson().fromJson(json, LxcModel[].class);
            LxcModel tmplxc; 
            List<LxcModel> lxcModels = new ArrayList<>();
            for(int i = 0; i<lxcs.length;i++){
                tmplxc = new LxcModel(lxcs[i].getIps(), lxcs[i].getName(), lxcs[i].getState());
                lxcModels.add(tmplxc);
            }
            
            return lxcModels;
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(RufusController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<String> getLxcTemplates() {
        HttpGet hg = new HttpGet("http://" + ip + ":" + port + "/" + version + "/templates");
        try {
            HttpResponse answer = httpClient.execute(hg);
            BufferedReader br = new BufferedReader(new InputStreamReader((answer.getEntity().getContent())));
            String output = "";
            String json = "";
            while ((output = br.readLine()) != null) {
                json += output;
            }

            List<String> templateList = new ArrayList<String>();

            templateList = new Gson().fromJson(json, templateList.getClass());

            return templateList;

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(RufusController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public void createLxc(String name, String template) {
        String order = "";

        //if (template.equals("default")) {
        order = "{\"name\":\"" + name + "\"}";
        //} else {
        //  order = "{\"name\":\"" + name + "\", \"template\":\"" + template + "\"}";
        //}

        HttpPost hp = new HttpPost("http://" + ip + ":" + port + "/" + version + "/containers");
        StringEntity st = new StringEntity(order, "utf-8");
        st.setContentType("application/json");
        hp.setEntity(st);
        try {
            httpClient.execute(hp);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(RufusService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void changeState(String name, String state) {
        String order = "";

        if (state.equals("STOPPED")) {
            order = "{\"state\":\"RUNNING\"}";
        }
        if (state.equals("RUNNING")) {
            order = "{\"state\":\"STOPPED\"}";
        }

        HttpPut clientPut = new HttpPut("http://" + ip + ":" + port + "/" + version + "/containers/" + name + "");
        StringEntity st = new StringEntity(order, "utf-8");
        st.setContentType("application/json");
        clientPut.setEntity(st);
        try {
            httpClient.execute(clientPut);

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(RufusService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteLxc(String name) {
        String order = "http://" + ip + ":" + port + "/" + version + "/containers/" + name;
        HttpDelete hd = new HttpDelete(order);
        try {
            httpClient.execute(hd);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(RufusService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //
    //UPLOAD Section
    //
    public List<FileModel> myFileList() {

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        File raiz = new File(pathNfsDirectory+"/"+userSession.currentUser().getEmail()+"/files");
        FilenameFilter filter = new FileFileFilter() {
            public boolean accept(File dir, String name) {
                String lowercaseName = name.toLowerCase();
                if (!lowercaseName.startsWith(".")) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        List<FileModel> list = new ArrayList<>();
        
        if(!raiz.exists()){
            raiz.mkdir();
        }
        
        for (File file : raiz.listFiles(filter)) {
            FileModel fm = new FileModel();
            fm.setName(file.getName());
            fm.setDate(sdf.format(file.lastModified()));
            list.add(fm);
        }

        return list;
    }
    
    

    public String operateLxc(LxcInput lxcInputs) {
        String order = "{\"command\" : \"" + lxcInputs.getActivity() + "\"}";

        HttpPost hp = new HttpPost("http://" + ip + ":" + port + "/" + version + "/containers/input/run");
        StringEntity st = new StringEntity(order, "utf-8");
        st.setContentType("application/json");
        hp.setEntity(st);
        String json = "";
            
        HttpResponse answer;
        try {
            answer = httpClient.execute(hp);
            BufferedReader br = new BufferedReader(new InputStreamReader((answer.getEntity().getContent())));
            String output = "";
            while ((output = br.readLine()) != null) {
                json += output;
            }

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(RufusService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return json;

    }
    
    
    //***********************************
    //WORKFLOW SERVICES
    //***********************************
    public void runOperations(String user, String app_id){
        File inputs = new File(pathNfsDirectory+user+"/"+app_id+"/in/");
        logger.info(pathNfsDirectory+user+"/"+app_id+"/in/");
        FilenameFilter filter = new FileFileFilter() {
            public boolean accept(File dir, String name) {
                String lowercaseName = name.toLowerCase();
                if (!lowercaseName.equals("stdin")) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        List<String> inputNames = new ArrayList<>();
                
        for(String inputName : inputs.list(filter)){
            inputNames.add(inputName);
        }
        
        String jsonFile = new Gson().toJson(inputNames);
        
        
        
        String order = "{\"username\":\""+user+"\", \"app_id\":\""+app_id+"\", \"args\":"+jsonFile+"}";
        logger.info(order+"####################");
        
        HttpPost hp = new HttpPost("http://" + ip + ":" + port + "/" + version + "/containers/Addition/run");
        StringEntity st = new StringEntity(order, "utf-8");
        st.setContentType("application/json");
        hp.setEntity(st);
        String json = "";
            
        HttpResponse answer;
        try {
            answer = httpClient.execute(hp);
            BufferedReader br = new BufferedReader(new InputStreamReader((answer.getEntity().getContent())));
            String output = "";
            while ((output = br.readLine()) != null) {
                json += output;
            }

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(RufusService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

   
}
