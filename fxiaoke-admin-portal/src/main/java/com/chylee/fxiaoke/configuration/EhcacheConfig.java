package com.chylee.fxiaoke.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class EhcacheConfig {
    @Bean
    public KeyGenerator keyGenerator() {
        return new CacheKeyGenerator();
    }

}
