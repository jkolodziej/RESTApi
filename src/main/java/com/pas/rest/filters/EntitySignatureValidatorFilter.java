/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pas.rest.filters;

import com.pas.rest.utils.EntityIdentitySignerVerifier;
import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author student
 */
@Provider
@EntitySignatureValidatorFilterBinding
public class EntitySignatureValidatorFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String header = requestContext.getHeaderString("If-Match");
        if(header == null || header.isEmpty()){
            requestContext.abortWith(Response.status(Response.Status.BAD_REQUEST).build());
        } else if(!EntityIdentitySignerVerifier.validateEntitySignature(header)){
            requestContext.abortWith(Response.status(Response.Status.PRECONDITION_FAILED).build());
        }
    }
    
}
