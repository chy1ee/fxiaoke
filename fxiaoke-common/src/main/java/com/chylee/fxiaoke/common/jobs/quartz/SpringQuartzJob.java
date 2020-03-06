package com.chylee.fxiaoke.common.jobs.quartz;

public interface SpringQuartzJob {
    void invoke(int qrtzId, String params) throws Exception;
}
