package com.chylee.fxiaoke.xjl.event;

import com.chylee.fxiaoke.common.event.ResponseEvent;
import com.chylee.fxiaoke.xjl.model.Copma;

public class AccountRespEvent extends ResponseEvent {
    private Copma copma;
    private boolean create;

    public AccountRespEvent() {
    }

    public AccountRespEvent(Copma copma, boolean create) {
        this.copma = copma;
        this.create = create;
    }

    public AccountRespEvent(int code, String message) {
        super(code, message);
    }

    public Copma getCopma() {
        return copma;
    }

    public void setCopma(Copma copma) {
        this.copma = copma;
    }

    public boolean isCreate() {
        return create;
    }

    public void setCreate(boolean create) {
        this.create = create;
    }
}
