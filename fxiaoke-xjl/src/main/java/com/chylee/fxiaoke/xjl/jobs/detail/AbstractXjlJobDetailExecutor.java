package com.chylee.fxiaoke.xjl.jobs.detail;

import com.chylee.fxiaoke.common.event.Event;
import com.chylee.fxiaoke.common.exception.*;
import com.chylee.fxiaoke.common.jobs.detail.AbstractJobDetailExecutor;
import com.chylee.fxiaoke.common.jobs.detail.JobDetailExecutor;
import com.chylee.fxiaoke.common.model.JobDetail;
import com.chylee.fxiaoke.common.service.JobDetailService;
import com.chylee.fxiaoke.common.service.SysReportService;
import com.chylee.fxiaoke.core.service.FXKSequenceService;
import com.chylee.fxiaoke.xjl.jobs.JobContext;
import com.chylee.fxiaoke.xjl.jobs.JobContextHolder;

public abstract class AbstractXjlJobDetailExecutor extends AbstractJobDetailExecutor implements JobDetailExecutor {
    private final JobDetailService jobDetailService;
    private final SysReportService reportService;
    private final FXKSequenceService sequenceService;

    protected AbstractXjlJobDetailExecutor(JobDetailService jobDetailService, SysReportService reportService,
                                           FXKSequenceService sequenceService) {
        this.jobDetailService = jobDetailService;
        this.reportService = reportService;
        this.sequenceService = sequenceService;
    }

    @Override
    public boolean execute(JobDetail jobDetail) {
        try {
            invokeTask(jobDetail);
            return true;
        } catch (Exception e) {
            String error = e instanceof ExecutorExcetpion ?
                    ((ExecutorExcetpion)e).getMsg() :
                    e.getMessage() == null ? "其他错误" : e.getMessage();
            JobContextHolder.setError(error);
            return false;
        } finally {
            JobContext context = JobContextHolder.getContext();
            boolean success = context.isSuccess();
            String seriaNo = context.getSerialNo();
            String error = context.getError();

            Debug(context.toString());

            //状态日志
            jobDetailService.upateStatusById(jobDetail.getId(),
                    success ? 1 : -1,
                    success ? seriaNo : error);

            //状态报告
            if (!context.isEmpty()) {
                reportService.sendExecutorReport(
                        context.getOwner(),
                        context.getType(),
                        context.getSerialNo(),
                        context.getError());
            }

            //释放jobCcontext
            JobContextHolder.clean();
        }
    }

    protected void invokeTask(JobDetail jobDetail)
            throws CrmApiException, CrmDataException, ErpDataException {
        saveEvent(createEvent(jobDetail));
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

    protected abstract void saveEvent(Event event) throws ErpDataException, CrmDataException, CrmApiException;

    protected abstract Event createEvent(JobDetail jobDetail) throws CrmApiException, CrmDataException, ErpDataException;
}
