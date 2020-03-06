package com.chylee.fxiaoke.xjl.jobs;

import com.chylee.fxiaoke.common.jobs.quartz.SpringQuartzJob;
import com.chylee.fxiaoke.xjl.service.FxkBaojiadanDjService;
import org.springframework.stereotype.Component;

@Component
public class BaojiadanDzJob implements SpringQuartzJob {
    private final FxkBaojiadanDjService baojaidanDj;

    public BaojiadanDzJob(FxkBaojiadanDjService baojaidanDj) {
        this.baojaidanDj = baojaidanDj;
    }

    @Override
    public void invoke(int qrtzId, String params) throws Exception {
        baojaidanDj.upload();
    }
}
