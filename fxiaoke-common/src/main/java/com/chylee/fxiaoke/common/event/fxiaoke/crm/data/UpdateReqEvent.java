package com.chylee.fxiaoke.common.event.fxiaoke.crm.data;

import com.chylee.fxiaoke.common.event.fxiaoke.crm.BaseCrmReqEvent;
import com.chylee.fxiaoke.common.exception.AccessTokenException;
import com.chylee.fxiaoke.common.api.AccessTokenManager;

public class UpdateReqEvent extends BaseCrmReqEvent {
    private boolean triggerWorkFlow;
    private UpdateData data;

    public boolean isTriggerWorkFlow() {
        return triggerWorkFlow;
    }

    public void setTriggerWorkFlow(boolean triggerWorkFlow) {
        this.triggerWorkFlow = triggerWorkFlow;
    }

    public UpdateData getData() {
        return data;
    }

    public void setData(UpdateData data) {
        this.data = data;
    }
}
