package com.pas.rest.model;

import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
public class Rent {

    private String id;
    private Elem element = new Elem();
    private User user = new User();
    private LocalDate startDate;
    private LocalDate endDate;
    
    public Rent() {}

    public Rent(Elem rentedElement, User rentingUser, LocalDate startDate) {
        this.element = rentedElement;
        this.user = rentingUser;
        this.startDate = startDate;
        this.endDate = null;
    }
}
