package com.chylee.fxiaoke.common.event.fxiaoke;


public class ObjectRespEvent<T> extends BaseRespEvent {
    
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
