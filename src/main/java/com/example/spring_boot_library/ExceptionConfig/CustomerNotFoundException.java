package com.example.spring_boot_library.ExceptionConfig;

public class CustomerNotFoundException extends IllegalArgumentException{
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
