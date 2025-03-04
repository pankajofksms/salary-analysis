package com.organization.analysis.exceptions;

/**
 * Exception thrown when no CEO is found in the data.
 */
public class NoCEOFoundException extends RuntimeException {
    public NoCEOFoundException(String message) {
        super(message);
    }
}
