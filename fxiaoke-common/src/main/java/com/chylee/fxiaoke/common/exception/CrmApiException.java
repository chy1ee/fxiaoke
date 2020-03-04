package com.chylee.fxiaoke.common.exception;

public class CrmApiException extends ExecutorExcetpion {

    public CrmApiException(int code, String msg) {
        super(code, msg);
    }

    public CrmApiException(String msg) {
        this(99999, msg);
    }
}
