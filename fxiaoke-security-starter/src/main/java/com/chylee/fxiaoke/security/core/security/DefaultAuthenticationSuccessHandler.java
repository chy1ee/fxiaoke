package com.chylee.fxiaoke.security.core.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DefaultAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authResult)
            throws IOException {
        if(!(authResult instanceof NullAuthentication)) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"success\":true,\"code\":0}");
        }
    }
}
