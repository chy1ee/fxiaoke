package com.chylee.fxiaoke.common.event.fxiaoke;

public class CorpAccessTokenRespEvent extends BaseRespEvent {

    /**
     * corpAccessToken
     */
    private String corpAccessToken;

    /**
     * corpId
     */
    private String corpId;

    /**
     * corpAccessToken 超时时间(单位为：秒)
     */
    private long expiresIn;

    public String getCorpAccessToken() {
        return corpAccessToken;
    }

    public void setCorpAccessToken(String corpAccessToken) {
        this.corpAccessToken = corpAccessToken;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    @Override
    public String toString() {
        return "CorpAccessTokenRespEvent{" +
                "corpAccessToken='" + corpAccessToken + '\'' +
                ", corpId='" + corpId + '\'' +
                ", expiresIn=" + expiresIn +
                ", errorCode=" + getErrorCode() +
                ", errorMessage='" + getErrorMessage() + '\'' +
                '}';
    }
}
