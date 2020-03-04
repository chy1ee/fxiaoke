package com.chylee.fxiaoke.security.core.security;

import com.chylee.fxiaoke.security.exception.AuthenticationException;
import com.chylee.fxiaoke.security.exception.PasswordAuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    public UsernamePasswordAuthenticationProvider(UserDetailsService userDetailsService,
                                                  PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean isSupport(Authentication authentication) {
        return authentication instanceof UsernamePasswordAuthentication;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        UsernamePasswordAuthentication usernamePasswordAuthentication = (UsernamePasswordAuthentication)authentication;

        User user = userDetailsService.loadUserByUsername(usernamePasswordAuthentication.getUsername());

        verifyPassword(user, usernamePasswordAuthentication);

        user.erasePassword();
        usernamePasswordAuthentication.setUser(user);

        return usernamePasswordAuthentication;
    }

    private void verifyPassword(User user, UsernamePasswordAuthentication authentication)
            throws PasswordAuthenticationException {
        String password = authentication.getPassword();
        if(!passwordEncoder.matches(password, user.getPassword()))
            throw new PasswordAuthenticationException("密码错误");
    }
}
