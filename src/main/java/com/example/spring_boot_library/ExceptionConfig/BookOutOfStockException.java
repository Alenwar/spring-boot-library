package com.example.spring_boot_library.ExceptionConfig;

public class BookOutOfStockException extends IllegalArgumentException {
    public BookOutOfStockException(String message) {
        super(message);
    }
}