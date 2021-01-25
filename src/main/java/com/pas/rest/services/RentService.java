package com.pas.rest.services;

import com.pas.rest.managers.ElementManager;
import com.pas.rest.managers.RentManager;
import com.pas.rest.managers.UserManager;
import com.pas.rest.model.*;
import java.time.LocalDate;
import javax.inject.Inject;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
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
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

@RequestScoped
@Path("rents")
public class RentService {

    @Inject
    private RentManager rentManager;
    @Inject
    private UserManager userManager;
    @Inject 
    private ElementManager elementManager;

    //CREATE
    @POST
    @Path("{elementID}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createRent(@PathParam("elementID") String elementID, @Context UriInfo uriInfo, @Context SecurityContext securityContext) {
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        Elem element = elementManager.getElementWithID(elementID);
        Renter user = (Renter) userManager.getUserWithLogin(securityContext.getUserPrincipal().getName());
        LocalDate startTime = LocalDate.now();
        if (element.isRented() || !user.isActive() || (user instanceof Renter) == false) {;
            return Response.status(Status.CONFLICT.getStatusCode(), "Element is already rented or user is inactive").build();
        }
        Rent rent = new Rent(element, user, startTime);
        
        rentManager.createRent(rent);
        element.setRented(true);
        return Response.created(uriBuilder.build())
                .entity(rent)
                .build();
    }

    //READ
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getRents() {
        List<Rent> rents = rentManager.getRents();
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
        Rent rent = rentManager.getRentWithID(id);
        if (rent == null){
            return Response.status(Status.NOT_FOUND.getStatusCode(), "Rent does not exist").build();
        }
        return Response.ok()
                .entity(rent)
                .build();
    }

    @GET
    @Path("self")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllUserRents(@PathParam("id") String id, @Context SecurityContext securityContext) {
        Renter renter = (Renter) userManager.getUserWithLogin(securityContext.getUserPrincipal().getName());
        List<Rent> userRents = rentManager.getAllUserRents(renter.getLogin());
        if (userRents == null){
            return Response.status(Status.NOT_FOUND.getStatusCode(), "").build();
        }
        return Response.ok()
                .entity(userRents)
                .build();
    }

}
