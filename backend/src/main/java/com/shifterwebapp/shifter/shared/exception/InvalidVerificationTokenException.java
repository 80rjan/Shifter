package com.shifterwebapp.shifter.shared.exception;

public class InvalidVerificationTokenException extends RuntimeException {
    public InvalidVerificationTokenException(String message) {
        super(message);
    }
}
