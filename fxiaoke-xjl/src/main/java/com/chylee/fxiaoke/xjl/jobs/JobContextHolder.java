package com.chylee.fxiaoke.xjl.jobs;

import java.util.List;

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

    public static void setSuccess() {
        getContext().setSuccess(true);
    }

    public static void setType(String type) {
        getContext().setType(type);
    }

    public static void setError(String error) {
        getContext().setError(error);
    }

    public static void setSerialNo(String serialNo) {
        getContext().setSerialNo(serialNo);
    }

    public static void setOwner(List<String> owner) {
        getContext().setOwner(owner);
    }

    public static void clean() {
        contextHolder.remove();
    }
}
