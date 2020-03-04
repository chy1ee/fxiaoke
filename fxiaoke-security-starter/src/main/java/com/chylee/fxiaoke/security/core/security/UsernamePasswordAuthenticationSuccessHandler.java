package com.chylee.fxiaoke.security.core.security;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class UsernamePasswordAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UserDetailsCacher userDetailsCacher;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authResult)
            throws IOException {
        UsernamePasswordAuthentication authentication = (UsernamePasswordAuthentication)authResult;
        String token = UUID.randomUUID().toString().replaceAll("-","");
        userDetailsCacher.get(token, authentication.getUser());

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"success\":true,\"token\":\""+token+"\"}");
    }
}
