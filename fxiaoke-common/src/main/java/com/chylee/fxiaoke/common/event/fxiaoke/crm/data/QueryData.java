package com.chylee.fxiaoke.common.event.fxiaoke.crm.data;

public class QueryData {

    private String dataObjectApiName;

    private QueryInfo search_query_info;

    public String getDataObjectApiName() {
        return dataObjectApiName;
    }

    public void setDataObjectApiName(String dataObjectApiName) {
        this.dataObjectApiName = dataObjectApiName;
    }

    public QueryInfo getSearch_query_info() {
        return search_query_info;
    }

    public void setSearch_query_info(QueryInfo search_query_info) {
        this.search_query_info = search_query_info;
    }
}
