package com.shifterwebapp.shifter.shared.exception;

public class AlreadyEnrolledException extends RuntimeException {
    public AlreadyEnrolledException(String message) {
        super(message);
    }
}
