package com.chylee.fxiaoke.security.web.filter;

import com.chylee.fxiaoke.security.core.security.Authentication;
import com.chylee.fxiaoke.security.core.security.UsernamePasswordAuthentication;
import com.chylee.fxiaoke.security.core.security.UsernamePasswordAuthenticationFailureHandler;
import com.chylee.fxiaoke.security.core.security.UsernamePasswordAuthenticationSuccessHandler;
import com.chylee.fxiaoke.security.exception.AuthenticationException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class UsernamePasswordAuthenticationFilter extends AbstractAuthenticationFilter {
    private String usernameParam = "username";
    private String passwordParam = "password";
    private boolean postOnly = true;

    public UsernamePasswordAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);

        this.setSuccessHandler(new UsernamePasswordAuthenticationSuccessHandler());
        this.setFailureHandler(new UsernamePasswordAuthenticationFailureHandler());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
        if (this.postOnly && !request.getMethod().equals("POST"))
            throw new AuthenticationException("不支持" + request.getMethod());

        UsernamePasswordAuthentication authentication = new UsernamePasswordAuthentication();
        // authentication.setUsername(request.getParameter(usernameParam));
        // authentication.setPassword(request.getParameter(passwordParam));

        ObjectMapper mapper = new ObjectMapper();
        Map result = null;
        result = mapper.readValue(request.getReader(), Map.class);
        authentication.setUsername((String)result.get(usernameParam));
        authentication.setPassword((String)result.get(passwordParam));

        return this.getAuthenticationManager().authenticate(authentication);
    }


}
