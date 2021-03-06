package com.chylee.fxiaoke.common.service.impl;

import com.chylee.fxiaoke.common.event.support.Page;
import com.chylee.fxiaoke.common.exception.ServiceException;
import com.chylee.fxiaoke.common.jobs.JobDetailQueue;
import com.chylee.fxiaoke.common.mapper.JobDetailMapper;
import com.chylee.fxiaoke.common.mapper.JobLogMapper;
import com.chylee.fxiaoke.common.model.JobDetail;
import com.chylee.fxiaoke.common.service.JobDetailService;
import com.chylee.fxiaoke.common.service.SysReportService;
import com.chylee.fxiaoke.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class JobDetailServiceImpl implements JobDetailService {
    private Logger logger = LoggerFactory.getLogger(JobDetailServiceImpl.class);

    private final JobDetailMapper jobDetailMapper;
    private final JobLogMapper jobLogMapper;
    private final JobDetailQueue jobDetailQueue;
    private final SysReportService reportService;

    public JobDetailServiceImpl(JobDetailMapper jobDetailMapper, JobLogMapper jobLogMapper,
                                JobDetailQueue jobDetailQueue, SysReportService reportService) {
        this.jobDetailMapper = jobDetailMapper;
        this.jobLogMapper = jobLogMapper;
        this.jobDetailQueue = jobDetailQueue;
        this.reportService = reportService;
    }

    @Override
    public void initJobDetailCachar() {
        List<JobDetail> jobDetails = jobDetailMapper.listByStatus0(0);
        if (jobDetails == null || jobDetails.isEmpty())
            return;

        insertBatch(jobDetails, false);
    }

    @Override
    public void reset(int id) throws ServiceException {
        JobDetail jobDetail = jobDetailMapper.selectById(id);
        if(jobDetail == null || jobDetail.getStatus() != -1)
            throw new ServiceException("操作失败");

        JobDetail ToUpdate = new JobDetail();
        ToUpdate.setId(id);
        ToUpdate.setStatus(0);
        try {
            jobDetailMapper.updateStatusById(ToUpdate);
            jobDetail.setStatus(0);
            jobDetailQueue.put(jobDetail);
            jobLogMapper.updateFail(jobDetail.getLogId());
        } catch (Exception e) {
            logger.error("失败任务重做失败，任务号[{}]", id, e);
        }
    }

    @Override
    public void insertBatch(List<JobDetail> jobDetails) {
        insertBatch(jobDetails, true);
    }

    public void insertBatch(List<JobDetail> jobDetails, boolean inserToDb) {
        if (jobDetails == null || jobDetails.isEmpty())
            return;

        for (JobDetail jobDetail : jobDetails) {
            try {
                if (inserToDb)
                    jobDetailMapper.insert(jobDetail);
                jobDetailQueue.put(jobDetail);
            } catch (Exception e) {
                logger.error("数据入任务队列时发生错误\n\t{}", jobDetail, e);
                reportService.sendToAdmin("数据入任务队列时发生错误，请及时查看日志并处理，以免数据丢失");
            }
        }
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

    /**
     * 做Quartz定时任务兼容性处理
     * @return 待处理任务列表
     */
    @Override
    public List<JobDetail> listStatus0() {
        List<JobDetail> jobDetails  = new ArrayList<>(PAGE_SIZE);

        for (int i=0;i<PAGE_SIZE;i++) {
            JobDetail jobDetail = null;
            try {
                jobDetail = jobDetailQueue.poll(2, TimeUnit.SECONDS);
                if (jobDetail == null)
                    break;
            } catch (InterruptedException e) {
                logger.error("批量获取任务失败", e);
                break;
            }

            jobDetails.add(jobDetail);
        }

        return jobDetails;
    }

    @Override
    public Page listByLogId(int logId, int page, int pageSize) {
        Page<JobDetail> page_ = new Page<>(page, pageSize);
        page_.setTotal(jobDetailMapper.count(logId, 1));
        if (page_.getTotal() > 0)
            page_.setData(jobDetailMapper.listByLogId(logId, page, (page-1)*pageSize, pageSize));
        return page_;
    }
}
