package com.chylee.fxiaoke.common.event.fxiaoke;


public class AppTokenRespEvent extends BaseRespEvent {

    /**
     * appAccessToken
     */
    private String appAccessToken;

    /**
     * appToken 超时时间(单位为：分钟)
     */
    private long expiresIn;

    public String getAppAccessToken() {
        return appAccessToken;
    }

    public void setAppAccessToken(String appAccessToken) {
        this.appAccessToken = appAccessToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    @Override
    public String toString() {
        return "AppTokenRespEvent{" +
                "appAccessToken='" + appAccessToken + '\'' +
                ", expiresIn=" + expiresIn +
                ", errorCode=" + getErrorCode() +
                ", errorMessage='" + getErrorMessage() + '\'' +
                '}';
    }
}
