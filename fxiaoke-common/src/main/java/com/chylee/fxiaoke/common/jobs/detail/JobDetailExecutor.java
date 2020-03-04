package com.chylee.fxiaoke.common.jobs.detail;

import com.chylee.fxiaoke.common.model.JobDetail;

public interface JobDetailExecutor {
    boolean isSupported(int typeId);
    boolean execute(JobDetail jobDetail);
}
