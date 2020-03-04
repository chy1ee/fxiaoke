package com.chylee.fxiaoke.security.core.security;

public class SecurityContextHolder {
    private static final ThreadLocal<SecurityContext> contextHolder = new ThreadLocal();

    public static void setContext(SecurityContext context) {
        contextHolder.set(context);
    }

    public static SecurityContext getContext() {
        SecurityContext ctx = contextHolder.get();
        if (ctx == null) {
            ctx = new SecurityContext(null, null, false);
            contextHolder.set(ctx);
        }
        return ctx;
    }

    public static void clearContext() {
        contextHolder.remove();
    }
}
