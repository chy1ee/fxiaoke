package com.chylee.fxiaoke.common.service.impl;

import com.chylee.fxiaoke.common.api.ComplieModeSupported;
import com.chylee.fxiaoke.common.event.support.Page;
import com.chylee.fxiaoke.common.mapper.JobLogMapper;
import com.chylee.fxiaoke.common.model.JobLog;
import com.chylee.fxiaoke.common.service.JobLogService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JobLogServiceImpl extends ComplieModeSupported implements JobLogService {

    private final JobLogMapper logMapper;

    public JobLogServiceImpl(JobLogMapper logMapper) {
        this.logMapper = logMapper;
    }

    @Override
    public Page<JobLog> listAll(int qrtzId, int valid, int page, int pageSize) {
        Page page_ = new Page(page, pageSize);
        page_.setTotal(logMapper.count(qrtzId, valid));
        if (page_.getTotal() > 0)
            page_.setData(logMapper.listAllByPage(qrtzId, valid, (page-1)*page_.getPageSize(), page_.getPageSize()));
        return page_;
    }

    @Override
    public JobLog current(int qrtzId, int typeId, Date endTime) {
        JobLog current = new JobLog();
        Integer maxId = logMapper.maxId(typeId);
        if(maxId == null) {
            current.setStartTime(new Date(1572537600000L));
        }
        else {
            JobLog last = logMapper.selectById(maxId);

            //开发模式只取最后一个任务
            if (isDevMode())
                return last;

            current.setStartTime(last.getEndTime());
        }
        current.setQrtzId(qrtzId);
        current.setTypeId(typeId);
        current.setEndTime(endTime);
        current.setCount(0);
        logMapper.insert(current);

        return current;
    }

    @Override
    public void updateRowsById(int id, int rows) {
        JobLog ToUpdate = new JobLog();
        ToUpdate.setId(id);
        ToUpdate.setCount(rows);
        logMapper.updateById(ToUpdate);
    }

    @Override
    public void updateMessageById(int id, String message) {
        JobLog ToUpdate = new JobLog();
        ToUpdate.setId(id);
        ToUpdate.setMessage(message);
        logMapper.updateById(ToUpdate);
    }

    @Override
    public void updateResultById(int id, int success, int fail) {
        JobLog jobLog = new JobLog();
        jobLog.setId(id);
        jobLog.setSuccess(success);
        jobLog.setFail(fail);
        logMapper.updateById(jobLog);
    }
}
