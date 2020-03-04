package com.chylee.fxiaoke.core.service.impl;

import com.chylee.fxiaoke.common.event.support.Page;
import com.chylee.fxiaoke.core.mapper.QrtzLogMapper;
import com.chylee.fxiaoke.core.model.QrtzLog;
import com.chylee.fxiaoke.core.service.QrtzLogService;
import org.springframework.stereotype.Service;

@Service
public class QrtzLogServiceImpl implements QrtzLogService {
    private final QrtzLogMapper qrtzLogMapper;

    public QrtzLogServiceImpl(QrtzLogMapper qrtzLogMapper) {
        this.qrtzLogMapper = qrtzLogMapper;
    }

    @Override
    public Page listLogByPage(Page page) {
        page.setTotal(qrtzLogMapper.count());
        if (page.getTotal() > 0)
            page.setData(qrtzLogMapper.listByPage((page.getPage() - 1) * page.getPageSize(), page.getPageSize()));
        return page;
    }

    @Override
    public void insertLog(QrtzLog qrtzLog) {
        qrtzLogMapper.insert(qrtzLog);
    }

    @Override
    public void updateEndtimeById(int id, String error) {
        qrtzLogMapper.updateEndtimeById(id, error);
    }
}
