package com.chylee.fxiaoke.common.jobs.log;

import com.chylee.fxiaoke.common.api.ComplieModeSupported;
import com.chylee.fxiaoke.common.exception.AccessTokenException;
import com.chylee.fxiaoke.common.exception.CrmDataException;
import com.chylee.fxiaoke.common.exception.ErpApiException;
import com.chylee.fxiaoke.common.exception.ErpDataException;
import com.chylee.fxiaoke.common.model.JobDetail;
import com.chylee.fxiaoke.common.model.JobLog;
import com.chylee.fxiaoke.common.service.JobDetailService;
import com.chylee.fxiaoke.common.service.JobLogService;
import com.chylee.fxiaoke.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class AbstractJobLogExecutor extends ComplieModeSupported implements JobLogExecutor {
    protected Logger logger = LoggerFactory.getLogger(AbstractJobLogExecutor.class);

    private final JobLogService jobLogService;
    private final JobDetailService jobDetailService;

    protected AbstractJobLogExecutor(JobLogService jobLogService, JobDetailService jobDetailService) {
        this.jobLogService = jobLogService;
        this.jobDetailService = jobDetailService;
    }

    @Override
    public void execute(int qrtzId, int typeId, String apiName)
            throws AccessTokenException, CrmDataException, ErpApiException, ErpDataException {
        JobLog jobLog = jobLogService.current(qrtzId, typeId, now(qrtzId, typeId, apiName));

        int logId = jobLog.getId();
        List<JobDetail> jobDetails = this.listJobs(logId, apiName,
                jobLog.getStartTime(), jobLog.getEndTime());
        if (jobDetails == null) {
            if (logger.isDebugEnabled())
                logger.debug("{}的任务列表为空[{}-{}]", apiName, jobLog.getStartTime(), jobLog.getEndTime());
            return;
        }

        int jobsCount = jobDetails.size();
        if (jobsCount > 0) {
            jobDetailService.insertBatch(jobDetails);
            jobLogService.updateRowsById(logId, jobDetails.size());
        }
    }

    //当前时间
    protected Date now(int qrtzId, int typeId, String apiName) {
        Calendar now = Calendar.getInstance();

        // 开发模式时间推前24小时
        if (isDevMode())
            now.add(Calendar.DAY_OF_MONTH, -1);

        return now.getTime();
    }

    protected abstract List<JobDetail> listJobs(Integer logId, String apiName, Date startTime, Date endTime)
            throws AccessTokenException, CrmDataException, ErpApiException, ErpDataException;
}
