package com.chylee.fxiaoke.common.event.fxiaoke.crm.data;

import com.chylee.fxiaoke.common.event.fxiaoke.data.object.DataObject;
import com.chylee.fxiaoke.common.event.fxiaoke.BaseRespEvent;

public class GetRespEvent<T extends DataObject> extends BaseRespEvent {
    public T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
