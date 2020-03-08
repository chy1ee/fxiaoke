package com.chylee.fxiaoke.quartz.jobs;

import com.chylee.fxiaoke.common.jobs.quartz.SpringQuartzJob;
import com.chylee.fxiaoke.common.model.JobDetail;
import com.chylee.fxiaoke.common.service.JobDetailService;
import com.chylee.fxiaoke.common.service.JobLogService;
import com.chylee.fxiaoke.quartz.jobs.support.Result;
import com.chylee.fxiaoke.quartz.jobs.support.ResultContext;

import java.util.Collection;
import java.util.List;

/**
 * 定时任务方式消费任务队列
 */
public class DefaultJobDetailHandler extends AbstractJobDetailHandler implements SpringQuartzJob {
    private final JobLogService jobLogService;
    private final JobDetailService jobDetailService;

    public DefaultJobDetailHandler(JobDetailService jobDetailService, JobLogService jobLogService) {
        super(jobDetailService);
        this.jobDetailService = jobDetailService;
        this.jobLogService = jobLogService;
    }

    @Override
    public void invoke(int qrtzId, String params) {
        ResultContext context = new ResultContext();
        while(true) {
            List<JobDetail> jobDetails = jobDetailService.listStatus0();
            if (jobDetails == null || jobDetails.isEmpty()) {
                if (logger.isDebugEnabled())
                    logger.debug("***任务队列为空，执行器将退出");
                break;
            }

            for (JobDetail jobDetail : jobDetails) {
                if (handleJobDetail(jobDetail))
                    context.addSuccess(jobDetail.getLogId());
                else
                    context.addFail(jobDetail.getLogId());
            }
        }

        Collection<Result> results = context.getContext();
        for (Result result : results)
            jobLogService.updateResultById(result.getLogId(), result.getSuccess(), result.getFail());
    }
}
