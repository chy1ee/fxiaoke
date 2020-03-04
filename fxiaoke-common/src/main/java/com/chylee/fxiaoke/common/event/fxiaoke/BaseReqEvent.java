package com.chylee.fxiaoke.common.event.fxiaoke;

import com.chylee.fxiaoke.common.event.RequestEvent;
import com.chylee.fxiaoke.common.api.AccessTokenManager;
import com.chylee.fxiaoke.common.exception.AccessTokenException;

public class BaseReqEvent extends RequestEvent {

    /**
     * 第三方应用获得企业授权的凭证
     */
    protected String corpAccessToken;

    /**
     * 开放平台派发的公司账号
     */
    protected String corpId;

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

    public void setCorpAccessToken(CorpAccessToken corpAccessToken) throws AccessTokenException {
        if (corpAccessToken == null)
            throw new AccessTokenException(-1, "corpAccessToken不允许为空");

        this.setCorpAccessToken(corpAccessToken.getCorpAccessToken());
        this.setCorpId(corpAccessToken.getCorpId());
    }

    @Override
    public String toString() {
        return "BaseReqEvent{" +
                "corpAccessToken='" + corpAccessToken + '\'' +
                ", corpId='" + corpId + '\'' +
                '}';
    }
}
