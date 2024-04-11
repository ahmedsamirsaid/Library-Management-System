package com.example.Galaxy;

import com.example.library.controller.BookController;
import com.example.library.entities.Book;
import com.example.library.services.IBookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookTest {

    @Mock
    private IBookService bookService;

    @InjectMocks
    private BookController bookController;


    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooks() {
                List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Book 1", "Author 1",2022,"110111"));
        books.add(new Book(2L, "Book 2", "Author 2",2022,"110112"));

                when(bookService.getAllBooks()).thenReturn(books);

                ResponseEntity<List<Book>> response = bookController.getAllBooks();

                assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetBookById() {
                Book book = new Book(1L, "Book 1", "Author 1",2022,"110111");

                when(bookService.getBookById(1L)).thenReturn(book);

                ResponseEntity<Book> response = bookController.getBookById(1L);

                assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book 1", Objects.requireNonNull(response.getBody()).getTitle());
        assertEquals("Author 1", response.getBody().getAuthor());
    }

    @Test
    void testAddBook() {
                Book bookToAdd = new Book(null, "New Book", "New Author",2022,"110111");
        Book savedBook = new Book(1L, "New Book", "New Author",2022,"110111");

                when(bookService.addBook(bookToAdd)).thenReturn(savedBook);

                ResponseEntity<Book> response = bookController.addBook(bookToAdd);

                assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        assertEquals("New Book", response.getBody().getTitle());
        assertEquals("New Author", response.getBody().getAuthor());
    }

    @Test
    void testUpdateBook() {
                Book updatedBook = new Book(1L, "Updated Book", "Updated Author",2022,"110111");

                when(bookService.updateBook(1L, updatedBook)).thenReturn(updatedBook);

                ResponseEntity<Book> response = bookController.updateBook(1L, updatedBook);

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Updated Book", response.getBody().getTitle());
        assertEquals("Updated Author", response.getBody().getAuthor());
    }

    @Test
    void testDeleteBook() {
        ResponseEntity<Void> response = bookController.deleteBook(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(bookService, times(1)).deleteBook(1L);
    }



    @Test
    void testTitleNotBlank() {
        Book book = new Book(null, "", "Author", 2000, "ISBN");

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        assertEquals(2, violations.size());
    }

    @Test
    void testAuthorNotBlank() {
        Book book = new Book(null, "Title", "", 2000, "ISBN");

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        assertEquals(2, violations.size());
    }

    @Test
    void testPublicationYearMinValue() {
        Book book = new Book(null, "Title", "Author", 1799, "ISBN");

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        assertEquals(1, violations.size());
        assertEquals("Publication year must be at least 1800", violations.iterator().next().getMessage());
    }

    @Test
    void testPublicationYearMaxValue() {
        Book book = new Book(null, "Title", "Author", 2025, "ISBN");

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        assertEquals(1, violations.size());
        assertEquals("Publication year must be at most 2024", violations.iterator().next().getMessage());
    }

    @Test
    void testIsbnNotBlank() {
        Book book = new Book(null, "Title", "Author", 2000, "");

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertEquals(2, violations.size());
    }
}
