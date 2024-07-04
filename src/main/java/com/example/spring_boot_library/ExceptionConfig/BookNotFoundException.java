package com.example.spring_boot_library.ExceptionConfig;

public class BookNotFoundException extends IllegalArgumentException{

    public BookNotFoundException(String message) {
        super(message);
    }
}
