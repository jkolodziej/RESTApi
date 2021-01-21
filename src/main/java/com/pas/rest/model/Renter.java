package com.pas.rest.model;

import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@Setter
public class Renter extends User {

    private List<Rent> rents = new ArrayList<>();
    private final String accessLevel = "RENTER";

    public Renter() {
        super();
    }

    public Renter(String login, String password) {
        super(login, password);
    }

    public void addRent(Rent rent) {
        rents.add(rent);
    }

    @Override
    public String toString() {
        return "\nWypożyczający" + super.toString();
    }
}
