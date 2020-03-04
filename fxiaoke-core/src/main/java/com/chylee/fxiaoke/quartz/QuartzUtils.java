package com.chylee.fxiaoke.quartz;

import com.chylee.fxiaoke.core.model.QrtzJob;
import com.chylee.fxiaoke.core.model.QrtzTrigger;
import org.quartz.*;
import org.springframework.util.StringUtils;

public class QuartzUtils {

    public static String getGroup(String group) {
        return StringUtils.hasText(group) ? group : "DEFAULT";
    }

    public static JobKey jobKey(int jobId, String jobGroup) {
        return JobKey.jobKey("Job_" + jobId, getGroup(jobGroup));
    }

    public static TriggerKey triggerKey(int triggerId, String triggerGroup) {
        return TriggerKey.triggerKey("Trigger_" + triggerId, getGroup(triggerGroup));
    }

    public static Trigger buildTrigger(QrtzTrigger trigger){
        return TriggerBuilder.newTrigger()
                .withIdentity(triggerKey(trigger.getId(), trigger.getTriggerGroup()))
                .withSchedule(buildSchedulerBuilder(trigger))
                .forJob(jobKey(trigger.getJobId(), trigger.getTriggerGroup()))
                .build();
    }

    public static ScheduleBuilder<?> buildSchedulerBuilder(QrtzTrigger trigger) {
        if (!StringUtils.isEmpty(trigger.getCronExpression())) {
            return CronScheduleBuilder.cronSchedule(trigger.getCronExpression());
        } else {
            return SimpleScheduleBuilder.repeatSecondlyForever(trigger.getTimeInterval().intValue());
        }
    }

    public static JobDetail buildJobDetail(QrtzJob qrtzJob){
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("SPRING_JOB_BEAN_INFO", qrtzJob);
        return JobBuilder.newJob(SpringQuartzJobBean.class)
                .storeDurably(true).withIdentity(jobKey(qrtzJob.getId(), qrtzJob.getJobGroup()))
                .withDescription(qrtzJob.getJobDesc()).setJobData(jobDataMap).build();
    }
}
