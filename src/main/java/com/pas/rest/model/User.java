package com.pas.rest.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private String id;
    private String name;
    private String surname;
    private String login;
    private String password;
    private boolean active;

    public User() {

    }

    public User(String name, String surname, String login, String password) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.active = true;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\nLogin: " + login + "\nImię: " + name + "\nNazwisko: " + surname + "\nStatus aktywnosci: " + active;
    }

    public void setActivity(boolean active) {
        this.active = active;
    }

    public int getMaxRentDays() {
        return 30;
    }

}
