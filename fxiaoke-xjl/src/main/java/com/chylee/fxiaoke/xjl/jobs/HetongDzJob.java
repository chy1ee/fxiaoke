package com.chylee.fxiaoke.xjl.jobs;

import com.chylee.fxiaoke.common.jobs.quartz.SpringQuartzJob;
import com.chylee.fxiaoke.xjl.service.FxkHetongDjService;
import org.springframework.stereotype.Component;

@Component
public class HetongDzJob implements SpringQuartzJob {
    private final FxkHetongDjService hetongDjService;

    public HetongDzJob(FxkHetongDjService hetongDjService) {
        this.hetongDjService = hetongDjService;
    }

    @Override
    public void invoke(int qrtzId, String params) throws Exception {
        hetongDjService.upload();
    }
}
