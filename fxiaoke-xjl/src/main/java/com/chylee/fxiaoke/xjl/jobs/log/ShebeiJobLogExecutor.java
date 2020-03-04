package com.chylee.fxiaoke.xjl.jobs.log;

import com.chylee.fxiaoke.common.service.JobDetailService;
import com.chylee.fxiaoke.common.service.JobLogService;
import com.chylee.fxiaoke.xjl.event.ErpJobRespEvent;
import com.chylee.fxiaoke.xjl.service.ErpAssistantService;
import com.chylee.fxiaoke.xjl.service.ErpDingdanService;
import org.springframework.stereotype.Component;

@Component
public class ShebeiJobLogExecutor extends AbstractXjlJobLogExecutor {
    private final ErpDingdanService shebeiService;

    protected ShebeiJobLogExecutor(JobLogService jobLogService, JobDetailService jobDetailService,
                                   ErpDingdanService shebeiService, ErpAssistantService assistantService) {
        super(jobLogService, jobDetailService, assistantService);
        this.shebeiService = shebeiService;
    }

    @Override
    protected ErpJobRespEvent getErpJobResp(String startTime, String endTime) {
        return shebeiService.list(startTime, endTime);
    }

    @Override
    public boolean isSupport(int typeId, String executor, String apiName) {
        return "erpJobQueueExecutor".equals(executor) &&
                "/shebei?startTime={startTime}&endTime={endTime}&page={page}".equals(apiName);
    }
}
