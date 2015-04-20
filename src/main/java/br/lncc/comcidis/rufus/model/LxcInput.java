/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lncc.comcidis.rufus.model;

/**
 *
 * @author jonatan
 */
public class LxcInput {
    private String name;
    private String activity;
    private String id;
    private String type;
    private Source source;
    private Target target;
    private boolean container;
    private String nodes;
    private int step = 0;

    public LxcInput(String name, String activity, String id, String type, Source source, Target target, boolean container, String nodes) {
        this.name = name;
        this.activity = activity;
        this.id = id;
        this.type = type;
        this.source = source;
        this.target = target;
        this.container = container;
        this.nodes = (nodes.isEmpty() || nodes == null) ? "1" : nodes;
    }

    public LxcInput() {
    }

    public void setNodes(String nodes) {
        
        this.nodes = (nodes.isEmpty() || nodes == null) ? "1" : nodes;
    }

    public String getNodes() {
        return nodes;
    }

    public boolean isContainer() {
        return container;
    }

    public void setContainer(boolean container) {
        this.container = container;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
    
    public void incrementStep(){
        this.step ++;
    }
   
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "LxcInput{" + "name=" + name + ", activity=" + activity + ", id=" + id + ", source=" + source.getId() + ", target=" + target.getId() + '}';
    }

    
}
