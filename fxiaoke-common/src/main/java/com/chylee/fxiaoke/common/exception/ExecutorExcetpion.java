package com.chylee.fxiaoke.common.exception;

public class ExecutorExcetpion extends Exception {
    /**
     * 异常代码
     */
    private int code;

    /**
     * 异常信息
     */
    private String msg;

    public ExecutorExcetpion(int code, String msg) {
        super(code + ":" + msg);
        this.code = code;
        this.msg = msg;
    }

    public ExecutorExcetpion(String msg) {
        this(99999, msg);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
