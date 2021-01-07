package com.pas.rest.repositories;

import java.util.ArrayList;
import java.util.Date;
import com.pas.rest.ID;
import com.pas.rest.model.Rent;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RentRepository {

    private final ArrayList<Rent> rents;

    public RentRepository() {
        this.rents = new ArrayList<>();
    }

    public void add(Rent rent) {
        synchronized (rents) {
            if (rent == null) {
                throw new IllegalArgumentException("Próba dodania wypożyczenia bez podania danych");
            }
            rent.setId(ID.generateID());
            rents.add(rent);
        }
    }

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
    
    public void endRent(Rent rent) {
        synchronized (rents) {
            for (Rent r : rents) {
                if (r.getId().equals(rent.getId()) && r.getEndDate() == null) {
                    rent.setEndDate(new Date());
                    rent.getElement().setRented(false);
                }
            }
        }
    }

    public ArrayList<Rent> getAllRents() {
        synchronized (rents) {
            return new ArrayList<>(rents);
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
    
    public ArrayList<Rent> getAllElementRents(String id) {
        synchronized (rents) {
            ArrayList<Rent> elemRents = new ArrayList<>();
            for (Rent rent : rents) {
                if (rent.getElement().getId().equals(id)) {
                    elemRents.add(rent);
                }
            }
            return elemRents;
        }
    }

    public Rent findRent(String id) {
        synchronized (rents) {
            for (Rent rent : rents) {
                if (rent.getId().equals(id)) {
                    return rent;
                }
            }

            return null;
        }
    }
}
