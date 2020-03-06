package com.chylee.fxiaoke.xjl.jobs.log;

import com.chylee.fxiaoke.common.exception.ErpDataException;
import com.chylee.fxiaoke.common.jobs.log.AbstractJobLogExecutor;
import com.chylee.fxiaoke.common.model.JobDetail;
import com.chylee.fxiaoke.common.service.JobDetailService;
import com.chylee.fxiaoke.common.service.JobLogService;
import com.chylee.fxiaoke.common.util.DateUtils;
import com.chylee.fxiaoke.xjl.event.DbnowRespEvent;
import com.chylee.fxiaoke.xjl.event.ErpJobRespEvent;
import com.chylee.fxiaoke.xjl.service.ErpAssistantService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractXjlJobLogExecutor extends AbstractJobLogExecutor {
    private final ErpAssistantService assistantService;

    protected AbstractXjlJobLogExecutor(JobLogService jobLogService, JobDetailService jobDetailService,
                                        ErpAssistantService assistantService) {
        super(jobLogService, jobDetailService);
        this.assistantService = assistantService;
    }

    @Override
    protected Date now(int qrtzId, int typeId, String apiName) {
        DbnowRespEvent respEvent = assistantService.dbnow();
        if (respEvent.isSuccess()) {
            Date now = respEvent.getNow();
            return now;
        }

        logger.error("获取ERP服务器时间失败 - {}", respEvent.getMessage());
        return super.now(qrtzId, typeId, apiName);
    }

    @Override
    protected List<JobDetail> listJobs(Integer logId, String apiName, Date startTime, Date endTime)
            throws ErpDataException {
        ErpJobRespEvent respEvent = getErpJobResp(DateUtils.toLongString(startTime), DateUtils.toLongString(endTime));
        if (respEvent == null)
            throw new ErpDataException("调用ERP接口失败");

        if (!respEvent.isSuccess())
            throw new ErpDataException(respEvent.getMessage());

        if(respEvent.getDataIdList() != null && !respEvent.getDataIdList().isEmpty()) {
            List<String> result = respEvent.getDataIdList();
            return result.stream().map(dataId -> {
                JobDetail entity = new JobDetail();
                entity.setLogId(logId);
                entity.setDataId(dataId);
                return entity;
            }).collect(Collectors.toList());
        }

        return null;
    }

    protected abstract ErpJobRespEvent getErpJobResp(String startTime, String endTime);
}
