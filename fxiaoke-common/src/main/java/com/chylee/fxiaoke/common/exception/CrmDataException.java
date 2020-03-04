package com.chylee.fxiaoke.common.exception;

public class CrmDataException extends ExecutorExcetpion {

    public CrmDataException(int code, String msg) {
        super(code, msg);
    }

    public CrmDataException(String msg) {
        super(msg);
    }
}
