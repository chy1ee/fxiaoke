package com.chylee.fxiaoke.common.event.fxiaoke.msg;

import com.chylee.fxiaoke.common.event.fxiaoke.BaseReqEvent;
import com.chylee.fxiaoke.common.exception.AccessTokenException;
import com.chylee.fxiaoke.common.api.AccessTokenManager;

import java.util.List;

public class MsgReqEvent extends BaseReqEvent {
    private List<String> toUser;

    private String msgType;

    private Content text;

    private Composite composite;

    public List<String> getToUser() {
        return toUser;
    }

    public void setToUser(List<String> toUser) {
        this.toUser = toUser;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public Content getText() {
        return text;
    }

    public void setText(Content text) {
        this.text = text;
    }

    public Composite getComposite() {
        return composite;
    }

    public void setComposite(Composite composite) {
        this.composite = composite;
    }

    @Override
    public String toString() {
        return "MsgReqEvent{" +
                "toUser=" + toUser +
                ", msgType='" + msgType + '\'' +
                ", text=" + text +
                ", composite=" + composite +
                '}';
    }
}
