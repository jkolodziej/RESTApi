package com.pas.rest.services;

import com.pas.rest.model.*;
import com.pas.rest.repositories.RentRepository;
import com.pas.rest.repositories.UserRepository;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RequestScoped
@Path("users")
public class UserService {

    @Inject
    private UserRepository userRepository;
    @Inject
    private RentRepository rentRepository;

    public UserService() {
    }

    //CREATE
    @POST
    @Path("/renters")
    @Consumes({MediaType.APPLICATION_JSON})
    public void createRenter(Renter renter) {
        userRepository.addUser(renter);
    }

    @POST
    @Path("/resourceAdmins")
    @Consumes({MediaType.APPLICATION_JSON})
    public void createResourceAdmin(ResourceAdmin resourceAdmin) {
        userRepository.addUser(resourceAdmin);
    }

    @POST
    @Path("/admins")
    @Consumes({MediaType.APPLICATION_JSON})
    public void createAdmin(Admin admin) {
        userRepository.addUser(admin);
    }

    //READ
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    @GET
    @Path("{login}")
    @Produces({MediaType.APPLICATION_JSON})
    public User findUserByLogin(@PathParam("login") String login) {
        return userRepository.getUserWithLogin(login);
    }

    //UPDATE
    @PUT
    @Path("/renters/{login}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void modifyUser(@PathParam("login") String login, Renter user) {
        userRepository.modifyUser(login, user);
    }
    
    //UPDATE
    @PUT
    @Path("/resourceAdmins/{login}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void modifyUser(@PathParam("login") String login, ResourceAdmin user) {
        userRepository.modifyUser(login, user);
    }
    
    //UPDATE
    @PUT
    @Path("/admins/{login}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void modifyUser(@PathParam("login") String login, Admin user) {
        userRepository.modifyUser(login, user);
    }
}
