package com.chylee.fxiaoke.common.service.impl;

import com.chylee.fxiaoke.common.api.*;
import com.chylee.fxiaoke.common.event.RequestEvent;
import com.chylee.fxiaoke.common.event.fxiaoke.BaseReqEvent;
import com.chylee.fxiaoke.common.event.fxiaoke.CorpAccessToken;
import com.chylee.fxiaoke.common.event.fxiaoke.crm.BaseCrmReqEvent;
import com.chylee.fxiaoke.common.exception.AccessTokenException;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.event.fxiaoke.ObjectRespEvent;

public class AbstractFxkServiceImpl extends FXiaokeApi {
    private final AccessTokenManager accessTokenManager;

    public AbstractFxkServiceImpl(AccessTokenManager accessTokenManager) {
        this.accessTokenManager = accessTokenManager;
    }

    protected void Debug(String msg, Object... objects) {
        if (logger.isDebugEnabled())
            logger.debug(msg, objects);
    }

    protected String getAdminOpenId() throws CrmApiException {
        try {
            return accessTokenManager.getCorpAccessToken().getSyncOpenId();
        } catch (AccessTokenException e) {
            throw new CrmApiException(e.getMsg());
        }
    }

    @Override
    protected ObjectRespEvent<String> validate(RequestEvent reqEvent) {
        ObjectRespEvent<String> result = new ObjectRespEvent<>();

        if (reqEvent instanceof  BaseReqEvent) {
            try {
                CorpAccessToken corpAccessToken = accessTokenManager.getCorpAccessToken();
                ((BaseReqEvent) reqEvent).setCorpAccessToken(corpAccessToken);
                if (reqEvent instanceof BaseCrmReqEvent)
                    ((BaseCrmReqEvent)reqEvent).setCurrentOpenUserId(corpAccessToken.getSyncOpenId());
            } catch (AccessTokenException e) {
                result.setErrorCode(Constants.interfaceResponseCode.FXK_CORP_ACCESS_TOKEN_EXPIRED.code);
                result.setErrorMessage(Constants.interfaceResponseCode.FXK_CORP_ACCESS_TOKEN_EXPIRED.msg);
                return result;
            }
        }

        return result;
    }
}
