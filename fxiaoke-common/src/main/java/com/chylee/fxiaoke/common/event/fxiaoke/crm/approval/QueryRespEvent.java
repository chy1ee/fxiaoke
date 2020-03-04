package com.chylee.fxiaoke.common.event.fxiaoke.crm.approval;

import com.chylee.fxiaoke.common.event.fxiaoke.BaseRespEvent;

public class QueryRespEvent extends BaseRespEvent {
    private QueryResult queryResult;

    public QueryResult getQueryResult() {
        return queryResult;
    }

    public void setQueryResult(QueryResult queryResult) {
        this.queryResult = queryResult;
    }
}
