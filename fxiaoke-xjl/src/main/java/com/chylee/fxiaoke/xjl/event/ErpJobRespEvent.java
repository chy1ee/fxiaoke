package com.chylee.fxiaoke.xjl.event;

import com.chylee.fxiaoke.common.event.ResponseEvent;

import java.util.List;

public class ErpJobRespEvent extends ResponseEvent {
    private List<String> dataIdList;

    public List<String> getDataIdList() {
        return dataIdList;
    }

    public void setDataIdList(List<String> dataIdList) {
        this.dataIdList = dataIdList;
    }
}
