package com.pas.rest.model;

import java.util.Date;
import lombok.*;

@Getter
@Setter
public class Rent {

    private String id;
    private Elem element = new Elem();
    private User user = new User();
    private Date startDate;
    private Date endDate;

    public Rent(Elem rentedElement, User rentingUser, Date startDate) {
        this.element = rentedElement;
        this.user = rentingUser;
        this.startDate = startDate;
        this.endDate = null;
    }

    @Override
    public String toString() {
        return "\nID: " + id + "\nLogin: " + user.getLogin() + "\nImię: " + user.getName() + "\nNazwisko: " + user.getSurname() + "\nTytuł: " + element.getName()
                + "\nData rozpoczęcia: " + startDate + "\nData zakończenia: " + endDate;
    }

    public int getDaysOver() {
        if (endDate == null) {
            return 0;
        }
        int duration = (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
        int daysOver = duration - user.getMaxRentDays();
        return Math.max(daysOver, 0);
    }

}
