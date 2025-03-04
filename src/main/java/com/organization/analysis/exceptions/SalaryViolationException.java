package com.organization.analysis.exceptions;

/**
 * Exception thrown when a manager's salary does not meet the required range
 */
public class SalaryViolationException extends RuntimeException {
    public SalaryViolationException(String message) {
        super(message);
    }
}
