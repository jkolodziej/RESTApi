package com.pas.rest.repositories;

import com.pas.rest.model.*;
import java.util.ArrayList;
import java.util.List;
import com.pas.rest.DataFiller;
import com.pas.rest.ID;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ElementRepository {

    private final ArrayList<Elem> elements = new ArrayList<>();

    public ElementRepository() {
        DataFiller dataFiller = new DataFiller();
        for (int i = 0; i < dataFiller.fillElementsWithData().size(); i++) {
            add(dataFiller.fillElementsWithData().get(i));
        }
    }

    public void add(Elem element) {
        synchronized (elements) {
            if (element == null) {
                throw new IllegalArgumentException("Element with no data");
            }
            element.setId(ID.generateID());
            elements.add(element);
        }
    }

    public List<Book> getAllBooks() {
        synchronized (elements) {
            ArrayList<Book> books = new ArrayList<>();
            for (Elem elem : elements) {
                if (elem instanceof Book) {
                    books.add((Book) elem);
                }
            }
            return books;
        }
    }

    public List<Newspaper> getAllNewspapers() {
        synchronized (elements) {
            ArrayList<Newspaper> newspapers = new ArrayList<>();
            for (Elem elem : elements) {
                if (elem instanceof Newspaper) {
                    newspapers.add((Newspaper) elem);
                }
            }
            return newspapers;
        }
    }

    public Elem findElement(String id) {
        synchronized (elements) {
            for (Elem elem : elements) {
                if (elem.getId().equals(id)) {
                    return elem;
                }
            }
            return null;
        }
    }

    public void remove(Elem elem) {
        synchronized (elements) {
            for (int i = 0; i < elements.size(); i++) {
                Elem e = elements.get(i);
                if (e.getId().equals(elem.getId())) {
                    elements.remove(i);
                }
            }
        }
    }

    public void modifyBook(Book book) {
        synchronized (elements) {
            for (int i = 0; i < elements.size(); i++) {
                if (elements.get(i).equals(book)) {
                    elements.set(i, book);
                }
            }
        }
    }

    public void modifyNewspaper(Newspaper newspaper) {
        synchronized (elements) {
            for (int i = 0; i < elements.size(); i++) {
                if (elements.get(i).equals(newspaper)) {
                    elements.set(i, newspaper);
                }
            }

        }
    }

    public List<Elem> getElements() {
        synchronized (elements) {
            return new ArrayList<>(elements);
        }
    }

}
