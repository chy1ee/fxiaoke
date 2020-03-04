package com.chylee.fxiaoke.xjl.service;

import com.chylee.fxiaoke.xjl.event.ErpJobRespEvent;
import com.chylee.fxiaoke.xjl.event.ShebeiRespEvent;

public interface ErpShebeiService {
    ErpJobRespEvent list(String startTime, String endTime);
    ShebeiRespEvent loadShebei(String db, String dh);
}
