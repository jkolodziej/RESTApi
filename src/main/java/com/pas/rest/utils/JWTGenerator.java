/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pas.rest.utils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import java.text.ParseException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.enterprise.identitystore.CredentialValidationResult;

/**
 *
 * @author student
 */
public class JWTGenerator {

    private static final String SECRET = "Yl0e-ulkfUI06IAqxdJoHXYxcw-mIl5pj1-SrNycbvktlYTINhRABKGDL_8b7hx7xBgZ5E3jNpiO422AV2q97glCd2n8nbYSSiAbus7gI6Qz0YnTZf0ZlqU66v5XqG4S1gECTLqvWBUfap3d-GWFSSAYZjbuhRd-_L3RceGXpR8";
    private static final long JWT_TIMEOUT = 10 * 60000;

    public static String generateJWTString(CredentialValidationResult credential) {

        try {
            final JWSSigner signerJWS = new MACSigner(SECRET);
            final JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(credential.getCallerPrincipal().getName())
                    .claim("auth", String.join(",", credential.getCallerGroups()))
                    .issuer("Rest API")
                    .expirationTime(new Date(new Date().getTime() + JWT_TIMEOUT))
                    .build();
            
            final SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);  
            signedJWT.sign(new MACSigner(SECRET));
        
            return signedJWT.serialize();
            
        } catch (JOSEException ex) {
            Logger.getLogger(JWTGenerator.class.getName()).log(Level.SEVERE, null, ex);
            return "JWT failure"; 
        }
    }
    
    public static String updateJWTString(String serializedJWTString){
        SignedJWT previousSignedJWT = null;
        try{
            final JWSSigner signer = new MACSigner(SECRET);
            previousSignedJWT = SignedJWT.parse(serializedJWTString);
            final JWTClaimsSet previousJWTClaimsSet = previousSignedJWT.getJWTClaimsSet();
            final JWTClaimsSet newJWTClaimsSet = new JWTClaimsSet.Builder()
                    .subject(previousJWTClaimsSet.getSubject())
                    .claim("auth", previousJWTClaimsSet.getClaim("auth"))
                    .issuer(previousJWTClaimsSet.getIssuer())
                    .expirationTime(new Date(new Date().getTime() + JWT_TIMEOUT))
                    .build();
            
            final SignedJWT newSignedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), newJWTClaimsSet);
            newSignedJWT.sign(signer);
            return newSignedJWT.serialize();
        } catch(JOSEException | ParseException ex){
            Logger.getLogger(JWTGenerator.class.getName()).log(Level.SEVERE, null, ex);
            return "JWT failure"; 
        }
    }
    
    public static boolean validateJWTSignature(String serializedJWTString){
        try{
            final JWSObject objectJWS = JWSObject.parse(serializedJWTString); 
            final JWSVerifier verifierJWS = new MACVerifier(SECRET);
            return objectJWS.verify(verifierJWS);
        } catch(JOSEException | ParseException ex){
            Logger.getLogger(JWTGenerator.class.getName()).log(Level.SEVERE, null, ex);
            return false; 
        }
    }
}
