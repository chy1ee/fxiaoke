package com.chylee.fxiaoke.security.core.security;

import com.chylee.fxiaoke.security.exception.AuthenticationException;

public interface AuthenticationProvider {
    boolean isSupport(Authentication authentication);
    Authentication authenticate(Authentication authentication) throws AuthenticationException;
}
