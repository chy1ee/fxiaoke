package com.chylee.fxiaoke.common.jobs.detail;

import com.chylee.fxiaoke.common.api.ComplieModeSupported;
import com.chylee.fxiaoke.common.api.Constants;
import com.chylee.fxiaoke.common.event.Event;
import com.chylee.fxiaoke.common.event.ResponseEvent;
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
        boolean success = false;
        int status = -1;
        try {
            invokeTask(jobDetail);
            status = 1;
            success = true;
            return true;
        } catch (Exception e) {
            String error;
            if (e instanceof ExecutorExcetpion) {
                ExecutorExcetpion excetpion = (ExecutorExcetpion)e;
                error = excetpion.getMsg();

                // 回填失败，不允许重发
                if (excetpion.getCode() == Constants.interfaceResponseCode.EXECUTOR_WRITE_BACK_ERROR.code)
                    status = -2;
                else {
                    if (isDevMode())
                        Debug("[开发模式]回写对接结果失败[{}][{}]", jobDetail.getDataId(), error);
                    else
                        writeErrorTo(jobDetail.getDataId(), error);
                }
            }
            else if (e.getMessage() == null) {
                logger.error("未知错误", e);
                error = "其他错误";
            }
            else {
                error = e.getMessage();
            }

            JobContextHolder.getContext().setMessage(error);
            return false;
        } finally {
            JobContext context = JobContextHolder.getContext();
            String seriaNo = context.getSerialNo();
            String message = context.getMessage();

            Debug(context.toString());

            //状态日志
            jobDetailService.upateStatusById(jobDetail.getId(),
                    success ? 1 : status,
                    success ? seriaNo : message);

            //状态报告
            if (!context.isEmpty()) {
                reportService.sendExecutorReport(
                        context.getOwner(),
                        context.getType(),
                        seriaNo,
                        success,
                        message);
            }

            //释放jobCcontext
            JobContextHolder.clean();
        }
    }

    protected void invokeTask(JobDetail jobDetail)
            throws CrmApiException, CrmDataException, ErpDataException {
        Event event = createEvent(jobDetail);
        ResponseEvent saveRespEvent = saveEvent(event);
        if (isDevMode())
            Debug("开发模式，不回写结果");
        else
            writeResultTo(event, saveRespEvent);
    }

    protected abstract void writeErrorTo(String dataId, String error);

    protected abstract void writeResultTo(Event event, ResponseEvent resp) throws CrmApiException, ErpDataException;

    protected abstract ResponseEvent saveEvent(Event event) throws ErpDataException, CrmDataException, CrmApiException;

    protected abstract Event createEvent(JobDetail jobDetail) throws CrmApiException, CrmDataException, ErpDataException;

    protected void Debug(String msg, Object... objects) {
        if (logger.isDebugEnabled())
            logger.debug("{}" + msg, "######", objects);
    }
}
