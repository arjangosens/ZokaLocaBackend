package com.example.zokalocabackend.exceptions;

import jakarta.validation.ConstraintViolationException;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@NoArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException ex) {
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("NoSuchElementException: {}", ex.getMessage(), ex);
        }
        String message = ex.getMessage().equals("No value present") ? "Resource not found" : ex.getMessage();
        return generateErrorResponseEntity(HttpStatus.NOT_FOUND, message);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResourceException(DuplicateResourceException ex) {
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("DuplicateResourceException: {}", ex.getMessage(), ex);
        }
        return generateErrorResponseEntityFromException(HttpStatus.CONFLICT, ex, "Resource already exists");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("IllegalArgumentException: {}", ex.getMessage(), ex);
        }
        return generateErrorResponseEntityFromException(HttpStatus.BAD_REQUEST, ex, "Invalid argument");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("MethodArgumentNotValidException: {}", ex.getMessage(), ex);
        }
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return generateErrorResponseEntity(HttpStatus.BAD_REQUEST, errors.entrySet().stream()
                .map(entry -> entry.getKey() + " " + entry.getValue())
                .collect(Collectors.joining(", ")));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("ConstraintViolationException: {}", ex.getMessage(), ex);
        }
        return generateErrorResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
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