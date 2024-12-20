package com.beansAndBite.beansAndBite.exception;

//Purpose: Used when an unexpected server-side error occurs.
//HTTP Status: 500 Internal Server Error

public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String message) {
        super(message);
    }
}
