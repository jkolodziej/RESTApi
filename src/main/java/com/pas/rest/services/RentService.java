package com.pas.rest.services;

import com.pas.rest.model.*;
import com.pas.rest.repositories.*;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.inject.Inject;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

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
    public Response createRent(@PathParam("elementID") String elementID, @Context UriInfo uriInfo) {
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        Elem element = elemRepository.getElementWithID(elementID);
        Renter user = (Renter) userRepository.getUserWithLogin("JD7");
        LocalDate startTime = LocalDate.of(2021,6,12);
        if (element.isRented() || !user.isActive() || startTime.compareTo(LocalDate.now()) < 0 || (user instanceof Renter) == false) {
            //throw new IllegalArgumentException("Element is already rented, user is inactive, is not a renter or the date is from past");
            return Response.status(Status.CONFLICT.getStatusCode(), "Element is already rented, user is inactive, is not a renter or the date is from past").build();
        }
        Rent rent = new Rent(element, user, startTime);
        rentRepository.addRent(rent);
        //user.addRent(rent);
        element.setRented(true);
        return Response.created(uriBuilder.build())
                .entity(rent)
                .build();
    }

    //READ
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getRents() {
        List<Rent> rents = rentRepository.getRents();
        if (rents == null) {
            return Response.status(Status.NOT_FOUND.getStatusCode(), "Rent repository does not exist").build();
        }
        return Response.ok()
                .entity(rents)
                .build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findRent(@PathParam("id") String id) {
        Rent rent = rentRepository.getRentWithID(id);
        if (rent == null){
            return Response.status(Status.NOT_FOUND.getStatusCode(), "Rent does not exist").build();
        }
        return Response.ok()
                .entity(rent)
                .build();
    }

    @GET
    @Path("/userRents/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllUserRents(@PathParam("id") String id) {
        List<Rent> userRents = rentRepository.getAllUserRents(id);
        if (userRents == null){
            return Response.status(Status.NOT_FOUND.getStatusCode(), "").build();
        }
        return Response.ok()
                .entity(userRents)
                .build();
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
