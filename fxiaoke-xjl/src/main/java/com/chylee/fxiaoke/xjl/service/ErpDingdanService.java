package com.chylee.fxiaoke.xjl.service;

import com.chylee.fxiaoke.xjl.event.DingdanRespEvent;
import com.chylee.fxiaoke.xjl.event.ErpJobRespEvent;

public interface ErpDingdanService {
    ErpJobRespEvent list(String startTime, String endTime);
    DingdanRespEvent loadDingdan(String dh, String db);
}
