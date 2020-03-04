package com.chylee.fxiaoke.common.jobs.detail;

import com.chylee.fxiaoke.common.api.ComplieModeSupported;
import com.chylee.fxiaoke.common.event.Event;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.exception.CrmDataException;
import com.chylee.fxiaoke.common.exception.ErpDataException;
import com.chylee.fxiaoke.common.exception.ExecutorExcetpion;
import com.chylee.fxiaoke.common.jobs.JobContext;
import com.chylee.fxiaoke.common.jobs.JobContextHolder;
import com.chylee.fxiaoke.common.model.JobDetail;
import com.chylee.fxiaoke.common.service.JobDetailService;
import com.chylee.fxiaoke.common.service.SysReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractJobDetailExecutor extends ComplieModeSupported implements JobDetailExecutor {
    protected Logger logger = LoggerFactory.getLogger(AbstractJobDetailExecutor.class);

    private final JobDetailService jobDetailService;
    private final SysReportService reportService;

    protected AbstractJobDetailExecutor(JobDetailService jobDetailService, SysReportService reportService) {
        this.jobDetailService = jobDetailService;
        this.reportService = reportService;
    }

    @Override
    public boolean execute(JobDetail jobDetail) {
        try {
            invokeTask(jobDetail);
            return true;
        } catch (Exception e) {
            String error;
            if (e instanceof ExecutorExcetpion) {
                error = ((ExecutorExcetpion)e).getMsg();
            }
            else if (e.getMessage() == null) {
                error = "其他错误";
            }
            else {
                error = e.getMessage();
            }

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

    protected abstract void saveEvent(Event event) throws ErpDataException, CrmDataException, CrmApiException;

    protected abstract Event createEvent(JobDetail jobDetail) throws CrmApiException, CrmDataException, ErpDataException;

    protected void Debug(String msg, Object... objects) {
        if (logger.isDebugEnabled())
            logger.debug(msg, objects);
    }
}
