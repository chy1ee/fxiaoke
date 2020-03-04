package com.chylee.fxiaoke.controller.job;

import com.chylee.fxiaoke.common.event.DataRespEvent;
import com.chylee.fxiaoke.common.event.ResponseEvent;
import com.chylee.fxiaoke.common.model.JobDetail;
import com.chylee.fxiaoke.common.model.JobLog;
import com.chylee.fxiaoke.common.service.JobDetailService;
import com.chylee.fxiaoke.common.service.JobLogService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("job/log")
public class JobLogController {

    private final JobLogService jobLogService;

    private final JobDetailService jobDetailService;

    public JobLogController(JobLogService jobLogService, JobDetailService jobDetailService) {
        this.jobLogService = jobLogService;
        this.jobDetailService = jobDetailService;
    }

    @GetMapping
    public DataRespEvent<JobLog> list(Integer qrtzId, Integer valid, Integer page) {
        int qrtzIdToUse = qrtzId == null ? 0 : qrtzId;
        int validToUse = valid == null ? 0 : valid;
        int pageToUse = page == null ? 1 : page;
        return new DataRespEvent<>(jobLogService.listAll(qrtzIdToUse, validToUse, pageToUse, 10));
    }

    @GetMapping("detail/{logId}")
    public DataRespEvent<JobDetail> detail(@PathVariable int logId, Integer page) {
        int pageToUse = page == null ? 1 : page;
        return new DataRespEvent<>(jobDetailService.listByLogId(logId, pageToUse, 10));
    }

    @PostMapping("reset/{id}")
    public ResponseEvent error(@PathVariable int id) {
        try {
            this.jobDetailService.reset(id);
        }
        catch(Exception e) {
            new ResponseEvent(e.getMessage());
        }

        return new ResponseEvent();
    }
}
