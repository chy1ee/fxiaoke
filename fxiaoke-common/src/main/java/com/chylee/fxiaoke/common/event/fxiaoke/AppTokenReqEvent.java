package com.chylee.fxiaoke.common.event.fxiaoke;

import com.chylee.fxiaoke.common.event.RequestEvent;

public class AppTokenReqEvent extends RequestEvent {
    /**
     * 应用ID
     */
    private String appId;

    /**
     * 应用秘钥
     */
    private String appSecret;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    @Override
    public String toString() {
        return "AppTokenReqEvent{" +
                "appId='" + appId + '\'' +
                ", appSecret='" + appSecret + '\'' +
                '}';
    }
}
