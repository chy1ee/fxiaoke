package com.chylee.fxiaoke.xjl.service;

import com.chylee.fxiaoke.xjl.event.ErpJobRespEvent;
import com.chylee.fxiaoke.xjl.event.HuikuanRespEvent;

public interface ErpHuikuanService {
    ErpJobRespEvent list(String startTime, String endTime);
    HuikuanRespEvent loadHuikuan(String dh, String db);
}
