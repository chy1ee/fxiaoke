package com.chylee.fxiaoke.configuration;

import com.chylee.fxiaoke.common.jobs.JobDetailQueue;
import com.chylee.fxiaoke.common.service.JobDetailService;
import com.chylee.fxiaoke.quartz.jobs.DefaultJobDetailConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class JobDetailConfig {

    public void startConsumer() {

    }

    @Bean
    @Lazy
    public DefaultJobDetailConsumer jobDetailConsumer(JobDetailQueue jobDetailQueue, JobDetailService jobDetailService) {
        return new DefaultJobDetailConsumer(jobDetailQueue, jobDetailService);
    }
}
