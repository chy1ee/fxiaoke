package com.chylee.fxiaoke.security.web.filter;

import com.chylee.fxiaoke.security.core.security.Authentication;
import com.chylee.fxiaoke.security.core.security.UserDetailsCacher;
import com.chylee.fxiaoke.security.exception.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutAuthenticationFilter extends AbstractAuthenticationFilter {

    private final UserDetailsCacher userDetailsCacher;

    public LogoutAuthenticationFilter(String defaultFilterProcessesUrl, UserDetailsCacher userDetailsCacher) {
        super(defaultFilterProcessesUrl);
        this.userDetailsCacher = userDetailsCacher;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
        String token = request.getHeader("X-Token");
        userDetailsCacher.delete(token);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"success\":true,\"code\":0}");
        return null;
    }
}
