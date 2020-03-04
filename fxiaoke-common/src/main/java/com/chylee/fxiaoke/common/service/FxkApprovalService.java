package com.chylee.fxiaoke.common.service;

import com.chylee.fxiaoke.common.event.fxiaoke.crm.approval.QueryRespEvent;
import com.chylee.fxiaoke.common.event.fxiaoke.crm.approval.State;

public interface FxkApprovalService {
    QueryRespEvent query(String flowApiName, State state, long startTime, long endTime,
                         Integer pageSize, Integer pageNumber);
}
