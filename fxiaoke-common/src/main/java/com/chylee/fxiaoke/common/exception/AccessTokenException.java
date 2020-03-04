package com.chylee.fxiaoke.common.exception;

@SuppressWarnings("serial")
public class AccessTokenException extends FxiaokeException {

    public AccessTokenException(int code, String msg) {
        super(code, msg);
    }

}
