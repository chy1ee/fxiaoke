package com.chylee.fxiaoke.security.web.filter;

import com.chylee.fxiaoke.security.core.security.*;
import com.chylee.fxiaoke.security.exception.AuthenticationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractAuthenticationFilter implements Filter {
    protected final Log logger = LogFactory.getLog(this.getClass());

    private boolean continueChainBeforeSuccessfulAuthentication = false;

    protected AuthenticationSuccessHandler successHandler;
    protected AuthenticationFailureHandler failureHandler;

    private RequestMatcher requiresAuthenticationRequestMatcher;
    private AuthenticationManager authenticationManager;

    protected AbstractAuthenticationFilter(String defaultFilterProcessesUrl) {
        this.setFilterProcessesUrl(defaultFilterProcessesUrl);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        if (!this.requiresAuthentication(request, response)) {
            filterChain.doFilter(request, response);
        }
        else {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Request is to process authentication");
            }

            Authentication authentication;
            try {
                authentication = this.attemptAuthentication(request, response);
                if (authentication == null)
                    return;
            } catch (AuthenticationException e) {
                this.failureHandler.onAuthenticationFailure(request, response, e);
                return;
            }

            if (this.continueChainBeforeSuccessfulAuthentication)
                filterChain.doFilter(request, response);

            this.successHandler.onAuthenticationSuccess(request, response, authentication);
        }
    }

    public abstract Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException;

    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return this.requiresAuthenticationRequestMatcher.matches(request);
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(filterProcessesUrl));
    }

    public final void setRequiresAuthenticationRequestMatcher(RequestMatcher requestMatcher) {
        this.requiresAuthenticationRequestMatcher = requestMatcher;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public void setSuccessHandler(AuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    public void setFailureHandler(AuthenticationFailureHandler failureHandler) {
        this.failureHandler = failureHandler;
    }

    public void setContinueChainBeforeSuccessfulAuthentication(boolean continueChainBeforeSuccessfulAuthentication) {
        this.continueChainBeforeSuccessfulAuthentication = continueChainBeforeSuccessfulAuthentication;
    }
}
