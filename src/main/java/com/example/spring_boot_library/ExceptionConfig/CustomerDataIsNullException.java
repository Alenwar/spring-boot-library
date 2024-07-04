package com.example.spring_boot_library.ExceptionConfig;

public class CustomerDataIsNullException extends IllegalArgumentException{
    public CustomerDataIsNullException(String message) {
        super(message);
    }
}