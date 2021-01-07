package com.pas.rest.model;

import lombok.*;

@Getter
@Setter
public class Book extends Elem {

    private String author;
    private int pages;

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
