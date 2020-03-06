package com.chylee.fxiaoke.common.api;

public class Constants {

    public static final String SESSION_CURRENT_OPEN_USER_ID = "currentOpenUserId";

    private Constants() {}

    /**
     * 接口返回码
     * 
     * @author gaoshengbo
     *
     */
    public enum interfaceResponseCode {

        EXECUTOR_IGNORED_SYNCHRONIZE(20001, "数据已对接过，如果需要重新对接，请清空CRM的单号单别并重置任务"),
        EXECUTOR_WRITE_BACK_ERROR(20002, "对接成功，回写单别和单号失败"),
        FXK_APP_ACCESS_TOKEN_EXPIRED(20005, "appAccessToken不存在或者已经过期"),
        FXK_CORP_ACCESS_TOKEN_EXPIRED(20016, "corpAccessToken不存在或者已经过期"),
        FXK_POST_JSON_CONVERT_ERROR(20020, "JSON转个成对象失败");

        public int code;

        public String msg;

        private interfaceResponseCode(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    /**
     * 异常码定义
     * 
     * @author gaoshengbo
     *
     */
    public enum interfaceException {

        ILLEGAL_ARGUMENT(-8, "参数不合法"),

        INTERFACE_EXCEPTION(-9, "调用接口失败");

        public int code;

        public String msg;

        private interfaceException(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

}
