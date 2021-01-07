package com.pas.rest.services;

import com.pas.rest.model.*;
import com.pas.rest.repositories.ElementRepository;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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
    private List<Elem> currentElements;
    private List<Book> currentBooks;
    private List<Newspaper> currentNewspapers;

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Elem findElement(@PathParam("id") String id) {
        return elemRepository.findElement(id);
    }

    public void createBook(String name, String genre, String author, int pages) {
        Book b = new Book(name, genre, author, pages);
        elemRepository.add(b);
    }

    public void createNewspaper(String name, String genre) {
        Newspaper n = new Newspaper(name, genre);
        elemRepository.add(n);
    }

    public List<Book> getAllBooks() {
        return currentBooks;
    }

    public List<Newspaper> getAllNewspapers() {
        return currentNewspapers;
    }

    public List<Book> getFilteredBook(String id) {
        if ("".equals(id)) {
            return currentBooks;
        } else {
            for (Book currentBook : currentBooks) {
                if (id.equals(currentBook.getId())) {
                    return List.of(currentBook);
                }
            }
        }
        return null;
    }

    public List<Newspaper> getFilteredNewspaper(String id) {
        if ("".equals(id)) {
            return currentNewspapers;
        } else {
            for (Newspaper currentNewspaper : currentNewspapers) {
                if (id.equals(currentNewspaper.getId())) {
                    return List.of(currentNewspaper);
                }
            }
        }
        return null;
    }

    public List<Elem> getCurrentElements() {
        return currentElements;
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

    public void remove(Elem element) {
        if (!element.isRented()) {
            elemRepository.remove(element);
        }
    }

}
