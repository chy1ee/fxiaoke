package com.chylee.fxiaoke.common.exception;

public class ServiceException extends Exception {
    public ServiceException() {
        super();
    }

    public ServiceException(String error) {
        super(error);
    }
}
