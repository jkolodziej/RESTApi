package com.pas.rest.model;

import java.time.LocalDate;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class Rent {

    private String id;
    @NotNull
    private Elem element = new Elem();
    @NotNull
    private User user = new User();
    @NotNull
    @FutureOrPresent
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
