package com.chylee.fxiaoke.security.core.security;

import java.io.Serializable;

public class SecurityContext implements Serializable {
    private String token;
    private User user;
    private boolean authorized;

    public SecurityContext(String token, User user, boolean authorized) {
        this.token = token;
        this.user = user;
        this.authorized = authorized;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    public boolean isAuthorized() {
        return authorized;
    }
}
