package com.chylee.fxiaoke.common.event.fxiaoke.crm.data;

import com.chylee.fxiaoke.common.event.fxiaoke.BaseRespEvent;

public class UpdateRespEvent extends BaseRespEvent {
    private String dataId;

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }
}
