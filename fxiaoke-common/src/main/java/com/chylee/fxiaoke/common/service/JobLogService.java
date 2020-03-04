package com.chylee.fxiaoke.common.service;

import com.chylee.fxiaoke.common.event.support.Page;
import com.chylee.fxiaoke.common.model.JobLog;

import java.util.Date;

public interface JobLogService {
    Page listAll(int qrtzId, int valid, int page, int pageSize);
    JobLog current(int qrtzId, int typeId, Date endTime);
    void updateRowsById(int rows, int id);
    void updateMessageById(int id, String message);
    void updateResultById(int id, int success, int fail);
}
