package com.shifterwebapp.shifter.exception;

public class GoogleCalendarException extends RuntimeException {
    private final boolean needsReauth;

    public GoogleCalendarException(String message, Throwable cause, boolean needsReauth) {
        super(message, cause);
        this.needsReauth = needsReauth;
    }

    public boolean isNeedsReauth() {
        return needsReauth;
    }
}
