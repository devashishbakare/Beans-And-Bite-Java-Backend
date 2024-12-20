package com.beansAndBite.beansAndBite.exception;

//Purpose: Used when the client is not authorized to access a resource.
//HTTP Status: 401 Unauthorized
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
