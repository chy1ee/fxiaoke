package com.chylee.fxiaoke.quartz;

import com.chylee.fxiaoke.core.mapper.QrtzJobMapper;
import com.chylee.fxiaoke.core.mapper.QrtzTriggerMapper;
import com.chylee.fxiaoke.core.model.QrtzJob;
import com.chylee.fxiaoke.core.model.QrtzTrigger;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class QuartzTriggerManager {
    private Logger logger = LoggerFactory.getLogger(QuartzTriggerManager.class);

    private Scheduler scheduler;
    private QrtzJobMapper qrtzJobMapper;
    private QrtzTriggerMapper qrtzTriggerMapper;

    public QuartzTriggerManager(Scheduler scheduler,
                                QrtzJobMapper qrtzJobMapper, QrtzTriggerMapper qrtzTriggerMapper) {
        this.scheduler = scheduler;
        this.qrtzJobMapper = qrtzJobMapper;
        this.qrtzTriggerMapper = qrtzTriggerMapper;
    }

    public void initTriggers() {
        try {
            removeAllExistedJobs();
            buildTriggers();
            scheduler.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void removeAllExistedJobs() throws Exception {
        logger.info("begin to remove all the job keys in current database!");
        Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.anyJobGroup());
        List<JobKey> willBeDeletedJobKeys = new ArrayList<>();
        willBeDeletedJobKeys.addAll(jobKeys);
        scheduler.deleteJobs(willBeDeletedJobKeys);
        logger.info("success remove all the job keys {} in current database!", willBeDeletedJobKeys.size());
    }

    private void buildTriggers(){
        List<QrtzTrigger> qrtzTriggers = qrtzTriggerMapper.listAllByBindJob();
        if(!CollectionUtils.isEmpty(qrtzTriggers)){
            qrtzTriggers.stream().forEach(qrtzTrigger->{
                QrtzJob qrtzJob = qrtzJobMapper.selectByPrimaryKey(qrtzTrigger.getJobId());
                try {
                    if (!scheduler.checkExists(QuartzUtils.jobKey(qrtzJob.getId(), qrtzJob.getJobGroup())))
                        scheduler.addJob(QuartzUtils.buildJobDetail(qrtzJob), false);

                    Trigger trigger = QuartzUtils.buildTrigger(qrtzTrigger);
                    scheduler.scheduleJob(trigger);

                    if(qrtzTrigger.getStatus() == 0 || qrtzJob.getStatus() == 0)
                        scheduler.pauseTrigger(trigger.getKey());
                } catch (SchedulerException e) {
                    logger.error("初始化Quartz时发生错误", e);
                }
            });
        }
    }
}
