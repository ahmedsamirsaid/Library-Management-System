package com.example.Galaxy;


import com.example.library.controller.PatronController;
import com.example.library.entities.Patron;
import com.example.library.services.IPatronService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PatronTest {
    @Mock
    private IPatronService patronService;

    @InjectMocks
    private PatronController patronController;

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @BeforeEach
    void setUp() {
                MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPatrons() {
                List<Patron> patrons = new ArrayList<>();
        patrons.add(new Patron(1L, "Patron 1", "1234567890","patron1@gmail.com","125 Main St"));
        patrons.add(new Patron(2L, "Patron 2", "1234567891","patron2@gmail.com","125 Main St"));               when(patronService.getAllPatrons()).thenReturn(patrons);

                ResponseEntity<List<Patron>> response = patronController.getAllPatrons();

                assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetPatronById() {
                Patron patron = new Patron(1L, "Patron 1", "1234567890","patron@gmail.com","125 Main St");

                when(patronService.getPatronById(1L)).thenReturn(patron);

                ResponseEntity<Patron> response = patronController.getPatronById(1L);

                assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Patron 1", response.getBody().getName());
    }

    @Test
    void testAddPatron() {
                Patron patronToAdd = new Patron(null, "New Patron", "1234567890","patron@gmail.com","125 Main St");
        Patron savedPatron = new Patron(1L, "New Patron", "1234567890","patron@gmail.com","125 Main St");

                when(patronService.addPatron(patronToAdd)).thenReturn(savedPatron);

                ResponseEntity<Patron> response = patronController.addPatron(patronToAdd);

                assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        assertEquals("New Patron", response.getBody().getName());
    }

    @Test
    void testUpdatePatron() {
                Patron updatedPatron = new Patron(1L, "Updated Patron", "1234567890","patron@gmail.com","125 Main St");

                when(patronService.updatePatron(1L, updatedPatron)).thenReturn(updatedPatron);

                ResponseEntity<Patron> response = patronController.updatePatron(1L, updatedPatron);

                assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Updated Patron", response.getBody().getName());
    }

    @Test
    void testDeletePatron() {
                ResponseEntity<Void> response = patronController.deletePatron(1L);

                assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(patronService, times(1)).deletePatron(1L);
    }

    @Test
    void testNameNotBlank() {
                Patron patron = new Patron(null, "", "1234567890", "email@example.com", "123 Street");

                Set<ConstraintViolation<Patron>> violations = validator.validate(patron);

                assertEquals(2, violations.size());
        assertEquals("size must be between 2 and 100", violations.iterator().next().getMessage());
    }

        @Test
    void testPhoneSizeMin() {
                Patron patron = new Patron(null, "John Doe", "12345", "email@example.com", "123 Street");

                Set<ConstraintViolation<Patron>> violations = validator.validate(patron);

                assertEquals(1, violations.size());
        assertEquals("size must be between 10 and 15", violations.iterator().next().getMessage());
    }


    @Test
    void testAddressNotBlank() {
                Patron patron = new Patron(null, "John Doe", "1234567890", "email@example.com", "");

                Set<ConstraintViolation<Patron>> violations = validator.validate(patron);

                assertEquals(2, violations.size());
    }


}
