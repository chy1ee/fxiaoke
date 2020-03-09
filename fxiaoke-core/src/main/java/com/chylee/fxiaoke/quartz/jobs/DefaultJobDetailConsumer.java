package com.chylee.fxiaoke.quartz.jobs;

import com.chylee.fxiaoke.common.jobs.JobDetailQueue;
import com.chylee.fxiaoke.common.jobs.quartz.SpringQuartzJob;
import com.chylee.fxiaoke.common.model.JobDetail;
import com.chylee.fxiaoke.common.service.JobDetailService;

import java.util.concurrent.TimeUnit;

/**
 * 定时任务方式消费任务队列
 */
public class DefaultJobDetailConsumer extends AbstractJobDetailHandler implements SpringQuartzJob {
    private final JobDetailQueue jobDetailQueue;

    public DefaultJobDetailConsumer(JobDetailQueue jobDetailQueue, JobDetailService jobDetailService) {
        super(jobDetailService);
        this.jobDetailQueue = jobDetailQueue;
    }

    @Override
    public void invoke(int qrtzId, String params) {
        new Thread(new JobDetailRunner()).start();
    }

    private class JobDetailRunner implements Runnable {
        private long last;
        public JobDetailRunner() {
            this.last = System.currentTimeMillis();
        }

        @Override
        @SuppressWarnings("InfiniteLoopStatement")
        public void run() {
            while (true) {
                try {
                    if (logger.isDebugEnabled())
                        logger.debug("###### 正在监听任务队列 ######");
                    JobDetail jobDetail = jobDetailQueue.poll(5, TimeUnit.MINUTES);
                    if (jobDetail != null) {
                        handleJobDetail(jobDetail);
                        long now = System.currentTimeMillis();
                        if (now - last > 1000 * 60 * 30) {
                            System.gc();
                            last = now;
                        }
                    }
                } catch (InterruptedException e) {
                    logger.error("获取队列任务失败", e);
                }
            }
        }
    }
}
