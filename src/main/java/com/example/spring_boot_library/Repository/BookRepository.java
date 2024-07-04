package com.example.spring_boot_library.Repository;

import com.example.spring_boot_library.Model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findByStockGreaterThan(int stock);
}