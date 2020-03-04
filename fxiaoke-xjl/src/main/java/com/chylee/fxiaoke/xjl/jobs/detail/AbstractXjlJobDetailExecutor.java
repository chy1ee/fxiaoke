package com.chylee.fxiaoke.xjl.jobs.detail;

import com.chylee.fxiaoke.common.exception.*;
import com.chylee.fxiaoke.common.jobs.detail.AbstractJobDetailExecutor;
import com.chylee.fxiaoke.common.jobs.detail.JobDetailExecutor;
import com.chylee.fxiaoke.common.service.JobDetailService;
import com.chylee.fxiaoke.common.service.SysReportService;
import com.chylee.fxiaoke.core.service.FXKSequenceService;

public abstract class AbstractXjlJobDetailExecutor extends AbstractJobDetailExecutor implements JobDetailExecutor {
    private final FXKSequenceService sequenceService;

    protected AbstractXjlJobDetailExecutor(JobDetailService jobDetailService, SysReportService reportService, FXKSequenceService sequenceService) {
        super(jobDetailService, reportService);
        this.sequenceService = sequenceService;
    }

    protected String getDanhao(String apiName) throws ErpDataException {
        try {
            return sequenceService.createXh(apiName);
        } catch (Exception e) {
            String error = "获取单号失败["+apiName+"]";
            logger.error(error, e);
            throw new ErpDataException(error);
        }
    }
}
