package com.chylee.fxiaoke.quartz.jobs.log;

import com.chylee.fxiaoke.common.exception.ErpApiException;
import com.chylee.fxiaoke.common.exception.ErpDataException;
import com.chylee.fxiaoke.common.jobs.log.AbstractJobLogExecutor;
import com.chylee.fxiaoke.common.service.FxkApprovalService;
import com.chylee.fxiaoke.common.util.DateUtils;
import com.chylee.fxiaoke.common.event.fxiaoke.crm.approval.Instance;
import com.chylee.fxiaoke.common.event.fxiaoke.crm.approval.QueryRespEvent;
import com.chylee.fxiaoke.common.event.fxiaoke.crm.approval.State;
import com.chylee.fxiaoke.common.model.JobDetail;
import com.chylee.fxiaoke.common.service.JobDetailService;
import com.chylee.fxiaoke.common.service.JobLogService;
import com.chylee.fxiaoke.common.exception.AccessTokenException;
import com.chylee.fxiaoke.common.exception.CrmDataException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  获取API
 */
@Component
public class FXKJobLogExecutor extends AbstractJobLogExecutor {
    private final int PAGE_SIZE = 50;

    private final FxkApprovalService approvalOpenApi;

    public FXKJobLogExecutor(FxkApprovalService approvalOpenApi, JobDetailService jobDetailService,
                               JobLogService jobLogService) {
        super(jobLogService, jobDetailService);
        this.approvalOpenApi = approvalOpenApi;
    }

    @Override
    protected List<JobDetail> listJobs(int typeId, int logId, String apiName, Date startTime, Date endTime)
            throws AccessTokenException, CrmDataException, ErpApiException, ErpDataException {
        int currentPage = 1;
        int totalPage = 1;

        List<Instance> instances = new ArrayList<>();

        if (logger.isDebugEnabled())
            logger.debug("{}的任务列表为空[{}-{}]", apiName, DateUtils.toLongString(startTime), DateUtils.toLongString(endTime));

        while(currentPage <= totalPage) {
            // 获取报价单数据
            QueryRespEvent respEvent = approvalOpenApi.query(apiName, State.pass, startTime.getTime(), endTime.getTime(), PAGE_SIZE, currentPage);

            if (!respEvent.isSuccess()) {
                throw new CrmDataException(respEvent.getErrorMessage());
            }

            //初始化分页信息
            if (respEvent.getQueryResult().getTotal() > PAGE_SIZE && totalPage == 1) {
                totalPage = (respEvent.getQueryResult().getTotal() + PAGE_SIZE - 1) / PAGE_SIZE;
            }

            List<Instance> temp = respEvent.getQueryResult().getInstanceList();
            if (temp != null && !temp.isEmpty())
                instances.addAll(temp);

            currentPage++;
        }

        if (logger.isDebugEnabled())
            logger.debug("任务列表长度：{}" + instances.size());

        return instances.stream().map(instance -> {
            JobDetail entity = new JobDetail();
            entity.setTypeId(typeId);
            entity.setLogId(logId);
            entity.setDataId(instance.getDataId());
            return entity;
        }).collect(Collectors.toList());
    }

    @Override
    public boolean isSupport(int typeId, String executor, String apiName) {
        return "fxkJobQueueExecutor".equals(executor);
    }
}
