package com.pas.rest.model;

import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@Setter
public class Renter extends User {

    private List<Rent> rents = new ArrayList<>();

    public Renter(String name, String surname, String login, String password) {
        super(name, surname, login, password);
    }

    public void addRent(Rent rent) {
        rents.add(rent);
    }

    @Override
    public String toString() {
        return "\nWypożyczający" + super.toString();
    }
}
