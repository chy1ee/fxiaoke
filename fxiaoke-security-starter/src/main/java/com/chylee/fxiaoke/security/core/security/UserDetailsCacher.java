package com.chylee.fxiaoke.security.core.security;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@CacheConfig(cacheNames="userDetailsCache")
public class UserDetailsCacher {

    @Cacheable(key="#token")
    public User get(String token, User user) {
        return user;
    }

    @CachePut(key="#token")
    public User reset(String token, User user) {
        return user;
    }

    @CacheEvict(key="#token")
    public User delete(String token) {
        return null;
    }
}
