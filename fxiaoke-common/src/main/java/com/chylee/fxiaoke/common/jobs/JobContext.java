package com.chylee.fxiaoke.common.jobs;

import java.util.List;

public class JobContext {
    private boolean success = false;

    private String serialNo;
    private String error;
    private String type;
    private List<String> owner;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getOwner() {
        return owner;
    }

    public void setOwner(List<String> owner) {
        this.owner = owner;
    }

    public boolean isEmpty() {
        return type == null;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "JobContext{" +
                "success=" + success +
                ", serialNo='" + serialNo + '\'' +
                ", error='" + error + '\'' +
                ", type='" + type + '\'' +
                ", owner=" + owner +
                '}';
    }
}
