package com.chylee.fxiaoke.core.service.impl;

import com.chylee.fxiaoke.common.event.support.Page;
import com.chylee.fxiaoke.common.exception.ChyleeException;
import com.chylee.fxiaoke.core.mapper.QrtzJobMapper;
import com.chylee.fxiaoke.core.mapper.QrtzTriggerMapper;
import com.chylee.fxiaoke.core.model.QrtzJob;
import com.chylee.fxiaoke.core.model.QrtzTrigger;
import com.chylee.fxiaoke.core.service.QrtzTriggerService;
import com.chylee.fxiaoke.quartz.QuartzUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class QrtzTriggerServiceImpl implements QrtzTriggerService {
    private Logger logger = LoggerFactory.getLogger(QrtzTriggerServiceImpl.class);

    @Autowired
    private QrtzTriggerMapper qrtzTriggerMapper;

    @Autowired
    private QrtzJobMapper qrtzJobMapper;

    @Autowired
    private Scheduler scheduler;

    @Override
    public QrtzTrigger saveTrigger(QrtzTrigger qrtzTrigger) throws ChyleeException, SchedulerException {
        if (qrtzTrigger.getId() == null || qrtzTrigger.getId() == 0) {
            qrtzTriggerMapper.insertSelective(qrtzTrigger);

            JobDetail jobDetail = scheduler.getJobDetail(QuartzUtils.jobKey(qrtzTrigger.getJobId(), qrtzTrigger.getTriggerGroup()));
            if (jobDetail == null) {
                QrtzJob qrtzJob =  qrtzJobMapper.selectByPrimaryKey(qrtzTrigger.getJobId());
                scheduler.addJob(QuartzUtils.buildJobDetail(qrtzJob), false);
            }
            Trigger trigger = QuartzUtils.buildTrigger(qrtzTrigger);
            scheduler.scheduleJob(trigger);
        }
        else {
            qrtzTrigger.setUpdateTime(new Date());
            if (qrtzTriggerMapper.updateByPrimaryKey(qrtzTrigger) != 1)
                throw new ChyleeException("Trigger不存在");

            Trigger trigger = QuartzUtils.buildTrigger(qrtzTrigger);
            scheduler.rescheduleJob(trigger.getKey(), trigger);
        }

        if(qrtzTrigger.getStatus() == 0)
            scheduler.pauseTrigger(QuartzUtils.triggerKey(qrtzTrigger.getId(), qrtzTrigger.getTriggerGroup()));

        return qrtzTrigger;
    }

    @Override
    public void deleteTrigger(int triggerId) throws ChyleeException {
        QrtzTrigger qrtzTrigger = qrtzTriggerMapper.selectByPrimaryKey(triggerId);
        if (qrtzTrigger == null)
            throw new ChyleeException("Trigger不存在");

        Trigger trigger = QuartzUtils.buildTrigger(qrtzTrigger);
        try {
            scheduler.unscheduleJob(trigger.getKey());
        } catch (SchedulerException e) {
            logger.error("删除Trigger失败", e);
        }

        qrtzTriggerMapper.deleteByPrimaryKey(triggerId);
    }

    @Override
    public Page listTriggerByPage(Page page) {
        page.setTotal(qrtzTriggerMapper.selectCount());
        if(page.getTotal() > 0)
            page.setData(qrtzTriggerMapper.listPage((page.getPage() - 1) * page.getPageSize(), page.getPageSize()));
        return page;
    }

    @Override
    public QrtzTrigger toggleStatus(int triggerId, Byte status) throws SchedulerException {
        QrtzTrigger qrtzTrigger = qrtzTriggerMapper.selectByPrimaryKey(triggerId);
        if(qrtzTrigger != null) {
            byte statusToUse = status == (byte)1 ? (byte)1 : 0;

            TriggerKey triggerKey = QuartzUtils.triggerKey(qrtzTrigger.getId(), qrtzTrigger.getTriggerGroup());
            if(statusToUse == 0){
                scheduler.pauseTrigger(triggerKey);
            }else if(status.intValue() == 1){
                scheduler.resumeTrigger(triggerKey);
            }

            QrtzTrigger qrtzTriggerToUpdate = new QrtzTrigger();
            qrtzTriggerToUpdate.setId(triggerId);
            qrtzTriggerToUpdate.setStatus(statusToUse);
            qrtzTriggerMapper.updateByPrimaryKeySelective(qrtzTriggerToUpdate);
            qrtzTrigger.setStatus(status);
        }
        return qrtzTrigger;
    }
}
