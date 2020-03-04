package com.chylee.fxiaoke.common.exception;

public class ErpApiException extends ExecutorExcetpion {

    public ErpApiException(int code, String msg) {
        super(code, msg);
    }

    public ErpApiException(String msg) {
        super(msg);
    }
}
