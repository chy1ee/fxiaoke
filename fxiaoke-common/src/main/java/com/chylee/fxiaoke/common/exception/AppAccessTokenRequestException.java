package com.chylee.fxiaoke.common.exception;

@SuppressWarnings("serial")
public class AppAccessTokenRequestException extends AccessTokenException {

    public AppAccessTokenRequestException(int code, String msg) {
        super(code, msg);
    }

}
