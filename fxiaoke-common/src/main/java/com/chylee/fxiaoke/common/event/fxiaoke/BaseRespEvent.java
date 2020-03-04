package com.chylee.fxiaoke.common.event.fxiaoke;

import com.chylee.fxiaoke.common.event.ResponseEvent;

public class BaseRespEvent extends ResponseEvent {
    public void setErrorCode(int code) {
        this.setCode(code);
    }

    public void setErrorMessage(String message) {
        this.setMessage(message);
    }

    public int getErrorCode() {
        return getCode();
    }

    public String getErrorMessage() {
        return getMessage();
    }
}
