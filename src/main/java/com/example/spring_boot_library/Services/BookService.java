package com.example.spring_boot_library.Services;

import com.example.spring_boot_library.ExceptionConfig.BookDataIsNullException;
import com.example.spring_boot_library.ExceptionConfig.BookNotFoundException;
import com.example.spring_boot_library.Model.Book;
import com.example.spring_boot_library.Repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> findAllAvailable() {
        return bookRepository.findByStockGreaterThan(0);
    }

    public Optional<Book> findById(String id) {
        if (bookRepository.findById(id).isPresent()) {
            return bookRepository.findById(id);
        }else {
            throw new BookNotFoundException("Book with ID " + id + " not found.");
        }
    }

    public Book save(Book book) {
        if  (book.getTitle() == null || book.getAuthor() == null) {
            throw new BookDataIsNullException("Book title:" + book.getTitle() + ", author:" + book.getAuthor() +
                    " at least one field is null.");
        } else {
            return bookRepository.save(book);
        }
    }

    public void deleteById(String id) {
        if (bookRepository.findById(id).isPresent()) {
            bookRepository.deleteById(id);
        } else {
            throw new BookNotFoundException("Book with ID " + id + " not found.");
        }
    }
}
