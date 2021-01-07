package com.pas.rest.model;

public class ResourceAdmin extends User {

    public ResourceAdmin() {

    }

    public ResourceAdmin(String name, String surname, String login, String password) {
        super(name, surname, login, password);
    }

    @Override
    public String toString() {
        return "\nZarządca zasobów" + super.toString();
    }
}
