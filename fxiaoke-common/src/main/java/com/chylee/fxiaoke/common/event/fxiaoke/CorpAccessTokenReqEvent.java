package com.chylee.fxiaoke.common.event.fxiaoke;

import com.chylee.fxiaoke.common.event.RequestEvent;

public class CorpAccessTokenReqEvent extends RequestEvent {

    /**
     * 应用ID
     */
    private String appId;

    /**
     * 应用秘钥
     */
    private String appSecret;

    /**
     * 永久授权码
     */
    private String permanentCode;

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

    public String getPermanentCode() {
        return permanentCode;
    }

    public void setPermanentCode(String permanentCode) {
        this.permanentCode = permanentCode;
    }

    @Override
    public String toString() {
        return "CorpAccessTokenReqEvent{" +
                "appId='" + appId + '\'' +
                ", appSecret='" + appSecret + '\'' +
                ", permanentCode='" + permanentCode + '\'' +
                '}';
    }
}
