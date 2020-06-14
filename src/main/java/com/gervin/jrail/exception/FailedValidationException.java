package com.gervin.jrail.exception;

public class FailedValidationException extends RuntimeException {
    public FailedValidationException() {
        super("Could not execute command on input with failed validation. " +
                  "Consider supplying a default input value or default return value.");
    }
}
