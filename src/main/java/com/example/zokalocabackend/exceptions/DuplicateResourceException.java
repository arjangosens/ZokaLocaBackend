package com.example.zokalocabackend.exceptions;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException() {
    }

    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
