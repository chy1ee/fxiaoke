package com.chylee.fxiaoke.quartz.jobs;

import com.chylee.fxiaoke.common.jobs.JobDetailQueue;
import com.chylee.fxiaoke.common.jobs.quartz.SpringQuartzJob;
import com.chylee.fxiaoke.common.service.JobDetailService;

/**
 * 定时任务方式消费任务队列
 */
public class DefaultJobDetailConsumer extends AbstractJobDetailHandler implements SpringQuartzJob  {
    private final JobDetailQueue jobDetailQueue;

    public DefaultJobDetailConsumer(JobDetailQueue jobDetailQueue, JobDetailService jobDetailService) {
        super(jobDetailService);
        this.jobDetailQueue = jobDetailQueue;
    }

    @Override
    public void invoke(int qrtzId, String params) throws Exception {
        while (true) {
            handleJobDetail(jobDetailQueue.poll());
        }
    }
}
