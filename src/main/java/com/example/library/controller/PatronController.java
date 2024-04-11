package com.example.library.controller;

import com.example.library.entities.Patron;
import com.example.library.services.IPatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api")
public class PatronController {


    @Autowired
    private IPatronService patronService;
    @GetMapping("/patrons")

    @PreAuthorize("hasRole('ROLE_ADMIN' )")
    public ResponseEntity<List<Patron>> getAllPatrons() {
        List<Patron> patrons = patronService.getAllPatrons();
        return new ResponseEntity<>(patrons, HttpStatus.OK);
    }

    @GetMapping("/patrons/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN' )")
    public ResponseEntity<Patron> getPatronById(@PathVariable Long id) {
        Patron patron = patronService.getPatronById(id);
        return new ResponseEntity<>(patron, HttpStatus.OK);
    }
    @PostMapping("/patrons")

    @PreAuthorize("hasRole('ROLE_ADMIN' ) or hasRole('ROLE_USER')")
    public ResponseEntity<Patron> addPatron(@Valid @RequestBody Patron patron) {
        Patron savedPatron = patronService.addPatron(patron);
        return new ResponseEntity<>(savedPatron, HttpStatus.CREATED);
    }
    @PutMapping("/patrons/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN' ) or hasRole('ROLE_USER')")
    public ResponseEntity<Patron> updatePatron(@PathVariable Long id, @Valid@RequestBody Patron patron) {
        Patron updatedPatron = patronService.updatePatron(id, patron);
        return new ResponseEntity<>(updatedPatron, HttpStatus.OK);
    }
    @DeleteMapping("/patrons/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN' )")
    public ResponseEntity<Void> deletePatron(@PathVariable Long id) {
        patronService.deletePatron(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
