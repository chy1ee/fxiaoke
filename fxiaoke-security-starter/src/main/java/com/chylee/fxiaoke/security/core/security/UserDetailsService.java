package com.chylee.fxiaoke.security.core.security;

import com.chylee.fxiaoke.security.exception.UsernameNotFoundException;

public interface UserDetailsService {
    User loadUserByUsername(String username) throws UsernameNotFoundException;
}
