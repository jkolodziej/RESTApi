package com.pas.rest.repositories;

import java.util.ArrayList;
import com.pas.rest.ID;
import com.pas.rest.model.Rent;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RentRepository {

    private final ArrayList<Rent> rents;

    public RentRepository() {
        this.rents = new ArrayList<>();
    }

    //CREATE
    public void addRent(Rent rent) {
        synchronized (rents) {
            if (rent == null) {
                throw new IllegalArgumentException("Próba dodania wypożyczenia bez podania danych");
            }
            rent.setId(ID.generateID());
            rents.add(rent);
        }
    }

    //READ
    public ArrayList<Rent> getRents() {
        synchronized (rents) {
            return new ArrayList<>(rents);
        }
    }

    public Rent getRentWithID(String id) {
        synchronized (rents) {
            for (Rent rent : rents) {
                if (rent.getId().equals(id)) {
                    return rent;
                }
            }

            return null;
        }
    }

    public ArrayList<Rent> getAllUserRents(String id) {
        synchronized (rents) {
            ArrayList<Rent> userRents = new ArrayList<>();
            for (Rent rent : rents) {
                if (rent.getUser().getId().equals(id)) {
                    userRents.add(rent);
                }
            }
            return userRents;
        }
    }

    //DELETE
    public void remove(Rent rent) {
        synchronized (rents) {
            if (rent.getEndDate() == null) {
                rent.getElement().setRented(false);
                for (int i = 0; i < rents.size(); i++) {
                    Rent r = rents.get(i);
                    if (r.getId().equals(rent.getId())) {
                        rents.remove(i);
                    }
                }
            }
        }
    }

}
