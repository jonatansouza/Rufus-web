/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lncc.comcidis.rufus.service;

import br.com.caelum.vraptor.environment.Property;
import br.lncc.comcidis.rufus.controller.RufusController;
import br.lncc.comcidis.rufus.model.Cells;
import br.lncc.comcidis.rufus.model.LxcInput;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.compiler.TokenId;
import javax.inject.Inject;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jonatan
 */
public class WorkflowService {

    @Inject
    @Property
    private String pathNfsDirectory;

    private List<List<String>> levels;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(WorkflowService.class);

    /**
     * esta funcao organiza os inputs nos diretorios correnspondentes aos
     * containers
     *
     * @param cells
     * @return
     */
    public List<String> prepareFiles(Cells cells) {
        List<File> app_ids = new ArrayList<>();

        for (LxcInput lxc : cells.getAdditions()) {
            File app_id = new File("/var/rufus/users/jonatan/" + lxc.getName() + "-" + lxc.getId());
            if (!app_id.exists()) {
                new File("/var/rufus/users/jonatan/" + lxc.getName() + "-" + lxc.getId()).mkdirs();
            }
            new File(app_id.getPath() + "/in").mkdirs();
            new File(app_id.getPath() + "/out").mkdirs();

            File stdin = new File(app_id.getPath() + "/in/stdin");
            File stdout = new File(app_id.getPath() + "/out/stdout");
            File stderr = new File(app_id.getPath() + "/out/stderr");
            try {
                stdin.createNewFile();
                stdout.createNewFile();
                stderr.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(WorkflowService.class.getName()).log(Level.SEVERE, null, ex);
            }
            app_ids.add(app_id);
        }
        for (LxcInput app_id : cells.getAdditions()) {
            for (LxcInput lxcInput : cells.getInputs()) {
                for (LxcInput link : cells.getLinks()) {
                    if (link.getSource().getId().equals(lxcInput.getId())
                            && link.getTarget().getId().equals(app_id.getId())) {
                        File input = new File(pathNfsDirectory + "jonatan/"
                                + app_id.getName() + "-" + app_id.getId()
                                + "/in/" + lxcInput.getName() + "-" + lxcInput.getId());
                        FileWriter fw;
                        try {
                            fw = new FileWriter(input);
                            BufferedWriter bw = new BufferedWriter(fw);
                            bw.write(lxcInput.getActivity());
                            bw.close();

                        } catch (IOException ex) {
                            Logger.getLogger(WorkflowService.class
                                    .getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }
            }
        }
        List<String> returnIds = new ArrayList<>();
        for (File app_idName : app_ids) {

            returnIds.add(app_idName.getName());
        }
        return returnIds;
    }

    /**
     * organiza os nivels para serem executados
     *
     * @param cells
     */
    public void organizeToRun(Cells cells) {
        levels = new ArrayList<>();
        LxcInput lxcResult = cells.getResult();
        List<String> sources = new ArrayList<>();
        for (LxcInput link : cells.getLinks()) {
            if (link.getTarget().getId().equals(lxcResult.getId())) {
                sources.add(link.getSource().getId());
               
            }
        }

        levels.add(sources);
        boolean alive = true;
        while (alive) {
            sources = null;
            sources = new ArrayList<>();
            for (LxcInput link : cells.getLinks()) {
                for (List<String> levelsources : levels) {
                    for (String id : levelsources) {
                        if (link.getTarget().getId().equals(id)) {
                            logger.info(id+" ---------> levels");
                            logger.info(link.getTarget().getId() + "---------> link target");
                            logger.info(link.getSource().getId() + "---------> link source");
                            sources.add(link.getSource().getId());
                        }
                    }
                }
            }
            if (sources.size() == 0) {
                alive = false;
            }
            levels.add(sources);
        }
        logger.info(levels.size()+" ############ NIVEIS");
    }
}
