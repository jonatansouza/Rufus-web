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
import br.lncc.comcidis.rufus.model.Me;
import br.lncc.comcidis.rufus.model.UserSession;
import br.lncc.comcidis.rufus.service.NaoAutenticadoException;
import com.google.gson.Gson;

import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
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
    @Inject
    private UserSession session;
    @Inject
    private HttpServletRequest httpServletRequest;
    
   

    @Path("/teste")
    public void teste() {

    }

    @Path("/login")
    public void login() {

    }
    @Path("oauth/logout")
    public void logout(){
        userSession.logout();
        result.redirectTo(RufusController.class).index();
    }

    @Inject
    HttpServletRequest rq;
    @Inject
    UserSession userSession;

    @Get("oauth/login")
    public void getOauthCode() throws OAuthSystemException {
        
        OAuthClientRequest request = OAuthClientRequest
                .authorizationLocation("http://auth.comcidis.lncc.br:3000/oauth/authorize")
                .setClientId("d61755e9a74f4645fd269acae0c7d8db865af5ec49bc55b991bdfbb80a3eed2e")
                .setRedirectURI("http://localhost:8084/rufus/oauth/callback")
                .setResponseType("code")
                .buildQueryMessage();

        result.redirectTo(request.getLocationUri());

    }

    @Get("oauth/callback")
    public void authToken(String code, String requestUri) throws OAuthSystemException, OAuthProblemException {
        
        OAuthClientRequest request = OAuthClientRequest
                .tokenLocation("http://auth.comcidis.lncc.br:3000/oauth/token")
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .setClientId("d61755e9a74f4645fd269acae0c7d8db865af5ec49bc55b991bdfbb80a3eed2e")
                .setClientSecret("df633872206a3d6ca65bf406bfe2c3856ba8331b57becc5c4f8d7e08e5edd0c0")
                .setRedirectURI("http://localhost:8084/rufus/oauth/callback")
                .setCode(code)
                .buildQueryMessage();

        //create OAuth client that uses custom http client under the hood
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

        OAuthJSONAccessTokenResponse oAuthResponse = oAuthClient.accessToken(request);

        String accessToken = oAuthResponse.getAccessToken();
        Long expiresIn = oAuthResponse.getExpiresIn();
       
        
         OAuthClientRequest bearerClientRequest = new OAuthBearerClientRequest("http://auth.comcidis.lncc.br:3000/api/v1/me.json")
                .setAccessToken(oAuthResponse.getAccessToken()).buildQueryMessage();

        OAuthResourceResponse resourceResponse = oAuthClient.resource(bearerClientRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);

        Me me = new Gson().fromJson(resourceResponse.getBody(), Me.class);

        try {
            session.authenticate(me);
        } catch (NaoAutenticadoException ex) {

        }

       
        logger.info("********************************");
        logger.info(httpServletRequest.getSession().getAttribute("requestUri")+" redirect uri");
        logger.info(me.getName());
        logger.info(me.getEmail());
        result.redirectTo("http://localhost:8084"+httpServletRequest.getSession().getAttribute("requestUri"));
    }
}
