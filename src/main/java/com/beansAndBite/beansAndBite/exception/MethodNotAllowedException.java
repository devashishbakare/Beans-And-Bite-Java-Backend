package com.beansAndBite.beansAndBite.exception;

//Purpose: Used when the client tries to access a resource using an unsupported HTTP method.
//HTTP Status: 405 Method Not Allowed
public class MethodNotAllowedException extends RuntimeException {
    public MethodNotAllowedException(String message) {
        super(message);
    }
}
