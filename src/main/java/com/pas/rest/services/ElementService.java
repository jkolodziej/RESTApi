package com.pas.rest.services;

import com.pas.rest.managers.ElementManager;
import com.pas.rest.model.*;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
@Path("elements")
public class ElementService {

    @Inject
    private ElementManager elemManager;

    //CREATE BOOK
    @POST
    @Path("/books")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createElement(@Valid Book book, @Context UriInfo uriInfo) {
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        try {
            elemManager.createBook(book);
        } catch (IllegalArgumentException e) {
            return Response.status(Status.CONFLICT.getStatusCode(), e.getMessage()).build();
        }
        return Response.created(uriBuilder.build())
                .entity(book)
                .build();

    }

    //CREATE NEWSPAPER
    @POST
    @Path("/newspapers")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createElement(@Valid Newspaper newspaper, @Context UriInfo uriInfo) {
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        try {
            elemManager.createNewspaper(newspaper);
        } catch (IllegalArgumentException e) {
            return Response.status(Status.CONFLICT.getStatusCode(), e.getMessage()).build();
        }
        return Response.created(uriBuilder.build())
                .entity(newspaper)
                .build();
    }

    //READ
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getElements() {
        List<Elem> elements = elemManager.getElements();
        if (elements == null) {
            return Response.status(Status.NOT_FOUND.getStatusCode(), "Element repository does not exist").build();
        }
        return Response.ok()
                .entity(elements)
                .build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findElement(@PathParam("id") String id) {
        Elem element = elemManager.getElementWithID(id);
        if (element == null) {
            return Response.status(Status.NOT_FOUND.getStatusCode(), "Element does not exist").build();
        }
        return Response.ok()
                .entity(element)
                .build();
    }

    //UPDATE BOOK
    @PUT
    @Path("/books/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response modifyElement(@PathParam("id") String id, @Valid Book elem) {
        if (elemManager.getElementWithID(id) == null) {
            return Response.status(Status.NOT_FOUND.getStatusCode(), "Element does not exist").build();
        } else {
            elemManager.modifyElement(id, elem);
            return Response.ok()
                    .entity(elem)
                    .build();
        }
    }

    //UPDATE NEWSPAPER
    @PUT
    @Path("/newspapers/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response modifyElement(@PathParam("id") String id, @Valid Newspaper elem) {
        if (elemManager.getElementWithID(id) == null) {
            return Response.status(Status.NOT_FOUND.getStatusCode(), "Element does not exist").build();
        } else {
            elemManager.modifyElement(id, elem);
            return Response.ok()
                    .entity(elem)
                    .build();
        }
    }

    //DELETE
    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") String id) {
        if (elemManager.getElementWithID(id) == null) {
            return Response.status(Status.NOT_FOUND.getStatusCode(), "Element does not exist").build();
        } else {
            elemManager.removeElement(id);
            return Response.ok()
                    .build();
        }
    }
}
