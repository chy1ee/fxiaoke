package com.chylee.fxiaoke.controller.job;

import com.chylee.fxiaoke.common.event.ResponseEvent;
import com.chylee.fxiaoke.common.event.RestResponse;
import com.chylee.fxiaoke.core.service.JobTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("job/type")
public class JobTypeController {

    private final JobTypeService jobTypeService;

    public JobTypeController(JobTypeService jobTypeService) {
        this.jobTypeService = jobTypeService;
    }

    @GetMapping("refresh")
    public ResponseEvent refresh() {
        jobTypeService.refresh();
        return RestResponse.success(null);
    }

    @GetMapping
    public ResponseEvent list() {
        return RestResponse.success(jobTypeService.listJobTypes());
    }
}
