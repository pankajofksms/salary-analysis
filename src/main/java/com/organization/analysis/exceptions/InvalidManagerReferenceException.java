package com.organization.analysis.exceptions;

/**
 * Exception thrown when an employee has an invalid manager reference (manager not found).
 */
public class InvalidManagerReferenceException extends RuntimeException {
    public InvalidManagerReferenceException(String message) {
        super(message);
    }
}
