package com.chylee.fxiaoke.core.service.impl;

import com.chylee.fxiaoke.common.event.support.Page;
import com.chylee.fxiaoke.common.exception.ChyleeException;
import com.chylee.fxiaoke.common.util.StringUtils;
import com.chylee.fxiaoke.core.mapper.QrtzJobMapper;
import com.chylee.fxiaoke.core.model.QrtzJob;
import com.chylee.fxiaoke.core.service.QrtzJobService;
import com.chylee.fxiaoke.quartz.QuartzUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QrtzJobServiceImpl implements QrtzJobService {
    private Logger logger = LoggerFactory.getLogger(QrtzJobServiceImpl.class);

    @Autowired
    private QrtzJobMapper qrtzJobMapper;

    @Autowired
    private Scheduler scheduler;

    @Override
    public QrtzJob saveJob(QrtzJob qrtzJob) throws ChyleeException {
        if (qrtzJob.getId() == null || qrtzJob.getId() == 0) {
            qrtzJobMapper.insertSelective(qrtzJob);
            try {
                scheduler.addJob(QuartzUtils.buildJobDetail(qrtzJob), false);
            } catch (SchedulerException e) {
                logger.error("新建Job失败", e);
            }
        }

        return qrtzJob;
    }

    @Override
    public void deleteJob(int jobId) throws ChyleeException {
        QrtzJob qrtzJob = qrtzJobMapper.selectByPrimaryKey(jobId);
        if (qrtzJob == null)
            throw new ChyleeException("Job不存在");

        try {
            JobKey jobKey = QuartzUtils.jobKey(qrtzJob.getId(), qrtzJob.getJobGroup());
            List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
            for (Trigger trigger : triggers) {
                TriggerKey triggerKey = trigger.getKey();
                scheduler.pauseTrigger(triggerKey);
                scheduler.unscheduleJob(triggerKey);
            }
            scheduler.deleteJob(QuartzUtils.jobKey(qrtzJob.getId(), qrtzJob.getJobGroup()));
        }
        catch(SchedulerException e) {
            throw new ChyleeException("删除Job失败");
        }

        qrtzJobMapper.deleteByPrimaryKey(jobId);
    }

    @Override
    public List<QrtzJob> list(String name) {
        boolean isNumber = StringUtils.isNumber(name);
        Integer id = isNumber ? Integer.parseInt(name) : null;
        String nameToUse = isNumber ? null : name;
        return qrtzJobMapper.list(id, nameToUse);
    }

    @Override
    public boolean triggerJob(int jobId) {
        QrtzJob qrtzJob = qrtzJobMapper.selectByPrimaryKey(jobId);
        if(qrtzJob != null) {
            try {
                scheduler.triggerJob(QuartzUtils.jobKey(qrtzJob.getId(), qrtzJob.getJobGroup()));
                return true;
            } catch (SchedulerException e) {
                logger.error("执行JOB失败", e);
            }
        }
        return false;
    }

    @Override
    public Page listJobByPage(Page page) {
        page.setTotal(qrtzJobMapper.selectCount());
        if(page.getTotal() > 0)
            page.setData(qrtzJobMapper.listPage((page.getPage() - 1) * page.getPageSize(), page.getPageSize()));
        return page;
    }

    @Override
    public QrtzJob toggleStatus(int jobId, Byte status) throws SchedulerException {
        QrtzJob qrtzJob = qrtzJobMapper.selectByPrimaryKey(jobId);
        if(qrtzJob != null) {
            byte statusToUse = status == (byte)1 ? (byte)1 : 0;

            JobKey jobKey = QuartzUtils.jobKey(qrtzJob.getId(), qrtzJob.getJobGroup());
            if(statusToUse == 0){
                scheduler.pauseJob(jobKey);
            }else if(status.intValue() == 1){
                scheduler.resumeJob(jobKey);
            }

            QrtzJob qrtzJobToUpdate = new QrtzJob();
            qrtzJobToUpdate.setId(jobId);
            qrtzJobToUpdate.setStatus(statusToUse);
            qrtzJobMapper.updateByPrimaryKeySelective(qrtzJobToUpdate);
            qrtzJob.setStatus(status);
        }
        return qrtzJob;
    }
}
