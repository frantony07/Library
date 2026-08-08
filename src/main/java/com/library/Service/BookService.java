package com.library.Service;

import com.library.Controller.Record.BookDto;
import com.library.Entity.Book;
import com.library.Entity.Enum.Category;
import com.library.Repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository ;

    public String salve(Book book){
        try {
            this.bookRepository.save(book);
            return "book successfully salve ";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Book findById(long id) {
        Optional<Book> book = this.bookRepository.findById(id);
        return book.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"book no find"));

    }

    public String update(Long id, Book bookNew) {
        try {
            Book bookOld = findById(id);
            exchangeDataInBook(bookOld, bookNew);
            return salve(bookOld);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void exchangeDataInBook(Book bookOLD , Book bookNew){
        bookOLD.setAuthor(bookNew.getAuthor());
        bookOLD.setCategory(bookNew.getCategory());
        bookOLD.setName(bookNew.getName());
        bookOLD.setFabricationYear(bookNew.getFabricationYear());
        bookOLD.setNumPage(bookNew.getNumPage());
        bookOLD.setActive(bookNew.isActive());
        bookOLD.setPrice(bookNew.getPrice());

    }
    public List<BookDto> toBookDto(List<Book> bookList){
        List<BookDto> bookDtoList = new ArrayList<>();
        bookList.forEach(book -> {
            BookDto bookDto = new BookDto(book.getName(),book.getCategory(),book.getAuthor(),book.getFabricationYear(),book.getPrice(),book.getNumPage(), book.isActive());
            bookDtoList.add(bookDto);
        });
        return bookDtoList;

    }

    public List<Book> findAll(){
        try {
            return this.bookRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public String delete(long id){
        try {
            Book book = findById(id);
            book.setActive(false);
            return "book " + book.getName() +  " was delete ";

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String applyPartialUpdate(long id, Map<String, Object> book) {
        Book newBook = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("book not found"));
        book.forEach((key, value) -> {
            switch (key) {
                case "name" -> newBook.setName((String) value);
                case "author" -> newBook.setAuthor((String) value);
                case "category" -> newBook.setCategory(Category.valueOf((String) value));
                case "fabricationYear" -> newBook.setFabricationYear(LocalDate.parse((String) value));
                case "price" -> newBook.setPrice((float) value);
                case "active" -> newBook.setActive((boolean) value);
                case "numPage" -> newBook.setNumPage((Integer) value);
            }
        });
        return salve(newBook);
    }

    public List<BookDto> findByFabricationYearGreaterThanEqualAndAuthor(LocalDate minDate, String author) {
        try {
            List<Book> bookList = bookRepository.findByFabricationYearGreaterThanEqualAndAuthor(minDate,author);
            return toBookDto(bookList);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Book> findAllActive() {
        try {
            return this.bookRepository.findByActiveTrue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<BookDto> findByCategory(Category category) {
        try {
            List<Book> bookList = bookRepository.findByCategory(category);
            return toBookDto(bookList) ;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public String rentBook(long id) {
        try {
            Book book = bookRepository.findById(id).orElseThrow(()-> new RuntimeException("The book already rented"));
            if (book.isDisp_to_rent()){
                book.setDisp_to_rent(false);
                salve(book);
                return "book rent successful ";
            }
            return String.valueOf(new RuntimeException("The book already rented"));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


}
