package com.chylee.fxiaoke.common.api;

import com.chylee.fxiaoke.common.event.fxiaoke.*;
import com.chylee.fxiaoke.common.exception.AccessTokenException;
import com.chylee.fxiaoke.common.exception.AppAccessTokenRequestException;
import com.chylee.fxiaoke.common.exception.CorpAccessTokenRequestException;
import com.chylee.fxiaoke.common.service.ConfigApiService;
import com.chylee.fxiaoke.common.service.impl.AbstractFxkServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AccessTokenManager extends FXiaokeApi {
    private Logger logger = LoggerFactory.getLogger(AccessTokenManager.class);

    private static final String KEY_EXPIRES_IN = "expiresIn";
    private static final String KEY_TOKEN = "token";
    private static final String APP_ACCESS_TOKEN_KEY_PREX = "appAccessToken_";
    private static final String CORP_ACCESS_TOKEN_KEY_PREX = "corpAccessToken_";

    private final ConfigApiService apiService;

    /**
     * AppAccessToken、CorpAccessToken缓存
     */
    private static Map<String, Map<String, Object>> accessTokenMap = new ConcurrentHashMap<>();

    public AccessTokenManager(ConfigApiService apiService) {
        this.apiService = apiService;
    }


    public void resetAppAccessToken() throws AppAccessTokenRequestException {
        accessTokenMap.remove(APP_ACCESS_TOKEN_KEY_PREX.concat(apiService.getConfig().getAppId()));
        getAppAccessToken();
    }

    public void resetCorpAccessToken() throws AccessTokenException {
        accessTokenMap.remove(CORP_ACCESS_TOKEN_KEY_PREX.concat(apiService.getConfig().getAppId()).concat(
                apiService.getConfig().getPermanentCode()));
        getCorpAccessToken();
    }

    public String getAppAccessToken() throws AppAccessTokenRequestException {
        String key = APP_ACCESS_TOKEN_KEY_PREX.concat(apiService.getConfig().getAppId());
        Map<String, Object> token = accessTokenMap.get(key);

        if (token != null) {
            long expiresIn = (Long) token.get(KEY_EXPIRES_IN);

            if (System.currentTimeMillis() < expiresIn) {
                return (String) token.get(KEY_TOKEN);
            }
            accessTokenMap.remove(key);
        }

        String appAccessToken = null;
        synchronized (this) {
            token = accessTokenMap.get(key);

            // 多线程环境下，其他线程可能已经获得最新appAccessToken，直接返回
            if (token != null) {
                return (String) token.get(KEY_TOKEN);
            }
            AppTokenReqEvent arg = new AppTokenReqEvent();
            arg.setAppId(apiService.getConfig().getAppId());
            arg.setAppSecret(apiService.getConfig().getAppSecret());
            AppTokenRespEvent result = doPost("/cgi/appAccessToken/get", arg, AppTokenRespEvent.class);
            if (result.getErrorCode() != 0) {
                throw new AppAccessTokenRequestException(result.getErrorCode(), result.getErrorMessage());
            }
            appAccessToken = result.getAppAccessToken();
            token = new  HashMap();
            // 减去3分钟，以免过时
            token.put(KEY_EXPIRES_IN, (result.getExpiresIn() - 3 * 60) * 1000 + System.currentTimeMillis());
            token.put(KEY_TOKEN, appAccessToken);
            accessTokenMap.put(key, token);
            return appAccessToken;
        }
    }

    public CorpAccessToken getCorpAccessToken() throws AccessTokenException {
        String key =
                CORP_ACCESS_TOKEN_KEY_PREX.concat(apiService.getConfig().getAppId()).concat(apiService.getConfig().getPermanentCode());
        Map<String, Object> token = accessTokenMap.get(key);

        if (token != null) {
            long expiresIn = (Long) token.get(KEY_EXPIRES_IN);
            if (System.currentTimeMillis() < expiresIn) {
                return (CorpAccessToken) token.get(KEY_TOKEN);
            }
            accessTokenMap.remove(key);
        }

        synchronized (this) {
            token = accessTokenMap.get(key);
            // 多线程环境下，其他线程可能已经获得最新corpAccessToken，直接返回
            if (token != null) {
                return (CorpAccessToken) token.get(KEY_TOKEN);
            }

            CorpAccessTokenReqEvent corpAccessTokenReqEvent = new CorpAccessTokenReqEvent();
            corpAccessTokenReqEvent.setAppId(apiService.getConfig().getAppId());
            corpAccessTokenReqEvent.setAppSecret(apiService.getConfig().getAppSecret());
            corpAccessTokenReqEvent.setPermanentCode(apiService.getConfig().getPermanentCode());
            CorpAccessTokenRespEvent corpAccessTokenRespEvent =  doPost("/cgi/corpAccessToken/get/V2",
                    corpAccessTokenReqEvent, CorpAccessTokenRespEvent.class);
            if(logger.isDebugEnabled())
                logger.debug(corpAccessTokenRespEvent.toString());
            if (corpAccessTokenRespEvent != null && corpAccessTokenRespEvent.getErrorCode() == 0) {
                CorpAccessToken corpAccessToken = new CorpAccessToken(corpAccessTokenRespEvent.getCorpAccessToken(),
                        corpAccessTokenRespEvent.getCorpId(), apiService.getConfig().getSyncOpenId());
                token = new HashMap<>();
                // 减去3分钟，以免过时
                token.put(KEY_EXPIRES_IN, (corpAccessTokenRespEvent.getExpiresIn() - 3 * 60) * 1000 + System.currentTimeMillis());
                token.put(KEY_TOKEN, corpAccessToken);
            }
            if (token == null) {
                throw new CorpAccessTokenRequestException(corpAccessTokenRespEvent.getErrorCode(),corpAccessTokenRespEvent.getErrorMessage());
            }
            accessTokenMap.put(key, token);
            CorpAccessToken corpAccessToken = (CorpAccessToken) token.get(KEY_TOKEN);
            if (corpAccessToken == null) {
                throw new CorpAccessTokenRequestException(corpAccessTokenRespEvent.getErrorCode(),corpAccessTokenRespEvent.getErrorMessage());
            }

            return corpAccessToken;
        }
    }
}
