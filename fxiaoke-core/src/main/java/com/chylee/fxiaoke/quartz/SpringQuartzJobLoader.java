package com.chylee.fxiaoke.quartz;

import com.chylee.fxiaoke.core.mapper.QrtzJobMapper;
import com.chylee.fxiaoke.core.model.QrtzJob;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringQuartzJobLoader implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    private Scheduler scheduler;
    private QrtzJobMapper qrtzJobMapper;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        this.scheduler = applicationContext.getBean(Scheduler.class);
        this.qrtzJobMapper = applicationContext.getBean(QrtzJobMapper.class);
    }

    public SpringQuartzJob loadJob(String name) {
        return applicationContext.getBean(name, SpringQuartzJob.class);
    }

    public void triggerJob(int jobId) throws SchedulerException {
        if (scheduler == null || qrtzJobMapper == null)
            return;

        QrtzJob qrtzJob = qrtzJobMapper.selectByPrimaryKey(jobId);
        if(qrtzJob != null)
            scheduler.triggerJob(QuartzUtils.jobKey(qrtzJob.getId(), qrtzJob.getJobGroup()));
    }
}
