package com.chylee.fxiaoke.configuration;

import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

public class CacheKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object o, Method method, Object... objects) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(o.getClass().getName())
                .append("_").append(method.getName());
        if (objects != null && objects.length > 0) {//把所有参数放进去
            for (Object obj : objects) {
               buffer.append("_").append(obj);
            }
        }

        System.out.println("*********************************" + buffer.toString());

        return buffer.toString();
    }
}
