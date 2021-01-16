package com.pas.rest.services;

import com.pas.rest.model.*;
import com.pas.rest.repositories.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RequestScoped
@Path("rents")
public class RentService {

    @Inject
    private RentRepository rentRepository;
    @Inject
    private UserRepository userRepository;
    @Inject
    private ElementRepository elemRepository;


    //CREATE
    @POST
    @Path("{elementID}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void createRent(@PathParam("elementID") String elementID) {
        Elem element = elemRepository.getElementWithID(elementID);
        Renter user = (Renter) userRepository.getUserWithLogin("JD7");
        LocalDate startTime = LocalDate.of(2021,6,12);
        if (element.isRented() || !user.isActive() || startTime.compareTo(LocalDate.now()) < 0 || (user instanceof Renter) == false) {
            throw new IllegalArgumentException("Element is already rented, user is inactive, is not a renter or the date is from past");
        }
        Rent rent = new Rent(element, user, startTime);
        rentRepository.addRent(rent);
        //user.addRent(rent);
        element.setRented(true);
    }

    //READ
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Rent> getRents() {
        return rentRepository.getRents();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Rent findRent(@PathParam("id") String id) {
        return rentRepository.getRentWithID(id);
    }

    @GET
    @Path("/userRents/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public ArrayList<Rent> getAllUserRents(@PathParam("id") String id) {
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
