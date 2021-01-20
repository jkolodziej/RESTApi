/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pas.rest.security;

import com.pas.rest.model.User;
import com.pas.rest.repositories.UserRepository;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

/**
 *
 * @author student
 */
@ApplicationScoped
public class CustomIdentityStore implements IdentityStore {

    private static final Logger LOG = Logger.getLogger(CustomIdentityStore.class.getName());

    @Inject
    private UserRepository userRepository;

    @Override
    public Set<ValidationType> validationTypes() {
        return IdentityStore.super.validationTypes();
    }

    @Override
    public int priority() {
        return IdentityStore.super.priority();
    }

    @Override
    public Set<String> getCallerGroups(CredentialValidationResult validationResult) {
        return IdentityStore.super.getCallerGroups(validationResult);
    }

    public CredentialValidationResult validate(UsernamePasswordCredential credential) {

        if (credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential usernamePassword = (UsernamePasswordCredential) credential;
            User user = userRepository.getActiveUserWithLoginPassword(usernamePassword.getCaller(), usernamePassword.getPasswordAsString());
            LOG.log(Level.SEVERE, "validation of login {0} resulted in {1}", new Object[]{usernamePassword.getCaller(), user});
            if (user != null && user.getPassword().equals(usernamePassword.getPasswordAsString())) {
                return new CredentialValidationResult(usernamePassword.getCaller());
            } else {
                return CredentialValidationResult.INVALID_RESULT;
            }
        }
        return CredentialValidationResult.NOT_VALIDATED_RESULT;
    }
}
