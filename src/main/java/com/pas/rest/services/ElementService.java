package com.pas.rest.services;

import com.pas.rest.model.*;
import com.pas.rest.repositories.ElementRepository;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@RequestScoped
@Path("elements")
public class ElementService {

    @Inject
    private ElementRepository elemRepository;

    // CREATE
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public void createElement(Book book) {
        elemRepository.addElement(book);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public void createElement(Newspaper newspaper) {
        elemRepository.addElement(newspaper);
    }

    // READ
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Elem> getElements() {
        return elemRepository.getElements();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Elem findElement(@PathParam("id") String id) {
        return elemRepository.getElementWithID(id);
    }

    // UPDATE
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void modifyElement(Elem oldElement, Elem newElement) {
        elemRepository.modifyElement(oldElement, newElement);
    }

    // DELETE
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        elemRepository.removeElement(id);
    }

//    @GET
//    @Path("books")
//    @Produces({MediaType.APPLICATION_JSON})
//    public List<Book> getAllBooks() {
//        return elemRepository.getAllBooks();
//    }
//
//    @GET
//    @Path("newspapers")
//    @Produces({MediaType.APPLICATION_JSON})
//    public List<Newspaper> getAllNewspapers() {
//        return elemRepository.getAllNewspapers();
//    }
}
