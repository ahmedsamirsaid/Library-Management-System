package com.example.library.errorHandeling;

public class BookAlreadyBorrowedException extends RuntimeException {
    public BookAlreadyBorrowedException(String s) {
        super(s);
    }
}
