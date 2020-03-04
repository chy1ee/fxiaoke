package com.chylee.fxiaoke.common.event;

public class ResponseEvent implements Event {
    private int code;
    private String message;

    public ResponseEvent() {
    }

    public ResponseEvent(String message) {
        this(-1, message);
    }

    public ResponseEvent(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return code == 0 ? true : false;
    }

    public void setError(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
