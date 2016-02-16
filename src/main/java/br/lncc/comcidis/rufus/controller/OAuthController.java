/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lncc.comcidis.rufus.controller;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.environment.Property;
import br.lncc.comcidis.rufus.model.Me;
import br.lncc.comcidis.rufus.model.UserSession;
import br.lncc.comcidis.rufus.service.NaoAutenticadoException;
import br.lncc.comcidis.rufus.service.OauthService;
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
    @Property
    private String AUTH_URI;
    
    @Inject
    @Property
    private String RUFUS_WEB_URI;
    
    @Inject
    @Property
    private String  APP_ID;   
    
    @Inject
    @Property
    private String APP_SECRET;
            
    @Inject
    @Property
    private String AUTH_REDIRECT_URI;
    
    @Inject
    @Property
    private String AUTH_CLIENT_REQUEST_URI;
    
    @Inject
    private Result result;
    @Inject
    private UserSession session;
    @Inject
    private HttpServletRequest httpServletRequest;
    @Inject
    private OauthService oauthService;
   

    @Path("/teste")
    public void teste() {

    }

    @Path("/login")
    public void login() throws OAuthSystemException {
        result.redirectTo(this).getOauthCode();
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
                .authorizationLocation(AUTH_URI+"/oauth/authorize")
                .setClientId(APP_ID)
                .setRedirectURI(AUTH_REDIRECT_URI)
                .setResponseType("code")
                .buildQueryMessage();

        result.redirectTo(request.getLocationUri());

    }

    @Get("oauth/callback")
    public void authToken(String code, String requestUri) throws OAuthSystemException, OAuthProblemException {
        
        OAuthClientRequest request = OAuthClientRequest
                .tokenLocation(AUTH_URI+"/oauth/token")
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .setClientId(APP_ID)
                .setClientSecret(APP_SECRET)
                .setRedirectURI(AUTH_REDIRECT_URI)
                .setCode(code)
                .buildQueryMessage();

        //create OAuth client that uses custom http client under the hood
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

        OAuthJSONAccessTokenResponse oAuthResponse = oAuthClient.accessToken(request);

        String accessToken = oAuthResponse.getAccessToken();
        Long expiresIn = oAuthResponse.getExpiresIn();
       
        
         OAuthClientRequest bearerClientRequest = new OAuthBearerClientRequest(AUTH_CLIENT_REQUEST_URI)
                .setAccessToken(oAuthResponse.getAccessToken()).buildQueryMessage();

        OAuthResourceResponse resourceResponse = oAuthClient.resource(bearerClientRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);

        Me me = new Gson().fromJson(resourceResponse.getBody(), Me.class);
        for(String user: oauthService.getRootUsers()){
            if(user.equalsIgnoreCase(me.getEmail())){
                me.setAdmin(true);
            }
        }
        logger.info(me.toString()+" ***********");
      
        try {
            session.authenticate(me);
        } catch (NaoAutenticadoException ex) {

        }

        result.redirectTo(RUFUS_WEB_URI+httpServletRequest.getSession().getAttribute("requestUri"));
    }
    
    @Post("/newUser")
    public void registerNewRootUser(String email){
        oauthService.registerNewRootUser(email);
        result.include("message", "User "+email+" has been added to the admin group.");
        result.redirectTo(RufusController.class).account();
    }
    @Path("/oauth/getRootUsers")
    public void listRootUsers(){
        
        result.include("admins", oauthService.getRootUsers());
    }
    @Get("/oauth/deleteRootUser/{name}")
    public void deleteRootUser(String name){
        oauthService.deleteRootUser(name);
        result.redirectTo(RufusController.class).account();
    }
    
}
