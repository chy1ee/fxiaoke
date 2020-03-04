package com.chylee.fxiaoke.security.core.security;

public interface User {
    int getId();
    String getUsername();
    String getPassword();
    String getNickname();
    int getDeptId();
    void erasePassword();
}
