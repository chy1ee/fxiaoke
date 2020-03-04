package com.chylee.fxiaoke.common.event.fxiaoke.crm.data;

import com.chylee.fxiaoke.common.event.fxiaoke.BaseRespEvent;

public class QueryRespEvent<T> extends BaseRespEvent {
    private QueryResult<T> data;

    public QueryResult<T> getData() {
        return data;
    }

    public void setData(QueryResult<T> data) {
        this.data = data;
    }
}
