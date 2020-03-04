package com.chylee.fxiaoke.xjl.event.crm;

import com.chylee.fxiaoke.common.api.AccessTokenManager;
import com.chylee.fxiaoke.common.event.fxiaoke.BaseReqEvent;
import com.chylee.fxiaoke.common.exception.AccessTokenException;

public class DeptUserReqEvent extends BaseReqEvent {
    private String openUserId;

    public String getOpenUserId() {
        return openUserId;
    }

    public void setOpenUserId(String openUserId) {
        this.openUserId = openUserId;
    }
}
