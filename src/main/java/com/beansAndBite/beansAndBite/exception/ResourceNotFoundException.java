package com.beansAndBite.beansAndBite.exception;

//Purpose: Used when a requested resource (e.g., a database record) is not found
//HTTP Status: 404 Not Found
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
