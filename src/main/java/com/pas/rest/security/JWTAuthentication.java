/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pas.rest.security;

import com.nimbusds.jwt.SignedJWT;
import com.pas.rest.utils.JWTGenerator;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author student
 */
@ApplicationScoped
public class JWTAuthentication implements HttpAuthenticationMechanism {

    private static final Logger LOG = Logger.getLogger(JWTAuthentication.class.getName());
    public final static String AUTHORIZATION_HEADER = "Authorization";
    public final static String BEARER = "Bearer";

    @Inject
    private JWTGenerator generatorJWT;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext context) throws AuthenticationException {

        if (!request.getRequestURL().toString().endsWith("/resources/authenticate")) {
            String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
            if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER)) {
                return context.responseUnauthorized();
            }
            String serializedJWTString = authorizationHeader.substring(BEARER.length()).trim();
            if (JWTGenerator.validateJWTSignature(serializedJWTString)) {
                try {
                    SignedJWT signedJWT = SignedJWT.parse(serializedJWTString);
                    String login = signedJWT.getJWTClaimsSet().getSubject();
                    String groups = signedJWT.getJWTClaimsSet().getStringClaim("auth");
                    Date expirationTime = (Date) (signedJWT.getJWTClaimsSet().getClaim("exp"));
                    //boolean tokenExpired = new Date().after(expirationTime);

                    if (new Date().after(expirationTime)) {
                        return context.responseUnauthorized();
                    }

                    return context.notifyContainerAboutLogin(login, new HashSet<>(Arrays.asList(groups.split(","))));

                } catch (ParseException ex) {
                    return context.responseUnauthorized();
                }
            }
            return context.responseUnauthorized();
        } else {
            return context.doNothing();
        }
    }
}
