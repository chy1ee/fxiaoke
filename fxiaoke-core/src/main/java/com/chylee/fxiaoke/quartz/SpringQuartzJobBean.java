package com.chylee.fxiaoke.quartz;

import com.chylee.fxiaoke.common.jobs.quartz.SpringQuartzJob;
import com.chylee.fxiaoke.common.service.SysReportService;
import com.chylee.fxiaoke.core.model.QrtzJob;
import com.chylee.fxiaoke.core.model.QrtzLog;
import com.chylee.fxiaoke.core.service.QrtzLogService;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.StringUtils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class SpringQuartzJobBean extends QuartzJobBean {

    private final SpringQuartzJobLoader springQuartzJobLoader;
    private final SysReportService reportService;
    private final QrtzLogService logService;

    public SpringQuartzJobBean(SpringQuartzJobLoader springQuartzJobLoader, SysReportService reportService,
                               QrtzLogService logService) {
        this.springQuartzJobLoader = springQuartzJobLoader;
        this.reportService = reportService;
        this.logService = logService;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        QrtzJob qrtzJob = (QrtzJob) jobDataMap.get("SPRING_JOB_BEAN_INFO");
        SpringQuartzJob springQuartzJob = springQuartzJobLoader.loadJob(qrtzJob.getBean());
        if(springQuartzJob != null) {
            QrtzLog qrtzLog = new QrtzLog();
            qrtzLog.setJobId(qrtzJob.getId());
            qrtzLog.setJobGroup(qrtzJob.getJobGroup());
            qrtzLog.setJobName(qrtzJob.getJobName());
            logService.insertLog(qrtzLog);

            try {
                springQuartzJob.invoke(qrtzLog.getId(), qrtzJob.getParams());
            } catch (Exception e) {
                writeLog(qrtzLog.getId(), e);
                return;
            }

            String childIds = qrtzJob.getChildIds();
            if (!StringUtils.hasText(childIds)) {
                writeLog(qrtzLog.getId(), null);
                return;
            }

            String[] ids = childIds.split(",");
            for (String id : ids) {
                try {
                    springQuartzJobLoader.triggerJob(Integer.parseInt(id));
                } catch (SchedulerException e) {
                    writeLog(qrtzLog.getId(), e);
                    throw new JobExecutionException(e);
                }
            }

            writeLog(qrtzLog.getId(), null);
        }
    }

    private void writeLog(int qrtzId, Throwable e) {
        String error = null;
        if (e != null) {
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            error = stringWriter.toString();
            reportService.sendToAdmin("计划任务调度出错，请查看调度日志");
        }
        logService.updateEndtimeById(qrtzId, error);
    }

}
