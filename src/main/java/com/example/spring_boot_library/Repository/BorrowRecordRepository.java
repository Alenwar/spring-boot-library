package com.example.spring_boot_library.Repository;

import com.example.spring_boot_library.Model.BorrowRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BorrowRecordRepository extends MongoRepository<BorrowRecord, String> {
}