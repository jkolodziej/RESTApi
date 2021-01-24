package com.pas.rest.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class Book extends Elem {

    @NotNull
    @NotEmpty
    private String author;
    @Min(value = 1)
    @Max(value = 5000)
    private int pages;

    public Book() {
        super();
    }

    public Book(String name, String genre, String author, int pages) {
        super(name, genre);
        this.author = author;
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "\nKsiążka" + super.toString() + "\nAutor: " + author + "\nLiczba stron: " + pages;
    }
}
