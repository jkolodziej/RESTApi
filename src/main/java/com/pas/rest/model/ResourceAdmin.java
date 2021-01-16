package com.pas.rest.model;

public class ResourceAdmin extends User {

    public ResourceAdmin() {
        super();
    }

    public ResourceAdmin(String login, String password) {
        super(login, password);
    }

    @Override
    public String toString() {
        return "\nZarządca zasobów" + super.toString();
    }
}
