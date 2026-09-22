package com.example.srms.exception;

/**
 * Thrown by the Service layer when a Student with a given id does not exist.
 * Translated into an HTTP 404 by {@link GlobalExceptionHandler}.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
