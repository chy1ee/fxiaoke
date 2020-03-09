package com.chylee.fxiaoke.quartz.jobs;

import com.chylee.fxiaoke.common.jobs.detail.JobDetailExecutor;
import com.chylee.fxiaoke.common.jobs.log.JobLogExecutor;
import com.chylee.fxiaoke.common.model.JobDetail;
import com.chylee.fxiaoke.common.service.JobDetailService;
import com.chylee.fxiaoke.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Collection;

public class AbstractJobDetailHandler implements ApplicationContextAware  {
    protected Logger logger = LoggerFactory.getLogger(DefaultJobDetailHandler.class);

    private Collection<JobDetailExecutor> executors = null;

    private final JobDetailService jobDetailService;

    public AbstractJobDetailHandler(JobDetailService jobDetailService) {
        this.jobDetailService = jobDetailService;
    }

    protected boolean handleJobDetail(JobDetail jobDetail)  {
        if (logger.isDebugEnabled())
            logger.debug("正在处理任务：" + jobDetail);

        if (jobDetail == null) {
            return false;
        }

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

        if (logger.isDebugEnabled()) {
            logger.debug("scaning executor:");
            int i = 1;
            for (JobDetailExecutor executor : executors)
                logger.debug("***{}.{} ", i++,  executor.getClass().getName());
        }
    }
}
