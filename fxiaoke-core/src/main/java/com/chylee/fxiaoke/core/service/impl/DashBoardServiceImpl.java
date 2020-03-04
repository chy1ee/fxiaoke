package com.chylee.fxiaoke.core.service.impl;

import com.chylee.fxiaoke.common.mapper.JobDetailMapper;
import com.chylee.fxiaoke.common.mapper.JobLogMapper;
import com.chylee.fxiaoke.core.event.sys.DashBoardRespEvent;
import com.chylee.fxiaoke.core.mapper.*;
import com.chylee.fxiaoke.core.service.DashBoardService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
public class DashBoardServiceImpl implements DashBoardService, InitializingBean {

    private final int TIME_OUT = 1000 * 60 * 10;

    private final JobTypeMapper jobTypeMapper;
    private final QrtzJobMapper qrtzJobMapper;
    private final QrtzLogMapper qrtzLogMapper;
    private final JobDetailMapper jobDetailMapper;
    private final JobLogMapper jobLogMapper;

    private Object lockObj = new Object();

    private int jobTypeCount = 0;
    private int executorCount = 0;
    private int qrtzLogCount = 0;
    private int errorCount = 0;

    private long lastTime = 0;

    public DashBoardServiceImpl(JobTypeMapper jobTypeMapper, QrtzJobMapper qrtzJobMapper,
                                QrtzLogMapper qrtzLogMapper, JobDetailMapper jobDetailMapper,
                                JobLogMapper jobLogMapper) {
        this.jobTypeMapper = jobTypeMapper;
        this.qrtzJobMapper = qrtzJobMapper;
        this.qrtzLogMapper = qrtzLogMapper;
        this.jobDetailMapper = jobDetailMapper;
        this.jobLogMapper = jobLogMapper;
    }

    @Override
    public DashBoardRespEvent info() {
        long now = System.currentTimeMillis();
        if (now - lastTime > TIME_OUT) {
            synchronized (this.lockObj) {
                if (now - lastTime > TIME_OUT)
                    this.afterPropertiesSet();
            }
        }

        DashBoardRespEvent respEvent = new DashBoardRespEvent();
        respEvent.setJobTypeCount(this.jobTypeCount);
        respEvent.setExecutorCount(this.executorCount);
        respEvent.setQrtzLogCount(this.qrtzLogCount);
        respEvent.setErrorCount(this.errorCount);
        respEvent.setList(this.jobLogMapper.lineChart());

        return respEvent;
    }

    @Override
    public void afterPropertiesSet() {
        this.jobTypeCount = this.jobTypeMapper.count();
        this.executorCount = this.qrtzJobMapper.selectCount();
        this.qrtzLogCount = this.qrtzLogMapper.count();
        this.errorCount = this.jobDetailMapper.errorCount();

        this.lastTime = System.currentTimeMillis();
    }
}
