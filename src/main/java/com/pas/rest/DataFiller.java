package com.pas.rest;

import com.pas.rest.model.*;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class DataFiller {

    ArrayList<User> users = new ArrayList<>();

    private void fillData() {

        users.add(new Admin("SS2115", "12345678", "Simon", "Sparrowtwooneonefive"));
        users.add(new Admin("CK2115", "12345678", "Caroline", "Kovaltzyk"));
        users.add(new ResourceAdmin("JC2115", "12345678", "Juliet", "Circle"));

        users.add(new Renter("JD7", "12345678", "John", "Dooo"));
        users.add(new Renter("TF7", "12345678", "Twisted", "Fate"));
        users.add(new Renter("AT7", "12345678", "Annie", "Tibbers"));
    }

    private boolean isDataEmpty() {
        return users.isEmpty();
    }

    public ArrayList<User> fillUsersWithData() {
        if (isDataEmpty()) {
            fillData();
        }
        return users;
    }

}
