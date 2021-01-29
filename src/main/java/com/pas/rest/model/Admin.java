package com.pas.rest.model;

import lombok.*;

@Getter
@Setter
public class Admin extends User {
    
    private final String accessLevel = "ADMIN";

    public Admin() {
        super();
    }

    public Admin(String login, String password, String name, String surname) {
        super(login, password, name, surname);
    }

    @Override
    public String toString() {
        return "\nAdministrator" + super.toString();
    }
}
