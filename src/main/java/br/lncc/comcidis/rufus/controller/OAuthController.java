/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lncc.comcidis.rufus.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.lncc.comcidis.rufus.service.UserSession;
import com.google.gson.Gson;
import java.util.logging.Level;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.GitHubTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.jboss.weld.bean.builtin.ee.HttpServletRequestBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jonatan
 */
@Controller
@SessionScoped

public class OAuthController {

    private static final Logger logger = LoggerFactory.getLogger(OAuthController.class);

    @Inject
    private Result result;
    
    @Path("/teste")
    public void teste() {

    }

    @Inject
    HttpServletRequest rq;
    @Inject
    UserSession userSession;

    @Get("/oauth/login")
    public void getOauthCode() throws OAuthSystemException {
        rq.getSession().invalidate();
        OAuthClientRequest request = OAuthClientRequest
                .authorizationLocation("http://auth.comcidis.lncc.br:3000/oauth/authorize")
                .setClientId("96faaa00d5da1e1117cce031dcf1083356fcc6a3d63b0ca5c29bec7627a00a38")
                .setRedirectURI("http://localhost:8084/rufus/oauth/callback")
                .setResponseType("code")
                .buildQueryMessage();

        result.redirectTo(request.getLocationUri());

    }

    @Get("oauth/callback")
    public void authToken(String code) throws OAuthSystemException, OAuthProblemException {
        OAuthClientRequest request = OAuthClientRequest
                .tokenLocation("http://auth.comcidis.lncc.br:3000/oauth/token")
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .setClientId("96faaa00d5da1e1117cce031dcf1083356fcc6a3d63b0ca5c29bec7627a00a38")
                .setClientSecret("23773eb69593d259ddb981311fa55f2e921b88c49ba156b75491004e6ee028ba")
                .setRedirectURI("http://localhost:8084/rufus/oauth/callback")
                .setCode(code)
                .buildQueryMessage();

        //create OAuth client that uses custom http client under the hood
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

        OAuthJSONAccessTokenResponse oAuthResponse = oAuthClient.accessToken(request);

        String accessToken = oAuthResponse.getAccessToken();
        Long expiresIn = oAuthResponse.getExpiresIn();
        
        userSession.setLogged(true);
        
        rq.getSession().setAttribute("token", accessToken);
        rq.getSession().setAttribute("userSession", userSession);
        
        
        /*result.include("token", accessToken)
                .use(Results.logic()).redirectTo(RufusController.class).dashboard();*/
        result.redirectTo(RufusController.class).login(accessToken);
    }
}
