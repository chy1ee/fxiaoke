package com.chylee.fxiaoke.common.service.impl;

import com.chylee.fxiaoke.common.api.ComplieModeSupported;
import com.chylee.fxiaoke.common.event.support.Page;
import com.chylee.fxiaoke.common.exception.ServiceException;
import com.chylee.fxiaoke.common.mapper.JobDetailMapper;
import com.chylee.fxiaoke.common.mapper.JobLogMapper;
import com.chylee.fxiaoke.common.model.JobDetail;
import com.chylee.fxiaoke.common.service.JobDetailService;
import com.chylee.fxiaoke.common.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobDetailServiceImpl implements JobDetailService {

    private JobDetailMapper jobDetailMapper;
    private JobLogMapper jobLogMapper;

    public JobDetailServiceImpl(JobDetailMapper jobDetailMapper, JobLogMapper jobLogMapper) {
        this.jobDetailMapper = jobDetailMapper;
        this.jobLogMapper = jobLogMapper;
    }

    @Override
    public void reset(int id) throws ServiceException {
        JobDetail jobDetail = jobDetailMapper.selectById(id);
        if(jobDetail == null || jobDetail.getStatus() != -1)
            throw new ServiceException("操作失败");

        JobDetail ToUpdate = new JobDetail();
        ToUpdate.setId(id);
        ToUpdate.setStatus(0);
        jobDetailMapper.updateStatusById(ToUpdate);

        jobLogMapper.updateFail(jobDetail.getLogId());
    }

    @Override
    public void insertBatch(List<JobDetail> jobDetailList) {
        jobDetailMapper.insertBatch(jobDetailList);
    }

    @Override
    public void upateStatusById(int id, int status, String error) {
        JobDetail toUpdate = new JobDetail();
        toUpdate.setId(id);
        toUpdate.setStatus(status);
        if(error != null)
            toUpdate.setError(StringUtils.left(error, 255));
        jobDetailMapper.updateStatusById(toUpdate);
    }

    @Override
    public List<JobDetail> listStatus0() {
        return jobDetailMapper.listByStatus0(PAGE_SIZE);
    }

    @Override
    public Page listByLogId(int logId, int page, int pageSize) {
        Page page_ = new Page(page, pageSize);
        page_.setTotal(jobDetailMapper.count(logId, 1));
        if (page_.getTotal() > 0)
            page_.setData(jobDetailMapper.listByLogId(logId, page, (page-1)*pageSize, pageSize));
        return page_;
    }
}
