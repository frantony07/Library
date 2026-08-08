package com.library.Controller;

import com.library.Controller.Record.BookDto;
import com.library.Entity.Book;
import com.library.Entity.Enum.Category;
import com.library.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/book")
public class BookController {

    // to do reverce of delete .
    @Autowired
    private BookService bookService;

    @PostMapping("/salve")
    public ResponseEntity<String> salve (@RequestBody Book book){
        try {
            String salveConfirmations = this.bookService.salve(book);
            return new ResponseEntity<String>(salveConfirmations, HttpStatus.OK);
        }catch (Exception e ) {
            return new  ResponseEntity<String>("error to database connections " , HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<BookDto> findById(@PathVariable long id){
        try {
            Book book = bookService.findById(id);
            BookDto bookDto = new BookDto(book.getName(), book.getCategory(),book.getAuthor(),book.getFabricationYear(),book.getPrice(),book.getNumPage(), book.isDisp_to_rent());
            return ResponseEntity.ok(bookDto);

        } catch (Exception e) {
            return (ResponseEntity<BookDto>) ResponseEntity.badRequest();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable long id, @RequestBody Book book){
        try {
            String putConfirmations = this.bookService.update(id, book);
            return new ResponseEntity<String>(putConfirmations, HttpStatus.OK);
        } catch (Exception e) {
            return new  ResponseEntity<String>("error to database connections " , HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/findAll")
    public ResponseEntity<List<BookDto>> findall(){
        try {
           List<Book> listOfBook = this.bookService.findAll();
           List<BookDto> bookDtoList = bookService.toBookDto(listOfBook);

           return ResponseEntity.ok(bookDtoList);

        } catch (Exception e) {
            return (ResponseEntity<List<BookDto>>) ResponseEntity.badRequest();
        }
    }
    @GetMapping("/findAll/active")
    public ResponseEntity<List<BookDto>> findallActive(){
        try {
            List<Book> listOfBook = this.bookService.findAllActive();
            List<BookDto> bookDtoList = bookService.toBookDto(listOfBook);

            return ResponseEntity.ok(bookDtoList);

        } catch (Exception e) {
            return (ResponseEntity<List<BookDto>>) ResponseEntity.badRequest();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id){
        try {
            String message = bookService.delete(id);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("/updatePatch/{id}")
    public ResponseEntity<String> updatePartial(@PathVariable long id , @RequestBody Map<String , Object> book){
        try {

            String message = this.bookService.applyPartialUpdate(id , book);
            return ResponseEntity.ok(message);

        } catch (Exception e) {
            return (ResponseEntity<String>) ResponseEntity.badRequest();
        }
    }
    @GetMapping("findBy/MinYearAndAuthor")
    public ResponseEntity<List<BookDto>> findByMinYearAndAuthor(@RequestParam String author, @RequestParam Integer year){
        try {
            LocalDate minDate = LocalDate.of(year, 1, 1);
            List<BookDto> books = bookService.findByFabricationYearGreaterThanEqualAndAuthor(minDate, author);

            return ResponseEntity.ok(books);
        } catch (Exception e) {
            return (ResponseEntity<List<BookDto>>) ResponseEntity.badRequest();
        }
    }
    @GetMapping("/findByCategory")
    public ResponseEntity<List<BookDto>> findByCategory(@RequestParam Category category){
        try {
            List<BookDto> bookDtoList =  bookService.findByCategory(category);
            return ResponseEntity.ok(bookDtoList);

        } catch (RuntimeException e) {
            return (ResponseEntity<List<BookDto>>) ResponseEntity.badRequest();
        }
    }
    @PatchMapping("/rent/{id}")
    public ResponseEntity<String> rentBook(@PathVariable long id){
        try {
            String message = bookService.rentBook( id);
            return ResponseEntity.ok(message);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The book already rented");
        }
    }
}
