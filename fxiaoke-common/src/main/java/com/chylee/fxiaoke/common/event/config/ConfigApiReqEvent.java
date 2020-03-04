package com.chylee.fxiaoke.common.event.config;

import com.chylee.fxiaoke.common.event.RequestEvent;
import com.chylee.fxiaoke.common.event.ResponseEvent;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;


public class ConfigApiReqEvent extends RequestEvent {
    public static final int TYPE = 1001;

    private Integer id;

    @Min(value = 1, message = "type值非法")
    private int type;

    @NotBlank(message = "appId不允许为空")
    private String appId;

    @NotBlank(message = "appSecret不允许为空")
    private String appSecret;

    @NotBlank(message = "permanentCode不允许为空")
    private String permanentCode;

    @NotBlank(message = "token不允许为空")
    private String token;

    @NotBlank(message = "encodingAesKey不允许为空")
    private String encodingAesKey;

    @NotBlank(message = "authorizeUrl不允许为空")
    private String authorizeUrl;

    @NotBlank(message = "syncOpenId不允许为空")
    private String syncOpenId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEncodingAesKey() {
        return encodingAesKey;
    }

    public void setEncodingAesKey(String encodingAesKey) {
        this.encodingAesKey = encodingAesKey;
    }

    public String getAuthorizeUrl() {
        return authorizeUrl;
    }

    public void setAuthorizeUrl(String authorizeUrl) {
        this.authorizeUrl = authorizeUrl;
    }

    public String getSyncOpenId() {
        return syncOpenId;
    }

    public void setSyncOpenId(String syncOpenId) {
        this.syncOpenId = syncOpenId;
    }

}
