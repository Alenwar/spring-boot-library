package com.example.spring_boot_library.Services;

import com.example.spring_boot_library.ExceptionConfig.BookNotFoundException;
import com.example.spring_boot_library.ExceptionConfig.BookOutOfStockException;
import com.example.spring_boot_library.ExceptionConfig.RecordNotFoundException;
import com.example.spring_boot_library.Model.Book;
import com.example.spring_boot_library.Model.BorrowRecord;
import com.example.spring_boot_library.Model.Customer;
import com.example.spring_boot_library.Repository.BookRepository;
import com.example.spring_boot_library.Repository.BorrowRecordRepository;
import com.example.spring_boot_library.Repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BorrowRecordServiceTest {

    @InjectMocks
    private BorrowRecordService borrowRecordService;

    @Mock
    private BorrowRecordRepository borrowRecordRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Book createTestBook(String id, String title, String author, int stock) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setStock(stock);
        return book;
    }

    private Customer createTestCustomer(String id, String name, String email) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(name);
        customer.setEmail(email);
        return customer;
    }

    private BorrowRecord createTestBorrowRecord(String id, String customerId, String bookId, Date borrowDate, Date returnDate) {
        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setId(id);
        borrowRecord.setCustomerId(customerId);
        borrowRecord.setBookId(bookId);
        borrowRecord.setBorrowDate(borrowDate);
        borrowRecord.setReturnDate(returnDate);
        return borrowRecord;
    }

    @Test
    void borrowBookWithNonExistentBookId() {
        String bookId = "nonExistentBookId";
        String customerId = "customer1";
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () ->
                borrowRecordService.borrowBook(customerId, bookId));
        assertEquals("Book with ID " + bookId + " not found.", exception.getMessage());
    }

    @Test
    void borrowBookWhenBookIsOutOfStock() {
        String bookId = "book1";
        String customerId = "customer1";

        Book book = createTestBook(bookId, "Test Title", "Test Author", 0);
        Customer customer = createTestCustomer(customerId, "Test Customer", "test@test.com");

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        BookOutOfStockException exception = assertThrows(BookOutOfStockException.class, () ->
                borrowRecordService.borrowBook(customerId, bookId));
        assertEquals("Book with ID " + bookId + " is out of stock.", exception.getMessage());
    }

    @Test
    void borrowBookWithValidData() {
        String bookId = "book1";
        String customerId = "customer1";
        Date borrowDate = new Date();
        Date returnDate = new Date();

        Book book = createTestBook(bookId, "Test Title", "Test Author", 1);
        Customer customer = createTestCustomer(customerId, "Test Customer", "test@test.com");

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        BorrowRecord borrowRecord = createTestBorrowRecord("1",customerId, bookId, borrowDate, returnDate);

        when(borrowRecordRepository.save(any(BorrowRecord.class))).thenReturn(borrowRecord);

        BorrowRecord result = borrowRecordService.borrowBook(customerId, bookId);

        assertEquals(customerId, result.getCustomerId());
        assertEquals(bookId, result.getBookId());
        verify(bookRepository, times(1)).save(book);
        verify(borrowRecordRepository, times(1)).save(any(BorrowRecord.class));
    }


    @Test
    void returnBookWithInvalidRecordId() {
        String recordId = "nonExistentRecordId";
        String bookId = "book1";
        String customerId = "customer1";

        Date borrowDate = new Date();
        Date returnDate = new Date();

        Book book = createTestBook(bookId, "Test Title", "Test Author", 1);
        Customer customer = createTestCustomer(customerId, "Test Customer", "test@test.com");

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        BorrowRecord borrowRecord = createTestBorrowRecord("1",customerId, bookId, borrowDate, returnDate);

        when(borrowRecordRepository.save(any(BorrowRecord.class))).thenReturn(borrowRecord);
        when(borrowRecordRepository.findById(recordId)).thenReturn(Optional.empty());

        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () ->
                borrowRecordService.returnBook(recordId));
        assertEquals("Borrow record with ID " + recordId + " not found.", exception.getMessage());
    }
}
