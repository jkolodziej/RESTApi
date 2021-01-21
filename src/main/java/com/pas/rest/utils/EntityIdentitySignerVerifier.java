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
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.pas.rest.SignableEntity;
import java.text.ParseException;

/**
 *
 * @author student
 */
public class EntityIdentitySignerVerifier {

    private static final String SECRET = "Yl0e-ulkfUI06IAqxdJoHXYxcw-mIl5pj1-SrNycbvktlYTINhRABKGDL_8b7hx7xBgZ5E3jNpiO422AV2q97glCd2n8nbYSSiAbus7gI6Qz0YnTZf0ZlqU66v5XqG4S1gECTLqvWBUfap3d-GWFSSAYZjbuhRd-_L3RceGXpR8";

    public static String calculateEntitySignature(SignableEntity entity) {
        try {
            JWSSigner signerJWS = new MACSigner(SECRET);
            JWSAlgorithm JWSAlgorithm = null;
            JWSObject objectJWS = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(entity.getSignablePayload()));
            objectJWS.sign(signerJWS);

            return objectJWS.serialize();

        } catch (JOSEException ex) {
            return "Etag failure";
        }
    }

    public static boolean validateEntitySignature(String header) {

        try {
            JWSObject objectJWS = JWSObject.parse(header);
            JWSVerifier verifier = new MACVerifier(SECRET);

            return objectJWS.verify(verifier);

        } catch (ParseException | JOSEException ex) {
            return false;
        }
    }

    public static boolean verifyEntityIntegrity(String header, SignableEntity entity) {
        try {
            final String ifMatchHeaderValue = JWSObject.parse(header).getPayload().toString();
            final String entitySignablePayloadValue = entity.getSignablePayload();
            
            return validateEntitySignature(header) && ifMatchHeaderValue.equals(entitySignablePayloadValue);
        } catch (ParseException ex) {
            return false;
        }
    }

}
