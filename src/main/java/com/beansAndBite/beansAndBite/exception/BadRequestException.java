package com.beansAndBite.beansAndBite.exception;

//Purpose: Used when the client sends invalid data or the request cannot be processed due to client errors.
//HTTP Status: 400 Bad Request
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
