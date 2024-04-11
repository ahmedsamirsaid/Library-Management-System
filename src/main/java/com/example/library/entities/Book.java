package com.example.library.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 200)
    private String title;
    @NotBlank
    @Size(min = 2, max = 200)
    private String author;
    @NotNull
    @Min(value = 1800, message = "Publication year must be at least 1800")
    @Max(value = 2024, message = "Publication year must be at most 2024")
    private int publicationYear;
    @NotBlank
    @Size(min = 2, max = 200)
    private String isbn;


}
