package com.chylee.fxiaoke.common.model;

import java.util.Date;

public class JobDetail {

    private Integer id;
    private Integer logId;
    private String dataId;
    private Integer status;
    private String error;
    private Date lastTime;

    private Integer typeId;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    @Override
    public String toString() {
        return "JobDetailEntity{" +
                "id=" + id +
                ", logId=" + logId +
                ", typeId=" + typeId +
                ", dataId='" + dataId + '\'' +
                ", status=" + status +
                ", error='" + error + '\'' +
                '}';
    }
}
