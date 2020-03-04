package com.chylee.fxiaoke.xjl.event;

import com.chylee.fxiaoke.common.event.ResponseEvent;

import java.util.Date;

public class DbnowRespEvent extends ResponseEvent {
    private Date now;

    public Date getNow() {
        return now;
    }

    public void setNow(Date now) {
        this.now = now;
    }
}
