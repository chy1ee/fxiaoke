package com.chylee.fxiaoke.common.event.fxiaoke.crm.approval;

import com.chylee.fxiaoke.common.event.fxiaoke.crm.BaseCrmReqEvent;

public class QueryReqEvent extends BaseCrmReqEvent {
    private String flowApiName;
    private State state;
    private Long startTime;
    private Long endTime;
    private String objectApiName;
    private Integer pageNumber;
    private int pageSize;

    public QueryReqEvent() {
        this.pageSize = 20;
    }

    public String getFlowApiName() {
        return flowApiName;
    }

    public void setFlowApiName(String flowApiName) {
        this.flowApiName = flowApiName;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getObjectApiName() {
        return objectApiName;
    }

    public void setObjectApiName(String objectApiName) {
        this.objectApiName = objectApiName;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
