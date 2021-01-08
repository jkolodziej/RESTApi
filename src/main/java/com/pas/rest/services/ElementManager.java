package com.pas.rest.services;

import com.pas.rest.model.*;
import com.pas.rest.repositories.ElementRepository;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RequestScoped
@Path("elements")
public class ElementManager{

    @Inject
    private ElementRepository elemRepository;

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Elem findElement(@PathParam("id") String id) {
        return elemRepository.findElement(id);
    }

//    @POST
//    @Path("books")
//    @Consumes({MediaType.APPLICATION_JSON})
    // type of param?
    public void createBook(String name, String genre, String author, int pages) {
        Book b = new Book(name, genre, author, pages);
        elemRepository.add(b);
    }

//    @POST
//    @Path("newspapers")
//    @Consumes({MediaType.APPLICATION_JSON})
    public void createNewspaper(String name, String genre) {
        Newspaper n = new Newspaper(name, genre);
        elemRepository.add(n);
    }

    @GET
    @Path("books")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Book> getAllBooks() {
        return elemRepository.getAllBooks();
    }

    @GET
    @Path("newspapers")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Newspaper> getAllNewspapers() {
        return elemRepository.getAllNewspapers() ;
    }

    public List<Book> getFilteredBook(String id) {
        if ("".equals(id)) {
            return elemRepository.getAllBooks();
        } else {
            for (Book currentBook : elemRepository.getAllBooks()) {
                if (id.equals(currentBook.getId())) {
                    return List.of(currentBook);
                }
            }
        }
        return null;
    }

    public List<Newspaper> getFilteredNewspaper(String id) {
        if ("".equals(id)) {
            return elemRepository.getAllNewspapers();
        } else {
            for (Newspaper currentNewspaper : elemRepository.getAllNewspapers()) {
                if (id.equals(currentNewspaper.getId())) {
                    return List.of(currentNewspaper);
                }
            }
        }
        return null;
    }

    public void modifyBook(Book book) {
        elemRepository.modifyBook(book);
    }

    public void modifyNewspaper(Newspaper newspaper) {
        elemRepository.modifyNewspaper(newspaper);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Elem> getElements() {
        return elemRepository.getElements();
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        elemRepository.remove(id);
    }
}
