package com.shifterwebapp.shifter.shared.exception;

public class PaymentNotCompleteException extends RuntimeException {
    public PaymentNotCompleteException(String message) {
        super(message);
    }
}
