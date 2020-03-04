package com.chylee.fxiaoke.quartz.jobs.support;

import java.util.Objects;

public class Result {
    private int logId;
    private int success;
    private int fail;

    public Result(int logId) {
        this.logId = logId;
        this.success = 0;
        this.fail = 0;
    }

    public void addSuccess() {
        this.success++;
    }

    public void addFail() {
        this.fail++;
    }

    public int getLogId() {
        return logId;
    }

    public int getSuccess() {
        return success;
    }

    public int getFail() {
        return fail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return logId == result.logId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(logId);
    }

    @Override
    public String toString() {
        return "Result{" +
                "logId=" + logId +
                ", success=" + success +
                ", fail=" + fail +
                '}';
    }
}
