package com.chylee.fxiaoke.common.event.fxiaoke.crm.object;

import com.chylee.fxiaoke.common.event.fxiaoke.BaseRespEvent;

public class ObjectRespEvent extends BaseRespEvent {
    private DescribeData data;

    public DescribeData getData() {
        return data;
    }

    public void setData(DescribeData data) {
        this.data = data;
    }
}
