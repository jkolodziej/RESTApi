package com.pas.rest.services;

import com.pas.rest.model.*;
import com.pas.rest.repositories.RentRepository;
import com.pas.rest.repositories.UserRepository;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class UserManager {

    private UserRepository userRepository;
    private List<User> currentUsers;
    private RentRepository rentRepository;

    @Inject
    public UserManager(UserRepository userRepository, RentRepository rentRepository) {
        this.userRepository = userRepository;
        this.rentRepository = rentRepository;
    }

    @PostConstruct
    public void initCurrentUsers() {
        currentUsers = userRepository.getAllUsers();
    }

    public User findUserById(String id) {
        return userRepository.findUserById(id);
    }

    public User findUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }

    public List<User> getFilteredUser(String id, String login) {
        return userRepository.getFilteredUser(id, login);
    }

    //aktywowanie i deaktywowanie Użytkownika
    public void changeUserActivity(User user, boolean active) {
        userRepository.changeUserActivity(user, active);
    }

    //operacje CRU
    public void createRenter(String name, String surname, String login, String password) {
        Renter r = new Renter(name, surname, login, password);
        userRepository.add(r);
    }

    public void createResourceAdmin(String name, String surname, String login, String password) {
        ResourceAdmin ra = new ResourceAdmin(name, surname, login, password);
        userRepository.add(ra);
    }

    public void createAdmin(String name, String surname, String login, String password) {
        Admin a = new Admin(name, surname, login, password);
        userRepository.add(a);
    }

    public void modifyUser(User user) {
        userRepository.modifyUser(user);
    }

    public String userString(User user) {
        return userRepository.toString(user);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

}
