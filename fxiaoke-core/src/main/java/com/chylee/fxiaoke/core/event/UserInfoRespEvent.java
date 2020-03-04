package com.chylee.fxiaoke.core.event;

import com.chylee.fxiaoke.common.event.ResponseEvent;

public class UserInfoRespEvent extends ResponseEvent {
    private String name;
    private String avatar;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
