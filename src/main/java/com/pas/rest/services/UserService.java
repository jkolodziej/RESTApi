package com.pas.rest.services;

import com.pas.rest.filters.EntitySignatureValidatorFilterBinding;
import com.pas.rest.managers.UserManager;
import com.pas.rest.model.*;
import com.pas.rest.utils.EntityIdentitySignerVerifier;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
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
    private UserManager userManager;

    public UserService() {
    }

    //CREATE
    @POST
    @Path("/renters")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createRenter(@Valid Renter renter, @Context UriInfo uriInfo) {
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        try {
            userManager.creatUser(renter);
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
    public Response createResourceAdmin(@Valid ResourceAdmin resourceAdmin, @Context UriInfo uriInfo) {
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        try {
            userManager.creatUser(resourceAdmin);
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
    public Response createAdmin(@Valid Admin admin, @Context UriInfo uriInfo) {
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        try {
            userManager.creatUser(admin);
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
        List<User> users = userManager.getAllUsers();
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
        User user = userManager.getUserWithLogin(login);
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
    public Response findSelf(@Context SecurityContext securityContext) {
        User user = userManager.getUserWithLogin(securityContext.getUserPrincipal().getName());
        return Response.ok()
                .entity(user)
                .build();
    }

    //UPDATE
    @PUT
    @Path("/renters/{login}")
    @Consumes({MediaType.APPLICATION_JSON})
    @EntitySignatureValidatorFilterBinding
    public Response modifyUser(@PathParam("login") String login, @HeaderParam("If-Match") @NotNull @NotEmpty String header, @Valid Renter user) {

        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(header, user)) {
            return Response.status(Status.NOT_ACCEPTABLE.getStatusCode(), "").build();
        }

        if (userManager.getUserWithLogin(login) == null) {
            return Response.status(Status.NOT_FOUND.getStatusCode(), "User does not exist").build();
        }

        userManager.modifyUser(login, user);

        return Response.ok()
                .entity(user)
                .build();
    }

    //UPDATE
    @PUT
    @Path("/resourceAdmins/{login}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response modifyUser(@PathParam("login") String login, @HeaderParam("If-Match") @NotNull @NotEmpty String header, @Valid ResourceAdmin user) {

        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(header, user)) {
            return Response.status(Status.NOT_ACCEPTABLE.getStatusCode(), "").build();
        }

        if (userManager.getUserWithLogin(login) == null) {
            return Response.status(Status.NOT_FOUND.getStatusCode(), "User does not exist").build();
        }

        userManager.modifyUser(login, user);

        return Response.ok()
                .entity(user)
                .build();
    }

    //UPDATE
    @PUT
    @Path("/admins/{login}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response modifyUser(@PathParam("login") String login, @HeaderParam("If-Match") @NotNull @NotEmpty String header, @Valid Admin user) {

        if (!EntityIdentitySignerVerifier.verifyEntityIntegrity(header, user)) {
            return Response.status(Status.NOT_ACCEPTABLE.getStatusCode(), "").build();
        }

        if (userManager.getUserWithLogin(login) == null) {
            return Response.status(Status.NOT_FOUND.getStatusCode(), "User does not exist").build();
        }

        userManager.modifyUser(login, user);

        return Response.ok()
                .entity(user)
                .build();
    }

    // CHANGE ACTIVITY
    @PUT
    @Path("/activity/{login}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response changeActivity(@PathParam("login") String login) {

        User user = userManager.getUserWithLogin(login);
        if (user == null) {
            return Response.status(Status.NOT_FOUND.getStatusCode(), "User does not exist").build();
        }

        userManager.changeUserActivity(login);

        return Response.ok()
                .entity(user)
                .build();
    }
}
