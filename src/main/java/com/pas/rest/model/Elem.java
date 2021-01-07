package com.pas.rest.model;

import lombok.*;

@Getter
@Setter
public class Elem {

    private String id;
    private String name;
    private String genre;
    private boolean rented;

    public Elem(String name, String genre) {
        this.name = name;
        this.genre = genre;
        this.rented = false;
    }

    Elem() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "\nTytuł: " + name + "\nGatunek: " + genre;
    }
}
