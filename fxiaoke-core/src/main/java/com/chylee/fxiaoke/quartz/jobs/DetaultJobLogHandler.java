package com.chylee.fxiaoke.quartz.jobs;

import com.chylee.fxiaoke.core.model.JobType;
import com.chylee.fxiaoke.core.service.JobTypeService;
import com.chylee.fxiaoke.common.service.SysReportService;
import com.chylee.fxiaoke.quartz.SpringQuartzJob;
import com.chylee.fxiaoke.common.jobs.log.JobLogExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * 生成任务队列
 */
@Component("jobLog")
public class DetaultJobLogHandler implements SpringQuartzJob, ApplicationContextAware {
    private Logger logger = LoggerFactory.getLogger(DetaultJobLogHandler.class);

    private Collection<JobLogExecutor> executors = null;

    private JobTypeService JobTypeService;

    public DetaultJobLogHandler(JobTypeService jobTypeService) {
        JobTypeService = jobTypeService;
    }

    @Override
    public void invoke(int qrtzId, String param) throws Exception {
        List<JobType> jobTypeEntities = JobTypeService.listJobTypes();
        for (JobType jobType : jobTypeEntities) {
            if (jobType.getStatus() != 1)
                continue;

            for (JobLogExecutor executor : executors) {
                if (executor.isSupport(jobType.getId(), jobType.getExecutor(), jobType.getApiName())) {
                    if (logger.isDebugEnabled())
                        logger.debug("开始执行任务[{}][{}][{}]", jobType.getId(), jobType.getExecutor(), jobType.getApiName() );
                    executor.execute(qrtzId, jobType.getId(), jobType.getApiName());
                }
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        executors = applicationContext.getBeansOfType(JobLogExecutor.class).values();

        if (logger.isDebugEnabled()) {
            logger.debug("scaning executor:");
            int i = 1;
            for (JobLogExecutor executor : executors)
                logger.debug("***{}.{} ", i++,  executor.getClass().getName());
        }
    }

}
