package com.pas.rest.model;

import lombok.*;

@Getter
@Setter
public class ResourceAdmin extends User {
    
    private final String accessLevel = "RESOURCEADMIN";

    public ResourceAdmin() {
        super();
    }

    public ResourceAdmin(String name, String login, String password) {
        super(name, login, password);
    }

    @Override
    public String toString() {
        return "\nZarządca zasobów" + super.toString();
    }
}
