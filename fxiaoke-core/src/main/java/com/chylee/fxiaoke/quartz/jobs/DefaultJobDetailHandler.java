package com.chylee.fxiaoke.quartz.jobs;

import com.chylee.fxiaoke.common.service.JobLogService;
import com.chylee.fxiaoke.common.model.JobDetail;
import com.chylee.fxiaoke.common.util.StringUtils;
import com.chylee.fxiaoke.quartz.SpringQuartzJob;
import com.chylee.fxiaoke.common.jobs.detail.JobDetailExecutor;
import com.chylee.fxiaoke.common.service.JobDetailService;
import com.chylee.fxiaoke.common.service.SysReportService;
import com.chylee.fxiaoke.quartz.jobs.support.Result;
import com.chylee.fxiaoke.quartz.jobs.support.ResultContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * 消费任务队列
 */
@Component("jobDetail")
public class DefaultJobDetailHandler implements SpringQuartzJob, ApplicationContextAware {
    private Logger logger = LoggerFactory.getLogger(DefaultJobDetailHandler.class);

    private Collection<JobDetailExecutor> executors = null;

    private JobLogService jobLogService;
    private JobDetailService jobDetailService;
    private SysReportService reportService;

    public DefaultJobDetailHandler(JobDetailService jobDetailService, SysReportService reportService,
                                   JobLogService jobLogService) {
        this.jobDetailService = jobDetailService;
        this.reportService = reportService;
        this.jobLogService = jobLogService;
    }

    @Override
    public void invoke(int qrtzId, String params) {
        boolean isSuccess = true;
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
                else {
                    if (isSuccess)
                        isSuccess = false;

                    context.addFail(jobDetail.getLogId());
                }
            }
        }

        Collection<Result> results = context.getContext();
        for (Result result : results)
            jobLogService.updateResultById(result.getLogId(), result.getSuccess(), result.getFail());

        if(!isSuccess)
            reportService.send("数据对接系统对接数据时发生错误，请注意查看日志");
    }


    private boolean handleJobDetail(JobDetail jobDetail)  {
        boolean result = false;
        for (JobDetailExecutor executor : executors) {
            if (executor.isSupported(jobDetail.getTypeId())) {
                if (!result)
                    result = true;

                if (!executor.execute(jobDetail))
                    return false;
            }
        }

        if (!result) {
            jobDetailService.upateStatusById(jobDetail.getId(), -1,
                    StringUtils.gbkLeft("没有符合条件的执行器", 200));
        }

        return result;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        executors = applicationContext.getBeansOfType(JobDetailExecutor.class).values();
    }
}
