package com.chylee.fxiaoke.security.web.filter;

import com.chylee.fxiaoke.security.core.security.*;
import com.chylee.fxiaoke.security.exception.AuthenticationException;
import com.chylee.fxiaoke.security.exception.ProccessingAuthenticationException;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProccessingAuthenticationFilter extends AbstractAuthenticationFilter {

    private final String[] ignores;
    private final UserDetailsCacher userDetailsCacher;

    private PathMatcher pathMatcher = new AntPathMatcher();

    public ProccessingAuthenticationFilter(String[] ignores, UserDetailsCacher userDetailsCacher) {
        super("/**");
        this.ignores = ignores;
        this.userDetailsCacher = userDetailsCacher;
        this.setFailureHandler(new DefaultAuthenticationFailureHandler());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        if (!this.requiresAuthentication(request, response) || isIgnore(request.getRequestURI())) {
            filterChain.doFilter(request, response);
        }
        else {
            if (this.logger.isDebugEnabled())
                this.logger.debug("Request is to process authentication");

            try {
                this.attemptAuthentication(request, response);
            } catch (AuthenticationException e) {
                this.failureHandler.onAuthenticationFailure(request, response, e);
                return;
            }

            try {
                filterChain.doFilter(request, response);
            } finally {
                if (this.logger.isDebugEnabled())
                    this.logger.debug("releasing SecurityContextHolder");
                SecurityContextHolder.clearContext();
            }
        }
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
        String token = request.getHeader("X-Token");
        if (token != null) {
            User user = userDetailsCacher.get(token, null);
            if (user != null) {
                if (this.logger.isDebugEnabled())
                    this.logger.debug("initializing SecurityContextHolder : " + user.getNickname());
                SecurityContextHolder.setContext(new SecurityContext(token, user, true));
            }
        }
        else if (this.logger.isDebugEnabled()) {
            this.logger.debug("can not found token");
            throw new ProccessingAuthenticationException(50008, null);
        }

        SecurityContext context = SecurityContextHolder.getContext();
        if ((context == null || !context.isAuthorized()))
            throw new ProccessingAuthenticationException(50008, "登录已过期，请重新登录");

        return new NullAuthentication();
    }

    private boolean isIgnore(String uri) {
        if (ignores != null) {
            for (String ignore : ignores) {
                if (pathMatcher.match(ignore, uri))
                    return true;
            }
        }

        return false;
    }
}