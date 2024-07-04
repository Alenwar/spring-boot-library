package com.example.spring_boot_library.Services;

import com.example.spring_boot_library.ExceptionConfig.BookDataIsNullException;
import com.example.spring_boot_library.ExceptionConfig.BookNotFoundException;
import com.example.spring_boot_library.Model.Book;
import com.example.spring_boot_library.Repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveBookWithNullData() {
        Book book = new Book();
        book.setId("1");
        BookDataIsNullException exception = assertThrows(BookDataIsNullException.class, () -> bookService.save(book));
        assertEquals("Book title:null, author:null at least one field is null.", exception.getMessage());
    }


    @Test
    void saveBookWithValidData() {
        Book book = new Book();
        book.setTitle("Test Book 1");
        book.setAuthor("Alona Olchowska");
        book.setStock(10);

        when(bookRepository.save(book)).thenReturn(book);

        Book savedBook = bookService.save(book);

        assertEquals(book, savedBook);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void findNonExistentBookById() {
        String bookId = "nonExistentBookId";
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> bookService.findById(bookId).
                orElseThrow(() -> new BookNotFoundException("Book with ID " + bookId + " not found.")));
        assertEquals("Book with ID " + bookId + " not found.", exception.getMessage());
    }
}
