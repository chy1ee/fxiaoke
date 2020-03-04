package com.chylee.fxiaoke.security.configuation;

import com.chylee.fxiaoke.security.core.security.*;
import com.chylee.fxiaoke.security.web.filter.LogoutAuthenticationFilter;
import com.chylee.fxiaoke.security.web.filter.ProccessingAuthenticationFilter;
import com.chylee.fxiaoke.security.web.filter.UsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthenticationConfig {

    @Value("${chylee.security.ignores:}")
    private String[] ignores;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnProperty(name = "chylee.security.enabled", havingValue = "true")
    public FilterRegistrationBean proccessingAuthenticationFilter(UserDetailsCacher userDetailsCacher) {
        FilterRegistrationBean registration = new FilterRegistrationBean(
                new ProccessingAuthenticationFilter(ignores, userDetailsCacher)
        );
        registration.addUrlPatterns("/*");
        registration.setName("proccessingAuthenticationFilter");
        registration.setOrder(3);
        return registration;
    }

    @Bean
    public FilterRegistrationBean logoutFilterRegistrationBean(UserDetailsCacher userDetailsCacher) {
        FilterRegistrationBean registration = new FilterRegistrationBean(
                new LogoutAuthenticationFilter("/user/logout", userDetailsCacher)
        );
        registration.addUrlPatterns("/*");
        registration.setName("logoutAuthenticationFilter");
        registration.setOrder(2);
        return registration;
    }

    @Bean
    public FilterRegistrationBean usernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager,
                                                                                     UsernamePasswordAuthenticationSuccessHandler successHandler) {
        UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter("/user/login");
        filter.setAuthenticationManager(authenticationManager);
        filter.setSuccessHandler(successHandler);

        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.addUrlPatterns("/*");
        registration.setName("usernamePasswordAuthenticationFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public UsernamePasswordAuthenticationSuccessHandler successHandler() {
        return new UsernamePasswordAuthenticationSuccessHandler();
    }
}
