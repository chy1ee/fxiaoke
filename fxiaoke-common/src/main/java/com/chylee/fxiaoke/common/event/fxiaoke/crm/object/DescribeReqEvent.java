package com.chylee.fxiaoke.common.event.fxiaoke.crm.object;

import com.chylee.fxiaoke.common.event.fxiaoke.crm.BaseCrmReqEvent;
import com.chylee.fxiaoke.common.exception.AccessTokenException;
import com.chylee.fxiaoke.common.api.AccessTokenManager;

public class DescribeReqEvent extends BaseCrmReqEvent {
    private String apiName;

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }
}
