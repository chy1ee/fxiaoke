package com.chylee.fxiaoke.security.core.security;

import com.chylee.fxiaoke.security.exception.AuthenticationException;
import com.chylee.fxiaoke.security.exception.ProccessingAuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DefaultAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        if(failed instanceof ProccessingAuthenticationException) {
            ProccessingAuthenticationException e = (ProccessingAuthenticationException)failed;
            response.getWriter().write(String.format("{\"success\":false,\"code\":%d,\"message\":\"%s\"}",
                    e.getCode(), e.getMessage()));
        }
        else {
            response.getWriter().write(String.format("{\"success\":false,\"code\":-1,\"message\":\"%s\"}",
                    failed.getMessage()));
        }
    }
}
