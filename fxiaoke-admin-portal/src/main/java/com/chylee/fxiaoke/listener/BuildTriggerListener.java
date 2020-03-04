package com.chylee.fxiaoke.listener;

import com.chylee.fxiaoke.quartz.QuartzTriggerManager;
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

        QuartzTriggerManager quartzTriggerManager = null;

        try {
            quartzTriggerManager = context.getBean(QuartzTriggerManager.class);
        } catch (NoSuchBeanDefinitionException e) {
            return;
        }

        try {

            if (quartzTriggerManager != null) {
                quartzTriggerManager.initTriggers();
            } else {
                logger.warn("quartzTriggerManager is not existed!");
                return;
            }
        } catch (Exception e) {
            logger.error("cannot initTriggers", e);
        }
    }
}
