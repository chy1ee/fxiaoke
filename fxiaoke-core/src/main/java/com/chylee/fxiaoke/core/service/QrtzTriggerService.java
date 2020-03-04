package com.chylee.fxiaoke.core.service;

import com.chylee.fxiaoke.common.event.support.Page;
import com.chylee.fxiaoke.common.exception.ChyleeException;
import com.chylee.fxiaoke.core.model.QrtzTrigger;
import org.quartz.SchedulerException;

public interface QrtzTriggerService {
    QrtzTrigger saveTrigger(QrtzTrigger qrtzTrigger) throws ChyleeException, SchedulerException;
    void deleteTrigger(int triggerId) throws ChyleeException;
    Page listTriggerByPage(Page page);
    QrtzTrigger toggleStatus(int triggerId, Byte status) throws SchedulerException;
}
