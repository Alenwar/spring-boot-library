package com.example.spring_boot_library.Controllers;

import com.example.spring_boot_library.Model.BorrowRecord;
import com.example.spring_boot_library.Services.BorrowRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/borrowRecords")
public class BorrowRecordController {

    private final BorrowRecordService borrowRecordService;

    public BorrowRecordController(BorrowRecordService borrowRecordService) {
        this.borrowRecordService = borrowRecordService;
    }

    @GetMapping
    public List<BorrowRecord> findAll() {
        return borrowRecordService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<BorrowRecord> findById(@PathVariable String id) {
        return borrowRecordService.findById(id);
    }

    @PostMapping("/borrow")
    public BorrowRecord borrowBook(@RequestBody BorrowRecord record) {
        return borrowRecordService.borrowBook(record.getCustomerId(), record.getBookId());
    }

    @PostMapping("/return/{id}")
    public BorrowRecord returnBook(@PathVariable String id) {
        return borrowRecordService.returnBook(id);
    }
}