package com.pas.rest.model;

import lombok.*;

@Getter
@Setter
public class Newspaper extends Elem {

    public Newspaper() {
        super();
    }

    public Newspaper(String name, String genre) {
        super(name, genre);
    }

    @Override
    public String toString() {
        return "\nGazeta" + super.toString();
    }

}
