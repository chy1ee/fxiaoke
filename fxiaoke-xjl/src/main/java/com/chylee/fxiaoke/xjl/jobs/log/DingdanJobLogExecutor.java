package com.chylee.fxiaoke.xjl.jobs.log;

import com.chylee.fxiaoke.common.service.JobDetailService;
import com.chylee.fxiaoke.common.service.JobLogService;
import com.chylee.fxiaoke.xjl.event.ErpJobRespEvent;
import com.chylee.fxiaoke.xjl.service.ErpAssistantService;
import com.chylee.fxiaoke.xjl.service.ErpDingdanService;
import org.springframework.stereotype.Component;

@Component
public class DingdanJobLogExecutor extends AbstractXjlJobLogExecutor {
    private final ErpDingdanService dingdanService;

    protected DingdanJobLogExecutor(JobLogService jobLogService, JobDetailService jobDetailService,
                                    ErpDingdanService dingdanService, ErpAssistantService assistantService) {
        super(jobLogService, jobDetailService, assistantService);
        this.dingdanService = dingdanService;
    }

    @Override
    protected ErpJobRespEvent getErpJobResp(String startTime, String endTime) {
        return dingdanService.list(startTime, endTime);
    }

    @Override
    public boolean isSupport(int typeId, String executor, String apiName) {
        return "erpJobQueueExecutor".equals(executor) &&
                "/dingdan?startTime={startTime}&endTime={endTime}&page={page}".equals(apiName);
    }
}
