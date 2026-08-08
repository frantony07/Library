package com.library.Entity;

import com.library.Entity.Enum.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String name ;
    @Enumerated(EnumType.STRING)
    @Column (name = "Category")
    private Category category;
    private String author;
    private LocalDate fabricationYear ;
    private Float price ;
    private Integer numPage ;
    private boolean active ;
    private  boolean disp_to_rent;
}
