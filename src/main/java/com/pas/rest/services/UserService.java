package com.pas.rest.services;

import com.nimbusds.jose.JOSEException;
import com.pas.rest.filters.EntitySignatureValidatorFilterBinding;
import com.pas.rest.model.*;
import com.pas.rest.repositories.RentRepository;
import com.pas.rest.repositories.UserRepository;
import com.pas.rest.utils.EntityIdentitySignerVerifier;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
    public Response createRenter(Renter renter, @Context UriInfo uriInfo) {
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        try {
            userRepository.addUser(renter);
        } catch (IllegalArgumentException e) {
            return Response.status(Status.CONFLICT.getStatusCode(), e.getMessage()).build();
        } catch (NullPointerException e) {
            return Response.status(422, e.getMessage()).build();
        }
        return Response.created(uriBuilder.build())
                .entity(renter)
                .build();
    }

    @POST
    @Path("/resourceAdmins")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createResourceAdmin(ResourceAdmin resourceAdmin, @Context UriInfo uriInfo) {
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        try {
            userRepository.addUser(resourceAdmin);
        } catch (IllegalArgumentException e) {
            return Response.status(Status.CONFLICT.getStatusCode(), e.getMessage()).build();
        } catch (NullPointerException e) {
            return Response.status(422, e.getMessage()).build();
        }
        return Response.created(uriBuilder.build())
                .entity(resourceAdmin)
                .build();
    }

    @POST
    @Path("/admins")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createAdmin(Admin admin, @Context UriInfo uriInfo) {
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        try {
            userRepository.addUser(admin);
        } catch (IllegalArgumentException e) {
            return Response.status(Status.CONFLICT.getStatusCode(), e.getMessage()).build();
        } catch (NullPointerException e) {
            return Response.status(422, e.getMessage()).build();
        }
        return Response.created(uriBuilder.build())
                .entity(admin)
                .build();
    }

    //READ
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUsers() {
        List<User> users = userRepository.getUsers();
        if (users == null) {
            return Response.status(Status.NOT_FOUND.getStatusCode(), "User repository does not exist").build();
        }
        return Response.ok()
                .entity(users)
                .build();
    }

    @GET
    @Path("{login}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findUserByLogin(@PathParam("login") String login) {
        User user = userRepository.getUserWithLogin(login);
        if (user == null) {
            return Response.status(Status.NOT_FOUND.getStatusCode(), "User does not exist").build();
        }
        return Response.ok()
                .entity(user)
                .tag(EntityIdentitySignerVerifier.calculateEntitySignature(user))
                .build();
    }
    
    @GET
    @Path("self")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findSelf(@Context SecurityContext securityContext){
        User user = userRepository.getUserWithLogin(securityContext.getUserPrincipal().getName());
        return Response.ok()
                .entity(user)
                .build();
    }

    //UPDATE
    @PUT
    @Path("{login}")
    @Consumes({MediaType.APPLICATION_JSON})
    @EntitySignatureValidatorFilterBinding
    public Response modifyUser(@PathParam("login") String login, @HeaderParam("If-Match") @NotNull @NotEmpty String header, Renter user) {

        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(header, user)) {
            return Response.status(Status.NOT_ACCEPTABLE.getStatusCode(), "").build();
        }

        if (userRepository.getUserWithLogin(login) == null) {
            return Response.status(Status.NOT_FOUND.getStatusCode(), "User does not exist").build();
        }

        userRepository.modifyUser(login, user);

        return Response.ok()
                .entity(user)
                .build();
    }

    //UPDATE
    @PUT
    @Path("/resourceAdmins/{login}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response modifyUser(@PathParam("login") String login, @HeaderParam("If-Match") @NotNull @NotEmpty String header, ResourceAdmin user) {

        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(header, user)) {
            return Response.status(Status.NOT_ACCEPTABLE.getStatusCode(), "").build();
        }

        if (userRepository.getUserWithLogin(login) == null) {
            return Response.status(Status.NOT_FOUND.getStatusCode(), "User does not exist").build();
        }

        userRepository.modifyUser(login, user);

        return Response.ok()
                .entity(user)
                .build();
    }

    //UPDATE
    @PUT
    @Path("/admins/{login}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response modifyUser(@PathParam("login") String login, @HeaderParam("If-Match") @NotNull @NotEmpty String header, Admin user) {

        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(header, user)) {
            return Response.status(Status.NOT_ACCEPTABLE.getStatusCode(), "").build();
        }

        if (userRepository.getUserWithLogin(login) == null) {
            return Response.status(Status.NOT_FOUND.getStatusCode(), "User does not exist").build();
        }

        userRepository.modifyUser(login, user);

        return Response.ok()
                .entity(user)
                .build();
    }
}
