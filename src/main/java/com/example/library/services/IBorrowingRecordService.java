package com.example.library.services;

import com.example.library.entities.BorrowingRecord;

public interface IBorrowingRecordService {
     BorrowingRecord borrowBook(Long bookId, Long patronId);
     BorrowingRecord returnBook(Long bookId, Long patronId);
}
