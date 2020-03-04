package com.chylee.fxiaoke.xjl.event;

import com.chylee.fxiaoke.common.event.ResponseEvent;

public class AccountRespEvent extends ResponseEvent {
    private String khbh;

    public AccountRespEvent() {
    }

    public AccountRespEvent(String khbh) {
        this.khbh = khbh;
    }

    public AccountRespEvent(int code, String message) {
        super(code, message);
    }

    public String getKhbh() {
        return khbh;
    }

    public void setKhbh(String khbh) {
        this.khbh = khbh;
    }
}
