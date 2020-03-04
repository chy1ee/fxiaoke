package com.chylee.fxiaoke.quartz;

public interface SpringQuartzJob {
    void invoke(int qrtzId, String params) throws Exception;
}
