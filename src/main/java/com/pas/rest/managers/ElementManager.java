/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pas.rest.managers;

import com.pas.rest.model.*;
import com.pas.rest.repositories.ElementRepository;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author student
 */
public class ElementManager {

    @Inject
    private ElementRepository elemRepository;

    // CREATE
    public void createBook(Book book) {
        elemRepository.addElement(book);
    }

    public void createNewspaper(Newspaper newspaper) {
        elemRepository.addElement(newspaper);
    }

    // READ
    public List<Elem> getElements() {
        return elemRepository.getElements();
    }

    public Elem getElementWithID(String id) {
        return elemRepository.getElement(id);
    }

    // UPDATE
    public void modifyElement(String id, Elem elem) {
        elemRepository.modifyElement(id, elem);
    }

    // DELETE
    public void removeElement(String id) {
        elemRepository.removeElement(id);

    }
}
