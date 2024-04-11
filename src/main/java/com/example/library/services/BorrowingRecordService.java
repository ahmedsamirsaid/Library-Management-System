package com.example.library.services;

import com.example.library.entities.Book;
import com.example.library.entities.BorrowingRecord;
import com.example.library.entities.Patron;
import com.example.library.errorHandeling.BookAlreadyBorrowedException;
import com.example.library.errorHandeling.ResourceNotFoundException;
import com.example.library.repository.BorrowingRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class BorrowingRecordService implements IBorrowingRecordService{

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Autowired
    private BookService bookService;

    @Autowired

    private PatronService patronService;

    @Override
    @Transactional
    public BorrowingRecord borrowBook(Long bookId, Long patronId) {
        Book book = bookService.getBookById(bookId);
        if (isBookBorrowed(book)) {
            throw new BookAlreadyBorrowedException("The book with ID " + bookId + " is already borrowed.");
        }
        Patron patron = patronService.getPatronById(patronId);
        BorrowingRecord borrowingRecord = new BorrowingRecord(book, patron, new Date());
        return borrowingRecordRepository.save(borrowingRecord);
    }

    private boolean isBookBorrowed(Book book) {
        BorrowingRecord borrowingRecord = borrowingRecordRepository.findByBookAndReturnDateIsNull(book);
        return borrowingRecord != null;
    }

    @Override
    @Transactional
    public BorrowingRecord returnBook(Long bookId, Long patronId) {
        Book book = bookService.getBookById(bookId);
        Patron patron = patronService.getPatronById(patronId);
        BorrowingRecord borrowingRecord = borrowingRecordRepository.findByBookAndPatronAndReturnDateIsNull(book, patron);
        if (borrowingRecord == null) {
            throw new ResourceNotFoundException("No borrowing record found for book id " + bookId + " and patron id " + patronId);
        }
        borrowingRecord.setReturnDate(new Date());
        return borrowingRecordRepository.save(borrowingRecord);
    }
}
