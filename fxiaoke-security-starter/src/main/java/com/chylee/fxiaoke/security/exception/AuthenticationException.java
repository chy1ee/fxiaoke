package com.chylee.fxiaoke.security.exception;

public class AuthenticationException extends Exception {
    private String message;

    public AuthenticationException(String message) {
        super(message);
        this.message = message;
    }

    public AuthenticationException(String message, Throwable throwable) {
        super(message, throwable);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
