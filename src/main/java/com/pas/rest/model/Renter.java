package com.pas.rest.model;

import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@Setter
public class Renter extends User {

    private final String accessLevel = "RENTER";

    public Renter() {
        super();
    }

    public Renter(String login, String password, String name, String surname) {
        super(login, password, name, surname);
    }
    
    @Override
    public String toString() {
        return "\nWypożyczający" + super.toString();
    }
}
