package com.pas.rest.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class Elem {

    private String id;
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private String genre;
    private boolean rented;

    public Elem() {
    }

    public Elem(String name, String genre) {
        this.name = name;
        this.genre = genre;
        this.rented = false;
    }

    @Override
    public String toString() {
        return "\nTytuł: " + name + "\nGatunek: " + genre;
    }
}
