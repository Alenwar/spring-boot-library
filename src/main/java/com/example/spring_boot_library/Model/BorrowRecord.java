package com.example.spring_boot_library.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "borrowRecords")
public class BorrowRecord {
    @Id
    private String id;
    private String customerId;
    private String bookId;
    private Date borrowDate;
    private Date returnDate;
}