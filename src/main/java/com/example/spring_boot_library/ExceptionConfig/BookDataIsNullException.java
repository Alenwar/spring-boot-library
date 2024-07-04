package com.example.spring_boot_library.ExceptionConfig;

public class BookDataIsNullException extends IllegalArgumentException{
    public BookDataIsNullException(String message) {
        super(message);
    }
}