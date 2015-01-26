/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lncc.comcidis.rufus.intercepts;

import br.com.caelum.vraptor.Accepts;
import br.com.caelum.vraptor.AfterCall;
import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.BeforeCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.InterceptorMethodParametersResolver;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.view.Results;
import br.lncc.comcidis.rufus.controller.OAuthController;
import br.lncc.comcidis.rufus.controller.RufusController;
import br.lncc.comcidis.rufus.service.UserSession;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jonatan
 */
@Intercepts
@RequestScoped
public class OAuthIntercept {

    private static final Logger logger = LoggerFactory.getLogger(OAuthIntercept.class);

    @Inject
    Result result;
    @Inject
    HttpServletRequest request;

    @Accepts
    public boolean accepts() {
        return true;
    }

    @BeforeCall
    public void before() throws OAuthSystemException {
        UserSession userSession = new UserSession();
        try {
            userSession = (UserSession) request.getSession().getAttribute("userSession");
            String url = request.getRequestURI();
            logger.info("**************");
            if (userSession.isLogged()) {
                logger.info(url);
                logger.info("logado");

            } else {
                logger.info(url);
                logger.info("nao logado");
            }

        } catch (Exception e) {
            logger.info("no User session");
        }

    }
}
