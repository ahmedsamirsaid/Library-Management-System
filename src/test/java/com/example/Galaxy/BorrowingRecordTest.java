package com.example.Galaxy;

import com.example.library.controller.BorrowingRecordController;
import com.example.library.entities.BorrowingRecord;
import com.example.library.services.IBorrowingRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class BorrowingRecordTest {

    @Mock
    private IBorrowingRecordService borrowingRecordService;

    @InjectMocks
    private BorrowingRecordController borrowingRecordController;

    @BeforeEach
    void setUp() {
        
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBorrowBook() {
        
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setId(1L);

        
        when(borrowingRecordService.borrowBook(1L, 1L)).thenReturn(borrowingRecord);

        
        ResponseEntity<BorrowingRecord> response = borrowingRecordController.borrowBook(1L, 1L);

        
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void testReturnBook() {
        
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setId(1L);

        
        when(borrowingRecordService.returnBook(1L, 1L)).thenReturn(borrowingRecord);

        
        ResponseEntity<BorrowingRecord> response = borrowingRecordController.returnBook(1L, 1L);

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
    }


}
