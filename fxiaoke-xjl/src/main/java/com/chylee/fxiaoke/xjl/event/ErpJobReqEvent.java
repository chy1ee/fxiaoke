package com.chylee.fxiaoke.xjl.event;

import com.chylee.fxiaoke.common.event.RequestEvent;

public class ErpJobReqEvent extends RequestEvent {
    private String startTime;
    private String endTime;
    private int page;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
