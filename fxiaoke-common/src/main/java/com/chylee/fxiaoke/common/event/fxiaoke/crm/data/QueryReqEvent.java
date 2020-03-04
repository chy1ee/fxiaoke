package com.chylee.fxiaoke.common.event.fxiaoke.crm.data;

import com.chylee.fxiaoke.common.event.fxiaoke.crm.BaseCrmReqEvent;
import com.chylee.fxiaoke.common.exception.AccessTokenException;
import com.chylee.fxiaoke.common.api.AccessTokenManager;

public class QueryReqEvent extends BaseCrmReqEvent {
    private QueryData data;

    public QueryData getData() {
        return data;
    }

    public void setData(QueryData data) {
        this.data = data;
    }
}
