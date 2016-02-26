/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lncc.comcidis.rufus.factory;

import br.lncc.comcidis.rufus.model.Hosts;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Qualifier;

/**
 *
 * @author jonatan
 */
@RequestScoped
public class HostsFactory {
    @Inject
    private SetupRufus setupRufus;
    
    
    @Produces @HostInterface
    public Hosts create(){
        return setupRufus.getHosts();
    }
}
