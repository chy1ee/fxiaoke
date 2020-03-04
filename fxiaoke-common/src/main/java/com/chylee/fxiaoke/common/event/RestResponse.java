package com.chylee.fxiaoke.common.event;

import com.chylee.fxiaoke.common.event.support.Page;
import com.chylee.fxiaoke.common.exception.ChyleeException;
import com.chylee.fxiaoke.common.util.ErrorCodeUtils;

import java.util.Collection;

public class RestResponse {

    public static ResponseEvent success(Object obj) {
        if(obj instanceof  ResponseEvent)
            return (ResponseEvent)obj;

        DataRespEvent respEvent = new DataRespEvent();
        if(obj instanceof Page) {
            Page page = (Page)obj;
            respEvent.setList(page.getData());
            respEvent.setPage(page.getPage());
            respEvent.setPages(page.getPages());
            respEvent.setPageSize(page.getPageSize());
            respEvent.setTotal(page.getTotal());
        }
        else if(obj instanceof Collection) {
            respEvent.setList((Collection) obj);
        }
        else {
            respEvent.setData(obj);
        }

        return respEvent;
    }

    public static ResponseEvent failure(int code, String message) {
        return new ResponseEvent(code, message);
    }

    public static ResponseEvent failure(Exception e) {
        if(e instanceof ChyleeException) {
            ChyleeException chyleeException = (ChyleeException)e;
            return failure(chyleeException.getCode(), chyleeException.getMessage());
        }

        return failure(-1, "未知错误");
    }

    public static ResponseEvent failure(int code) {
        String message = ErrorCodeUtils.getMessage(code);
        return failure(code, message == null ? "未知错误" : message);
    }
}
