package com.library.Repository;

import com.library.Entity.Book;
import com.library.Entity.Enum.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
     List<Book> findByCategory(Category category);
     List<Book> findByActiveTrue();
     List<Book> findByFabricationYearAndAuthor(LocalDate minDate ,String author);
     List<Book> findByFabricationYearGreaterThanEqualAndAuthor(LocalDate minDate, String author);
}
