package com.example.srms.exception;

/**
 * Thrown by the Service layer when a create/update would violate the
 * uniqueness constraint on rollNumber. Translated into an HTTP 400 by
 * {@link GlobalExceptionHandler}.
 */
public class DuplicateRollNumberException extends RuntimeException {
    public DuplicateRollNumberException(String message) {
        super(message);
    }
}
