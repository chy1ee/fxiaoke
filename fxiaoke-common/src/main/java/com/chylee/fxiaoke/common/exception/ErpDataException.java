package com.chylee.fxiaoke.common.exception;

public class ErpDataException extends ExecutorExcetpion {

    public ErpDataException(int code, String msg) {
        super(code, msg);
    }

    public ErpDataException(String msg) {
        super(msg);
    }

}
