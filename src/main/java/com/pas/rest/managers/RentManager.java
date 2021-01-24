/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pas.rest.managers;

import com.pas.rest.model.Elem;
import com.pas.rest.model.Rent;
import com.pas.rest.repositories.ElementRepository;
import com.pas.rest.repositories.RentRepository;
import com.pas.rest.repositories.UserRepository;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author student
 */
public class RentManager {

    @Inject
    private RentRepository rentRepository;
    @Inject
    private UserRepository userRepository;
    @Inject
    private ElementRepository elementRepository;

    // CREATE
    public void createRent(Rent rent) {
        rentRepository.addRent(rent);
    }

    // READ
    public List<Rent> getRents() {
        return rentRepository.getRents();
    }

    public Rent getRentWithID(String id) {
        return rentRepository.getRent(id);
    }

    public List<Rent> getAllUserRents(String id) {
        return rentRepository.getAllUserRents(id);
    }
    
    public boolean isRented(Elem element) {
        for (Rent rent : rentRepository.getRents()) {
            if (rent.getElement().equals(element) && rent.getElement().isRented()) {
                return true;
            }
        }

        return false;
    }
}
