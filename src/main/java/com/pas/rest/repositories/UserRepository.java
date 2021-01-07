package com.pas.rest.repositories;

import com.pas.rest.DataFiller;
import com.pas.rest.ID;
import com.pas.rest.model.Renter;
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
            add(dataFiller.fillUsersWithData().get(i));
        }
    }

    public User findUserById(String id) {
        synchronized (users) {
            for (User user : users) {
                if (user.getId().equals(id)) {
                    return user;
                }
            }
            return null;
        }
    }

    public User findUserByLogin(String login) {
        synchronized (users) {
            for (User user : users) {
                if (user.getLogin().equals(login)) {
                    return user;
                }
            }
            return null;
        }
    }

    public List<User> getFilteredUser(String id, String login) {
        List<User> list = new ArrayList<>();
        synchronized (users) {
            for (User user : users) {
                if ((id.equals("") || user.getId().startsWith(id))
                        && (login.equals("") || user.getLogin().startsWith(login))) {
                    list.add(user);
                }
            }
            if (list.isEmpty()) {
                return List.of(findUserById(id));
            }
            return list;
        }
    }

    public void modifyUser(User user) {
        synchronized (users) {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).equals(user)) {
                    users.set(i, user);
                }
            }
        }
    }

    public void add(User user) {
        synchronized (users) {
            if (user == null) {
                throw new IllegalArgumentException("Próba dodania użytkownika bez podania danych");
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

    public List<User> getAllUsers() {
        synchronized (users) {
            return new ArrayList<>(users);
        }
    }

    public void changeUserActivity(User user, boolean active) {
        synchronized (users) {
            user.setActivity(active);
        }
    }

    public String toString(User user) {
        synchronized (users) {
            for (User u : users) {
                if (user.getId().equals(u.getId())) {
                    return user.toString();
                }
            }
            return "W bazie nie ma takiego użytkownika";
        }
    }

    public List<Renter> getAllRenters() {
        List<Renter> renters = new ArrayList<>();
        for (User u : users) {
            if (u instanceof Renter) {
                renters.add((Renter) u);
            }
        }
        return renters;
    }

}
