package com.pas.rest.repositories;

import com.pas.rest.DataFiller;
import com.pas.rest.ID;
import com.pas.rest.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository {

    private final ArrayList<User> users = new ArrayList<>();

    public UserRepository() {
        DataFiller dataFiller = new DataFiller();
        for (int i = 0; i < dataFiller.fillUsersWithData().size(); i++) {
            addUser(dataFiller.fillUsersWithData().get(i));
        }
    }

    //CREATE
    public void addUser(User user) throws IllegalArgumentException {
        synchronized (users) {
            if (user == null) {
                throw new NullPointerException("Próba dodania użytkownika bez podania danych");
            }
            for (User u : users) {
                if (u.getLogin().equals(user.getLogin())) {
                    throw new IllegalArgumentException("User with such login already exists");
                } else if (u.getId().equals(user.getId())) {
                    throw new IllegalArgumentException("User with such Id already exists");
                }
            }
            user.setId(ID.generateID());
            users.add(user);
        }
    }

    //READ
    public List<User> getUsers() {
        synchronized (users) {
            return new ArrayList<>(users);
        }
    }

    public User getUserWithID(String id) {
        synchronized (users) {
            for (User user : users) {
                if (user.getId().equals(id)) {
                    return user;
                }
            }
            return null;
        }
    }

    public User getUserWithLogin(String login) {
        synchronized (users) {
            for (User user : users) {
                if (user.getLogin().equals(login)) {
                    return user;
                }
            }
            return null;
        }
    }
    
    public User getActiveUserWithLoginPassword(String login, String password) {
        synchronized (users) {
            for (User user : users) {
                if (user.getLogin().equals(login) && user.getPassword().equals(password) && user.isActive()) {
                    return user;
                }
            }
            return null;
        }
    }

    //UPDATE
    public void modifyUser(String login, User user) {
        synchronized (users) {
            String userID = getUserWithLogin(login).getId();
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getLogin().equals(login)) {
                    if (user.getLogin() != null) {
                        users.get(i).setLogin(user.getLogin());
                    }
                    if (user.getPassword() != null) {
                        users.get(i).setPassword(user.getPassword());
                    }
                    users.get(i).setId(userID);
                }
            }
        }
    }

}
