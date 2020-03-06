package com.chylee.fxiaoke.xjl.event.data.object;

import com.chylee.fxiaoke.common.event.fxiaoke.data.object.DataObject;

import java.util.Map;

public class UpdateDataDetail {
    private Map<String, DataObject> detail;

    public Map<String, DataObject> getDetail() {
        return detail;
    }

    public void setDetail(Map<String, DataObject> detail) {
        this.detail = detail;
    }

    public void addDetail(String key, DataObject obj) {
        if(detail == null)
            detail.put(key, obj);
    }
}
