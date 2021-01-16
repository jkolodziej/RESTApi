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
            addElement(dataFiller.fillElementsWithData().get(i));
        }
    }

    //CREATE
    public void addElement(Elem element) {
        synchronized (elements) {
            if (element == null) {
                throw new IllegalArgumentException("Element with no data");
            }
            element.setId(ID.generateID());
            elements.add(element);
        }
    }

    //READ
    public List<Elem> getElements() {
        synchronized (elements) {
            return new ArrayList<>(elements);
        }
    }

    public Elem getElementWithID(String id) {
        synchronized (elements) {
            for (Elem element : elements) {
                if (element.getId().equals(id)) {
                    return element;
                }
            }
        }
        return null;
    }

    // UPDATE
    public void modifyElement(String id, Elem elem) {
        synchronized (elements) {
            for (int i = 0; i < elements.size(); i++) {
                if (elements.get(i).getId().equals(id)) {
                    elements.set(i, elem);
                    elements.get(i).setId(id);
                }
            }
        }
    }

    //DELETE
    public void removeElement(String id) {
        synchronized (elements) {
            elements.removeIf(elem -> id.equals(elem.getId()) && !elem.isRented());
        }
    }
}
