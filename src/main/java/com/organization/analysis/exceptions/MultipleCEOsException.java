package com.organization.analysis.exceptions;

/**
 * Exception thrown when multiple CEOs are found in the data.
 */
public class MultipleCEOsException extends RuntimeException {
    public MultipleCEOsException(String message) {
        super(message);
    }
}
