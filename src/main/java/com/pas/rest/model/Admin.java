package com.pas.rest.model;

public class Admin extends User {

    public Admin() {

    }

    public Admin(String name, String surname, String login, String password) {
        super(name, surname, login, password);
    }

    @Override
    public String toString() {
        return "\nAdministrator" + super.toString();
    }
}
