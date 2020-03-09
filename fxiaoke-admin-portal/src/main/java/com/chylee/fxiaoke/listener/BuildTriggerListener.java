package com.chylee.fxiaoke.listener;

import com.chylee.fxiaoke.common.service.JobDetailService;
import com.chylee.fxiaoke.quartz.QuartzTriggerManager;
import com.chylee.fxiaoke.quartz.jobs.DefaultJobDetailConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;

public class BuildTriggerListener implements SpringApplicationRunListener {
    private Logger logger = LoggerFactory.getLogger(BuildTriggerListener.class);

    public BuildTriggerListener(SpringApplication sa, String[] args) {
        super();
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        logger.info("initializing jobs");

        try {
            context.getBean(QuartzTriggerManager.class).initTriggers();
        } catch (NoSuchBeanDefinitionException e) {
            return;
        } catch (Exception e) {
            logger.error("cannot initTriggers", e);
        }

        context.getBean(JobDetailService.class).initJobDetailCachar();

        logger.debug("######启动消费线程处理JobDetail######");
        context.getBean(DefaultJobDetailConsumer.class).invoke(0, null);
    }
}
