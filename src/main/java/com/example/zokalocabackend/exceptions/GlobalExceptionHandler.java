package com.example.zokalocabackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException ex) {
        String message = ex.getMessage().equals("No value present") ? "Resource not found" : ex.getMessage();
        return generateErrorResponseEntity(HttpStatus.NOT_FOUND, message);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResourceException(DuplicateResourceException ex) {
        return generateErrorResponseEntityFromException(HttpStatus.CONFLICT, ex, "Resource already exists");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return generateErrorResponseEntityFromException(HttpStatus.BAD_REQUEST, ex, "Invalid argument");
    }

    private String getMessage(Throwable ex, String fallbackMessage) {
        return ex.getMessage() != null ? ex.getMessage() : fallbackMessage;
    }

    private ResponseEntity<ErrorResponse> generateErrorResponseEntityFromException(HttpStatus status, Throwable ex, String fallbackMessage) {
        return this.generateErrorResponseEntity(status, getMessage(ex, fallbackMessage));
    }

    private ResponseEntity<ErrorResponse> generateErrorResponseEntity(HttpStatus status, String message) {
        String timestamp = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);

        return new ResponseEntity<>(new ErrorResponse(timestamp, status.name(), status.value(), message), status);
    }
}
