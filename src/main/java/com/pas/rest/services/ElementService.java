package com.pas.rest.services;

import com.pas.rest.model.*;
import com.pas.rest.repositories.ElementRepository;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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
    private ElementRepository elemRepository;

    //CREATE BOOK
    @POST
    @Path("/books")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createElement(Book book, @Context UriInfo uriInfo) {
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        try {
            elemRepository.addElement(book);
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
    public Response createElement(Newspaper newspaper, @Context UriInfo uriInfo) {
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        try {
            elemRepository.addElement(newspaper);
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
        List<Elem> elements = elemRepository.getElements();
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
        Elem element = elemRepository.getElementWithID(id);
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
    public Response modifyElement(@PathParam("id") String id, Book elem) {
        if (elemRepository.getElementWithID(id) == null) {
            return Response.status(Status.NOT_FOUND.getStatusCode(), "Element does not exist").build();
        } else {
            elemRepository.modifyElement(id, elem);
            return Response.ok()
                    .entity(elem)
                    .build();
        }
    }

    //UPDATE NEWSPAPER
    @PUT
    @Path("/newspapers/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response modifyElement(@PathParam("id") String id, Newspaper elem) {
        if (elemRepository.getElementWithID(id) == null) {
            return Response.status(Status.NOT_FOUND.getStatusCode(), "Element does not exist").build();
        } else {
            elemRepository.modifyElement(id, elem);
            return Response.ok()
                    .entity(elem)
                    .build();
        }
    }

    //DELETE
    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") String id) {
        if (elemRepository.getElementWithID(id) == null) {
            return Response.status(Status.NOT_FOUND.getStatusCode(), "Element does not exist").build();
        } else {
            elemRepository.removeElement(id);
            return Response.ok()
                    .build();
        }
    }
}
