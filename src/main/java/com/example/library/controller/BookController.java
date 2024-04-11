package com.example.library.controller;


import com.example.library.entities.Book;
import com.example.library.services.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {
    @Autowired
    private IBookService bookService;


    @GetMapping("/books")
    @PreAuthorize("hasRole('ROLE_ADMIN' ) or hasRole('ROLE_USER')")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN' ) or hasRole('ROLE_USER')")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping("/books")
    @PreAuthorize("hasRole('ROLE_ADMIN' )")
    public ResponseEntity<Book> addBook(@Valid @RequestBody Book book) {
        Book savedBook = bookService.addBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @PutMapping("/books/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN' )")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid@RequestBody Book book) {
        Book updatedBook = bookService.updateBook(id, book);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @DeleteMapping("/books/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN' )")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
