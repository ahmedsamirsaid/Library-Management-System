package com.example.library.controller;

import com.example.library.entities.BorrowingRecord;
import com.example.library.services.IBorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class BorrowingRecordController {

    @Autowired
    private IBorrowingRecordService borrowingRecordService;
    @PostMapping("/borrow/{bookId}/patron/{patronId}")

    @PreAuthorize("hasRole('ROLE_ADMIN' ) or hasRole('ROLE_USER')")
    public ResponseEntity<BorrowingRecord> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        BorrowingRecord borrowingRecord = borrowingRecordService.borrowBook(bookId, patronId);
        return new ResponseEntity<>(borrowingRecord, HttpStatus.CREATED);
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    @PreAuthorize("hasRole('ROLE_ADMIN' ) or hasRole('ROLE_USER')")
    public ResponseEntity<BorrowingRecord> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        BorrowingRecord borrowingRecord = borrowingRecordService.returnBook(bookId, patronId);
        return new ResponseEntity<>(borrowingRecord, HttpStatus.OK);
    }

}
