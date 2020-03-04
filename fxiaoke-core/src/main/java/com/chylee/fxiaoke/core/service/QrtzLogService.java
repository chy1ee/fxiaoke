package com.chylee.fxiaoke.core.service;

import com.chylee.fxiaoke.common.event.support.Page;
import com.chylee.fxiaoke.core.model.QrtzLog;

public interface QrtzLogService {
    Page listLogByPage(Page page);
    void insertLog(QrtzLog qrtzLog);
    void updateEndtimeById(int id, String error);
}
