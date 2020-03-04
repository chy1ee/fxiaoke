package com.chylee.fxiaoke.common.event.fxiaoke;

import java.io.Serializable;

public class CorpAccessToken implements Serializable {

    private static final long serialVersionUID = -4995204525912210225L;

    /**
     * corpAccessToken
     */
    private String corpAccessToken;

    /**
     * corpId
     */
    private String corpId;

    private String syncOpenId;

    public CorpAccessToken(String corpAccessToken, String corpId, String syncOpenId) {
        this.corpAccessToken = corpAccessToken;
        this.corpId = corpId;
        this.syncOpenId = syncOpenId;
    }

    public String getCorpAccessToken() {
        return corpAccessToken;
    }

    public String getCorpId() {
        return corpId;
    }

    public String getSyncOpenId() {
        return syncOpenId;
    }

    @Override
    public String toString() {
        return "CorpAccessToken{" +
                "corpAccessToken='" + corpAccessToken + '\'' +
                ", corpId='" + corpId + '\'' +
                ", syncOpenId='" + syncOpenId + '\'' +
                '}';
    }
}
