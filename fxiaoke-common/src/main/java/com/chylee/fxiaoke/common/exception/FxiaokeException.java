package com.chylee.fxiaoke.common.exception;

@SuppressWarnings("serial")
public class FxiaokeException extends Exception {

    /**
     * 异常代码
     */
    private int code;

    /**
     * 异常信息
     */
    private String msg;

    public FxiaokeException(int code, String msg) {
        super(code + ":" + msg);
        this.code = code;
        this.msg = msg;
    }

    public FxiaokeException(Throwable cause) {
        super(cause);
    }

    public FxiaokeException(int code, String msg, Throwable cause) {
        super(code + ":" + msg, cause);
        this.code = code;
        this.msg = msg;
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
