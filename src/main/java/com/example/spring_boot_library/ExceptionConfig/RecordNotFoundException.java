package com.example.spring_boot_library.ExceptionConfig;

public class RecordNotFoundException extends IllegalArgumentException {
    public RecordNotFoundException(String message) {
        super(message);
    }
}
