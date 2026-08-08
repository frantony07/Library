package com.library.Controller.Record;

import com.library.Entity.Enum.Category;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;

public record BookDto(String name, Category category , String author, LocalDate fabricationYear, Float price, Integer numPage,boolean rent) {
}
