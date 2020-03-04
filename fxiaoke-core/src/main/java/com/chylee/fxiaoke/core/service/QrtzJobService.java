package com.chylee.fxiaoke.core.service;

import com.chylee.fxiaoke.common.event.support.Page;
import com.chylee.fxiaoke.common.exception.ChyleeException;
import com.chylee.fxiaoke.core.model.QrtzJob;
import org.quartz.SchedulerException;

import java.util.List;

public interface QrtzJobService {
    QrtzJob saveJob(QrtzJob qrtzJob) throws ChyleeException;
    void deleteJob(int jobId) throws ChyleeException;
    Page listJobByPage(Page page);
    List<QrtzJob> list(String name);
    boolean triggerJob(int jobId);
    QrtzJob toggleStatus(int jobId, Byte status) throws SchedulerException;
}
