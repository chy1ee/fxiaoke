package com.chylee.fxiaoke.common.service.impl;

import com.chylee.fxiaoke.common.api.AccessTokenManager;
import com.chylee.fxiaoke.common.event.fxiaoke.crm.approval.QueryReqEvent;
import com.chylee.fxiaoke.common.event.fxiaoke.crm.approval.QueryRespEvent;
import com.chylee.fxiaoke.common.event.fxiaoke.crm.approval.State;
import com.chylee.fxiaoke.common.service.impl.AbstractFxkServiceImpl;
import com.chylee.fxiaoke.common.service.FxkApprovalService;
import org.springframework.stereotype.Service;

@Service
public class FxkApprovalServiceImpl extends AbstractFxkServiceImpl implements FxkApprovalService {
    public FxkApprovalServiceImpl(AccessTokenManager accessTokenManager) {
        super(accessTokenManager);
    }

    public QueryRespEvent query(String flowApiName, State state, long startTime, long endTime,
                                Integer pageSize, Integer pageNumber) {
        QueryReqEvent reqEvent = new QueryReqEvent();
        reqEvent.setFlowApiName(flowApiName);
        reqEvent.setState(state);
        reqEvent.setStartTime(startTime);
        reqEvent.setEndTime(endTime);
        reqEvent.setPageNumber(pageNumber);
        reqEvent.setPageSize(pageSize);
        return doPost("/cgi/crm/approvalInstances/query", reqEvent, QueryRespEvent.class);
    }
}
