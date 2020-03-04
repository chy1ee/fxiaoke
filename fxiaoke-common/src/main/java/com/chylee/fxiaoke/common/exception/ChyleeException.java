package com.chylee.fxiaoke.common.exception;

public class ChyleeException extends Exception {
    private int code;
    private String message;

    public ChyleeException(int code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
        this.message = message;
    }

    public ChyleeException(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public ChyleeException(String message) {
        this(-1, message);
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
