package com.chylee.fxiaoke.xjl.jobs.log;

import com.chylee.fxiaoke.common.service.JobDetailService;
import com.chylee.fxiaoke.common.service.JobLogService;
import com.chylee.fxiaoke.xjl.event.ErpJobRespEvent;
import com.chylee.fxiaoke.xjl.service.ErpAssistantService;
import com.chylee.fxiaoke.xjl.service.ErpDingdanService;
import org.springframework.stereotype.Component;

@Component
public class HuikuanJobLogExeccutor extends AbstractXjlJobLogExecutor {
    private final ErpDingdanService huikuanService;

    protected HuikuanJobLogExeccutor(JobLogService jobLogService, JobDetailService jobDetailService,
                                     ErpDingdanService huikuanService, ErpAssistantService assistantService) {
        super(jobLogService, jobDetailService, assistantService);
        this.huikuanService = huikuanService;
    }

    @Override
    protected ErpJobRespEvent getErpJobResp(String startTime, String endTime) {
        return huikuanService.list(startTime, endTime);
    }

    @Override
    public boolean isSupport(int typeId, String executor, String apiName) {
        return "erpJobQueueExecutor".equals(executor) &&
                "/huikuan?startTime={startTime}&endTime={endTime}&page={page}".equals(apiName);
    }
}
