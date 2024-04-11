package com.example.library.services;

import com.example.library.entities.Patron;

import java.util.List;

public interface IPatronService{
    public Patron addPatron(Patron patron);
    public Patron updatePatron(Long id, Patron patron);
    public Patron getPatronById(Long id);
    public void deletePatron(Long id);
    public List<Patron> getAllPatrons();
}
