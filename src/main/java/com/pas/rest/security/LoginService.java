/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pas.rest.security;

import com.pas.rest.utils.JWTGenerator;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    
    public LoginService() {}
    
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    public Response authenticate(@NotNull AccountData accountData){
        
        Credential credential = new UsernamePasswordCredential(accountData.getLogin(), new Password(accountData.getPassword()));
        CredentialValidationResult result = identityStoreHandler.validate(credential);
        
        if(result.getStatus() == CredentialValidationResult.Status.VALID){
            return Response.accepted()
                    .type("application/jwt")
                    .entity(JWTGenerator.generateJWTString(result))
                    .build();
        }
        return Response.status(Status.UNAUTHORIZED).build();
    }
    
    
    
    
}
