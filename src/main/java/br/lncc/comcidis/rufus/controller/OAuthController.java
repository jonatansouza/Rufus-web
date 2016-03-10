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
import br.lncc.comcidis.rufus.factory.HTMLSanitizer;
import br.lncc.comcidis.rufus.factory.HostInterface;
import br.lncc.comcidis.rufus.model.Host;
import br.lncc.comcidis.rufus.model.Hosts;
import br.lncc.comcidis.rufus.model.Me;
import br.lncc.comcidis.rufus.model.UserSession;
import br.lncc.comcidis.rufus.service.NaoAutenticadoException;
import br.lncc.comcidis.rufus.service.OauthService;
import com.google.gson.Gson;
import javax.enterprise.context.RequestScoped;

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
@RequestScoped
public class OAuthController {

    private static final Logger logger = LoggerFactory.getLogger(OAuthController.class);

    private Result result;
    private UserSession session;
    private HttpServletRequest httpServletRequest;
    private OauthService oauthService;
    private Host auth;
    private Host web;

    @Deprecated
    public OAuthController() {

    }

    @Inject
    public OAuthController(Result result, UserSession session, HttpServletRequest httpServletRequest, OauthService oauthService, HttpServletRequest rq, UserSession userSession, @HostInterface Hosts hosts) {
        this.result = result;
        this.session = session;
        this.httpServletRequest = httpServletRequest;
        this.oauthService = oauthService;
        this.httpServletRequest = httpServletRequest;
        this.session = session;
        this.auth = hosts.get("auth");
        this.web = hosts.get("web");
    }

    @Path("/login")
    public void login() throws OAuthSystemException {
        result.redirectTo(this).getOauthCode();
    }

    @Path("oauth/logout")
    public void logout() {
        session.logout();
        result.redirectTo(RufusController.class).index();
    }

    @Get("oauth/login")
    public void getOauthCode() throws OAuthSystemException {
        OAuthClientRequest request = OAuthClientRequest
                .authorizationLocation(auth.getUrl() + "/oauth/authorize")
                .setClientId(auth.getAuthSetup().getAppID())
                .setRedirectURI(web.getUrl() + auth.getAuthSetup().getAppEndPoint())
                .setResponseType("code")
                .buildQueryMessage();

        result.redirectTo(request.getLocationUri());

    }

    @Get("oauth/callback")
    public void authToken(String code, String requestUri) throws OAuthSystemException, OAuthProblemException {
        OAuthClientRequest request = OAuthClientRequest
                .tokenLocation(auth.getUrl() + "/oauth/token")
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .setClientId(auth.getAuthSetup().getAppID())
                .setClientSecret(auth.getAuthSetup().getAppSecret())
                .setRedirectURI(web.getUrl() + auth.getAuthSetup().getAppEndPoint())
                .setCode(code)
                .buildQueryMessage();

        //create OAuth client that uses custom http client under the hood
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

        OAuthJSONAccessTokenResponse oAuthResponse = oAuthClient.accessToken(request);

        String accessToken = oAuthResponse.getAccessToken();
        Long expiresIn = oAuthResponse.getExpiresIn();

        OAuthClientRequest bearerClientRequest = new OAuthBearerClientRequest(auth.getUrl() + auth.getAuthSetup().getClientEndPoint())
                .setAccessToken(oAuthResponse.getAccessToken()).buildQueryMessage();

        OAuthResourceResponse resourceResponse = oAuthClient.resource(bearerClientRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);

        Me me = new Gson().fromJson(resourceResponse.getBody(), Me.class);
        for (String user : oauthService.getRootUsers()) {
            if (user.equalsIgnoreCase(me.getEmail())) {
                me.setAdmin(true);
            }
        }
        logger.info(me.toString() + " ***********");

        try {
            session.authenticate(me);
        } catch (NaoAutenticadoException ex) {

        }
        result.redirectTo(web.getUrl() + "" + httpServletRequest.getSession().getAttribute("requestUri"));
    }

    @Post("/newUser")
    public void registerNewRootUser(String email) {
        logger.info(email);
        String register = HTMLSanitizer.saniteze(email);
        logger.info(register);
        String msg = "";
        if (!register.isEmpty()) {
            oauthService.registerNewRootUser(register);
            msg = "User added!";
        }else{
            msg = "Email not valid!";
        }

        result.redirectTo(RufusController.class).account(msg);

    }

    @Path("/oauth/getRootUsers")
    public void listRootUsers() {

        result.include("admins", oauthService.getRootUsers());
    }

    @Get("/oauth/deleteRootUser/{name}")
    public void deleteRootUser(String name) {
        
        oauthService.deleteRootUser(HTMLSanitizer.saniteze(name));
        result.redirectTo(RufusController.class).account("User deleted!!");
    }

}
