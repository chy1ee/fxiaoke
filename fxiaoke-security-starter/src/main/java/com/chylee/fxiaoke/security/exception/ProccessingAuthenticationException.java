package com.chylee.fxiaoke.security.exception;

public class ProccessingAuthenticationException extends AuthenticationException {
    private int code;

    public ProccessingAuthenticationException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
