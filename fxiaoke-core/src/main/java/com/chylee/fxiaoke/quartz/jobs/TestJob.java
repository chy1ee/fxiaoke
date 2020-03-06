package com.chylee.fxiaoke.quartz.jobs;

import com.chylee.fxiaoke.common.jobs.quartz.SpringQuartzJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TestJob implements SpringQuartzJob {
    private Logger logger = LoggerFactory.getLogger(TestJob.class);

    @Override
    public void invoke(int qrtzId, String params) {
        logger.debug("hello, boy!");
    }
}
