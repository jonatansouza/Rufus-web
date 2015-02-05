/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lncc.comcidis.rufus.intercepts;

import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.AcceptsWithAnnotations;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.lncc.comcidis.rufus.controller.OAuthController;
import br.lncc.comcidis.rufus.model.UserSession;

import java.util.logging.Level;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jonatan
 */
@Intercepts
@AcceptsWithAnnotations(NeedLogin.class)
public class OAuthIntercept {
    @Inject
    private UserSession userSession;
    @Inject        
    private Result result;
    
    @AroundCall
    public void around(SimpleInterceptorStack stack) {
    
        if (!userSession.isLogged()) {
            result.redirectTo(OAuthController.class).login();
        } else {
            stack.next();
        }

    }

}
