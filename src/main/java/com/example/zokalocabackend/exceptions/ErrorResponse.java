package com.example.zokalocabackend.exceptions;

public record ErrorResponse(String timestamp, String error, int statusCode, String message) {}