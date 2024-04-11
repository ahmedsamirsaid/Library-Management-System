package com.example.library.repository;

import com.example.library.entities.Book;
import com.example.library.entities.BorrowingRecord;
import com.example.library.entities.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface BorrowingRecordRepository  extends JpaRepository<BorrowingRecord, Long> {
    BorrowingRecord findByBookAndPatronAndReturnDateIsNull(Book book, Patron patron);
    BorrowingRecord findByBookAndReturnDateIsNull(Book book);
}
