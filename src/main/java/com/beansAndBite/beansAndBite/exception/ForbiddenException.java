package com.beansAndBite.beansAndBite.exception;

//Purpose: Used when the client is authenticated but does not have the required permissions to access a resource.
//HTTP Status: 403 Forbidden
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
