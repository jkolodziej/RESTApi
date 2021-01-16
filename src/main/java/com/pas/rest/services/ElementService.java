package com.pas.rest.services;

import com.pas.rest.model.*;
import com.pas.rest.repositories.ElementRepository;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RequestScoped
@Path("elements")
public class ElementService {

    @Inject
    private ElementRepository elemRepository;
    
    //CREATE BOOK
    @POST
    @Path("/books")
    @Consumes({MediaType.APPLICATION_JSON})
    public void createElement(Book book) {
        elemRepository.addElement(book);
    }
    //CREATE NEWSPAPER
    @POST
    @Path("/newspapers")
    @Consumes({MediaType.APPLICATION_JSON})
    public void createElement(Newspaper newspaper) {
        elemRepository.addElement(newspaper);
    }
    
    //READ
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
    
    //UPDATE BOOK
    @PUT
    @Path("/books/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void modifyElement(@PathParam("id") String id, Book elem) {
        elemRepository.modifyElement(id, elem);
    }
    
    //UPDATE NEWSPAPER
    @PUT
    @Path("/newspapers/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void modifyElement(@PathParam("id") String id, Newspaper elem) {
        elemRepository.modifyElement(id, elem);
    }

    //DELETE
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        elemRepository.removeElement(id);
    }
}
