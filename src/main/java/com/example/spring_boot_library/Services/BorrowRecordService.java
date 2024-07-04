package com.example.spring_boot_library.Services;

import com.example.spring_boot_library.ExceptionConfig.BookNotFoundException;
import com.example.spring_boot_library.ExceptionConfig.BookOutOfStockException;
import com.example.spring_boot_library.ExceptionConfig.CustomerNotFoundException;
import com.example.spring_boot_library.ExceptionConfig.RecordNotFoundException;
import com.example.spring_boot_library.Model.Book;
import com.example.spring_boot_library.Model.BorrowRecord;
import com.example.spring_boot_library.Model.Customer;
import com.example.spring_boot_library.Repository.BookRepository;
import com.example.spring_boot_library.Repository.BorrowRecordRepository;
import com.example.spring_boot_library.Repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowRecordService {

    private final BorrowRecordRepository borrowRecordRepository;

    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;

    public BorrowRecordService(BorrowRecordRepository borrowRecordRepository, BookRepository bookRepository, CustomerRepository customerRepository) {
        this.borrowRecordRepository = borrowRecordRepository;
        this.bookRepository = bookRepository;
        this.customerRepository = customerRepository;
    }

    public List<BorrowRecord> findAll() {
        return borrowRecordRepository.findAll();
    }

    public Optional<BorrowRecord> findById(String id) {
        if (borrowRecordRepository.findById(id).isPresent()) {
            return borrowRecordRepository.findById(id);
        } else {
            throw new RecordNotFoundException("Borrow record with id " + id + " not found");
        }
    }

    public BorrowRecord borrowBook(String customerId, String bookId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent() && customerOptional.isPresent()) {
            Book book = bookOptional.get();
            if (book.getStock() > 0) {
                book.setStock(book.getStock() - 1);
                bookRepository.save(book);

                BorrowRecord borrowRecord = new BorrowRecord();
                borrowRecord.setCustomerId(customerId);
                borrowRecord.setBookId(bookId);
                borrowRecord.setBorrowDate(new Date());
                return borrowRecordRepository.save(borrowRecord);
            } else {
                throw new BookOutOfStockException("Book with ID " + bookId + " is out of stock.");
            }
        }if (bookOptional.isEmpty()) {
            throw new BookNotFoundException("Book with ID " + bookId + " not found.");
        } else {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " not found.");
        }
    }

    public BorrowRecord returnBook(String recordId) {
        Optional<BorrowRecord> recordOptional = borrowRecordRepository.findById(recordId);
        if (recordOptional.isPresent()) {
            BorrowRecord record = recordOptional.get();
            if (record.getReturnDate() == null) {
                record.setReturnDate(new Date());
                borrowRecordRepository.save(record);

                Optional<Book> bookOptional = bookRepository.findById(record.getBookId());
                if (bookOptional.isPresent()) {
                    Book book = bookOptional.get();
                    book.setStock(book.getStock() + 1);
                    bookRepository.save(book);
                }
                return record;
            }
        }
        throw new RecordNotFoundException("Borrow record with ID " + recordId + " not found.");
    }
}
