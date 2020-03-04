package com.chylee.fxiaoke.common.event.fxiaoke.crm;

import com.chylee.fxiaoke.common.event.fxiaoke.BaseReqEvent;
import com.chylee.fxiaoke.common.exception.AccessTokenException;
import com.chylee.fxiaoke.common.api.AccessTokenManager;

public class BaseCrmReqEvent extends BaseReqEvent {
    private String currentOpenUserId;

    public void setCurrentOpenUserId(String currentOpenUserId) {
        this.currentOpenUserId = currentOpenUserId;
    }

    public String getCurrentOpenUserId() {
        return currentOpenUserId;
    }
}
