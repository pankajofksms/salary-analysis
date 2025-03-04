package com.organization.analysis.exceptions;

/**
 * Exception thrown when an employee has too many managers between them and the CEO.
 */
public class TooManyManagersException extends RuntimeException {
    public TooManyManagersException(String message) {
        super(message);
    }
}
