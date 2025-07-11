package com.shifterwebapp.shifter.exception;

public class PaymentNotCompleteException extends RuntimeException {
    public PaymentNotCompleteException(String message) {
        super(message);
    }
}
