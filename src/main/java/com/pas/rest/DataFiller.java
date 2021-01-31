package com.pas.rest;

import com.pas.rest.model.*;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class DataFiller {

    ArrayList<Elem> elems = new ArrayList<>();
    ArrayList<User> users = new ArrayList<>();

    private void fillData() {
        elems.add(new Book("Pet Sematary", "Horror", "Stephen King", 421));
        elems.add(new Book("Harry Potter and the Half-Blood Prince", "Adventure", "J.K. Rowling", 665));
        elems.add(new Book("Feast", "Horror", "Masterton", 542));
        elems.add(new Book("Daily ProphetBook", "Horror", "Masterton", 542));

        elems.add(new Newspaper("NY Times 2020", "Information"));
        elems.add(new Newspaper("Daily Prophet1", "Disinformation"));
        elems.add(new Newspaper("The Washington Post", "Information"));
        elems.add(new Newspaper("Daily Prophet2", "Disinformation"));
        elems.add(new Newspaper("Daily Prophet3", "Disinformation"));

        users.add(new Admin("Simon", "SS2115", "12345678"));
        users.add(new Admin("Charlotte", "CK2115", "12345678"));
        users.add(new ResourceAdmin("Juliet", "JC2115", "12345678"));

        users.add(new Renter("Jaczemir", "JD7", "12345678"));
        users.add(new Renter("Taleleusz", "TF7", "12345678"));
        users.add(new Renter("Abundancja", "AT7", "12345678"));

    }

    private boolean isDataEmpty() {
        return elems.isEmpty() || users.isEmpty();
    }

    public ArrayList<Elem> fillElementsWithData() {
        if (isDataEmpty()) {
            fillData();
        }
        return elems;
    }

    public ArrayList<User> fillUsersWithData() {
        if (isDataEmpty()) {
            fillData();
        }
        return users;
    }

}
