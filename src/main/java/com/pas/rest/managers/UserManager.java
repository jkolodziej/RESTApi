/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pas.rest.managers;

import com.pas.rest.model.User;
import com.pas.rest.repositories.UserRepository;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author student
 */
public class UserManager {

    @Inject
    UserRepository userRepository;

    // CREATE
    public void creatUser(User user) {
        userRepository.addUser(user);
    }

    // READ
    public List<User> getAllUsers() {
        return userRepository.getUsers();
    }

    public User getUserWithID(String id) {
        return userRepository.getUserWithID(id);
    }

    public User getUserWithLogin(String login) {
        return userRepository.getUserWithLogin(login);
    }

    // UPDATE
    public void modifyUser(String login, User user) {
        userRepository.modifyUser(login, user);
    }
    
    public boolean checkIfUserIsActive(String login){
         return userRepository.getUserWithLogin(login).isActive();
    }

}
