package com.chylee.fxiaoke.quartz.jobs.support;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ResultContext {
    private Map<Integer, Result> context = new ConcurrentHashMap<>();

    public void addSuccess(int logId) {
        this.getResult(logId).addSuccess();
    }

    public void addFail(int logId) {
        this.getResult(logId).addFail();
    }

    public Collection<Result> getContext() {
        return context.values();
    }

    private Result getResult(int logId) {
        Result result = context.get(logId);
        if (result == null) {
            result = new Result(logId);
            context.put(logId, result);
        }
        return result;
    }

}
