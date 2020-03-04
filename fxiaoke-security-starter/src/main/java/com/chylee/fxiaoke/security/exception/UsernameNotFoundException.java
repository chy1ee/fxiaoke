package com.chylee.fxiaoke.security.exception;

public class UsernameNotFoundException extends AuthenticationException {
    public UsernameNotFoundException(String message) {
        super(message);
    }
}
