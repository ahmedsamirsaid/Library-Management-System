package com.example.library.services;

import com.example.library.entities.Book;
import com.example.library.errorHandeling.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.library.repository.BookRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "books")
public class BookService implements IBookService{

    @Autowired
    private BookRepository bookRepository;

    @Override
    @Cacheable
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    @Cacheable( key = "#id")
    public Book getBookById(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    @Override
    @CacheEvict(allEntries = true)
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    @CachePut(key = "#id")
    public Book updateBook(Long id, Book book) {
        Book existingBook = getBookById(id);
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setPublicationYear(book.getPublicationYear());
        existingBook.setIsbn(book.getIsbn());
        return bookRepository.save(existingBook);
    }

    @Override
    @CacheEvict
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
