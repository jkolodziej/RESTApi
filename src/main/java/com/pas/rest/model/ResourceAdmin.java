package com.pas.rest.model;

import lombok.*;

@Getter
@Setter
public class ResourceAdmin extends User {
    
    private final String accessLevel = "RESOURCEADMIN";

    public ResourceAdmin() {
        super();
    }

    public ResourceAdmin(String login, String password, String name, String surname) {
        super(login, password, name, surname);
    }

    @Override
    public String toString() {
        return "\nZarządca zasobów" + super.toString();
    }
}
