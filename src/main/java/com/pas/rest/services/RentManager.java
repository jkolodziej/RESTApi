package com.pas.rest.services;

import com.pas.rest.model.*;
import com.pas.rest.repositories.*;
import java.util.ArrayList;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class RentManager {

    @Inject
    private RentRepository rentRepository;
    @Inject
    private UserRepository userRepository;
    @Inject
    private ElementRepository elemRepository;
    private List<Rent> currentRents;

    public void initCurrentRents() {
        currentRents = rentRepository.getAllRents();
    }

    @PostConstruct
    public void fillRepo() {
        if (rentRepository.getAllRents().isEmpty()) {
            for (int i = 0; i < userRepository.getAllRenters().size(); i++) {
                createRent(userRepository.getAllRenters().get(i), elemRepository.getElements().get(i), new Date());
            }
        }
        initCurrentRents();
    }

    public void createRent(Renter user, Elem element, Date startTime) {
        if (element.isRented() || !user.isActive() || startTime.compareTo(new Date()) < 0 || (user instanceof Renter) == false) {
            throw new IllegalArgumentException("Element is already rented, user is inactive, is not a renter or the date is from past");
        }
        Rent rent = new Rent(element, user, startTime);
        rentRepository.add(rent);
        user.addRent(rent);
        element.setRented(true);
    }

    public void removeRent(Rent rent) {
        rentRepository.remove(rent);
    }

    public void endRent(Rent rent) {
        rentRepository.endRent(rent);
    }

    public List<Rent> getAllRents() {
        return currentRents;
    }

    public ArrayList<Rent> getAllUserRents(String id) {
        return rentRepository.getAllUserRents(id);
    }

    public ArrayList<Rent> getAllElementRents(String id) {
        return rentRepository.getAllElementRents(id);
    }

    public ArrayList<Rent> getFilteredRents(String userId, String elementId) {
        ArrayList<Rent> tempRents = new ArrayList<>();
        for (int i = 0; i < currentRents.size(); i++) {
            if ((userId.equals("") || currentRents.get(i).getUser().getId().equals(userId))
                    && elementId.equals("") || currentRents.get(i).getElement().getId().equals(elementId)) {
                tempRents.add(currentRents.get(i));
            }
        }
        return tempRents;
    }

    public Rent findRent(String id) {
        return rentRepository.findRent(id);
    }

    public boolean isRented(Elem element) {
        for (Rent rent : rentRepository.getAllRents()) {
            if (rent.getElement().equals(element) && rent.getElement().isRented()) {
                return true;
            }
        }

        return false;
    }
}
