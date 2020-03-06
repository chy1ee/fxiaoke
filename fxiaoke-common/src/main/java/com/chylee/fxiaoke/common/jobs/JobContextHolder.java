package com.chylee.fxiaoke.common.jobs;

public class JobContextHolder {
    private final static ThreadLocal<JobContext> contextHolder = new ThreadLocal<>();

    public static JobContext getContext() {
        JobContext context = contextHolder.get();
        if (context == null) {
            context = new JobContext();
            contextHolder.set(context);
        }
        return context;
    }

    public static void clean() {
        contextHolder.remove();
    }
}
