package com.chylee.fxiaoke.common.event.fxiaoke.crm.approval;

public class Instance {
    private String instanceId;
    private String instanceName;
    private String dataId;
    private String triggerType;
    private State state;
    private long createTime;
    private long lastModifyTime;
    private long endTime;
    private String flowApiName;
    private String applicantOpenUserId;
    private String cancelTime;
    private String objectApiName;
    private int logId;

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(long lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getFlowApiName() {
        return flowApiName;
    }

    public void setFlowApiName(String flowApiName) {
        this.flowApiName = flowApiName;
    }

    public String getApplicantOpenUserId() {
        return applicantOpenUserId;
    }

    public void setApplicantOpenUserId(String applicantOpenUserId) {
        this.applicantOpenUserId = applicantOpenUserId;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getObjectApiName() {
        return objectApiName;
    }

    public void setObjectApiName(String objectApiName) {
        this.objectApiName = objectApiName;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }
}
