package com.example.library.services;

import com.example.library.entities.Book;

import java.util.List;

public interface IBookService {
    public Book addBook(Book book);
    public Book updateBook(Long id, Book book);
    public Book getBookById(Long id);
    public void deleteBook(Long id);
    public List<Book> getAllBooks();


}
