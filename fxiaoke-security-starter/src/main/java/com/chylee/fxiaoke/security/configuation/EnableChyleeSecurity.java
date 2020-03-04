package com.chylee.fxiaoke.security.configuation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Configuration
@Import(AuthenticationConfig.class)
@ComponentScan("com.chylee.fxiaoke.security.core.security")
public @interface EnableChyleeSecurity {
}
