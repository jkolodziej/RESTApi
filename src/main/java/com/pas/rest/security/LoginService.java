/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pas.rest.security;

import com.nimbusds.jwt.SignedJWT;
import com.pas.rest.managers.UserManager;
import static com.pas.rest.security.JWTAuthentication.AUTHORIZATION_HEADER;
import static com.pas.rest.security.JWTAuthentication.BEARER;
import com.pas.rest.utils.JWTGenerator;
import java.text.ParseException;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author student
 */
@RequestScoped
@Path("authenticate")
public class LoginService {

    private static final Logger LOG = Logger.getLogger(LoginService.class.getName());

    @Inject
    private IdentityStoreHandler identityStoreHandler;
    @Inject
    private UserManager userManager;

    public LoginService() {
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    public Response authenticate(@NotNull AccountData accountData) {

        Credential credential = new UsernamePasswordCredential(accountData.getLogin(), new Password(accountData.getPassword()));
        CredentialValidationResult result = identityStoreHandler.validate(credential);

        if (result.getStatus() == CredentialValidationResult.Status.VALID) {
            return Response.accepted()
                    .type("application/jwt")
                    .entity(JWTGenerator.generateJWTString(result))
                    .build();
        }
        return Response.status(Status.UNAUTHORIZED).build();
    }

    @GET
    @Produces({MediaType.TEXT_PLAIN})
    public Response updateToken(@Context HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader(JWTAuthentication.AUTHORIZATION_HEADER);
        String serializedJWTString = authorizationHeader.substring(JWTAuthentication.BEARER.length()).trim();
        String login = null;
        try {
            login = SignedJWT.parse(serializedJWTString).getJWTClaimsSet().getSubject();
            if (userManager.checkIfUserIsActive(login)) {
                return Response.accepted()
                        .type("application/json")
                        .entity(JWTGenerator.updateJWTString(serializedJWTString))
                        .build();
            } else {
                return Response.status(Status.FORBIDDEN).build();
            }
        } catch (ParseException ex) {
            return Response.status(418).build();
        }
    }
}
