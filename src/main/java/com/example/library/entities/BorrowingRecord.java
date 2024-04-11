package com.example.library.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BorrowingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "patron_id")
    private Patron patron;

    @NotNull
    @PastOrPresent
    @Temporal(TemporalType.DATE)
    private Date borrowDate;

    @PastOrPresent
    @Temporal(TemporalType.DATE)
    private Date returnDate;

    public BorrowingRecord(Book book, Patron patron, Date date) {
        this.book = book;
        this.patron = patron;
        this.borrowDate = date;
    }
}