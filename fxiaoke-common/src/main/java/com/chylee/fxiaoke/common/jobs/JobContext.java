package com.chylee.fxiaoke.common.jobs;

import java.util.List;

public class JobContext {
    private String serialNo;
    private String message;
    private String type;
    private List<String> owner;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    @Override
    public String toString() {
        return "JobContext{" +
                ", serialNo='" + serialNo + '\'' +
                ", message='" + message + '\'' +
                ", type='" + type + '\'' +
                ", owner=" + owner +
                '}';
    }
}
