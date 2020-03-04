package com.chylee.fxiaoke.common.event.config;

import com.chylee.fxiaoke.common.event.RequestEvent;

import javax.validation.constraints.NotBlank;

public class ConfigAdminReqEvent extends RequestEvent {
    public static final int TYPE = 1002;

    private Integer id;

    private int status;

    @NotBlank(message = "接收人不能为空")
    private String openid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
