package com.chylee.fxiaoke.security.core.security;

import com.chylee.fxiaoke.security.exception.AuthenticationException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class AuthenticationManager implements ApplicationContextAware {
    private Collection<AuthenticationProvider> authenticationProviders;

    public Authentication authenticate (Authentication authentication) throws AuthenticationException {
        for (AuthenticationProvider provider : authenticationProviders) {
            Authentication result = provider.authenticate(authentication);
            if (result != null)
                return result;
        }

        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        authenticationProviders = applicationContext.getBeansOfType(AuthenticationProvider.class).values();
    }
}
