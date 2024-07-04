package com.example.spring_boot_library.Controllers;

import com.example.spring_boot_library.Model.Book;
import com.example.spring_boot_library.Services.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    public List<Book> findAll() {
        return bookService.findAll();
    }

    @GetMapping
    public List<Book> findAllAvailable() {
        return bookService.findAllAvailable();
    }

    @GetMapping("/{id}")
    public Optional<Book> findById(@PathVariable String id) {
        return bookService.findById(id);
    }

    @PostMapping
    public Book save(@RequestBody Book book) {
        return bookService.save(book);
    }

    @PutMapping("/{id}")
    public Book update(@PathVariable String id, @RequestBody Book book) {
        book.setId(id);
        return bookService.save(book);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        bookService.deleteById(id);
    }
}