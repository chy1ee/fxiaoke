package com.chylee.fxiaoke.common.event.fxiaoke.crm.data;

public class QueryInfoOrder {
    private String fieldName;
    private boolean isAsc;

    public QueryInfoOrder(String fieldName, boolean isAsc) {
        this.fieldName = fieldName;
        this.isAsc = isAsc;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public boolean isAsc() {
        return isAsc;
    }

    public void setAsc(boolean asc) {
        isAsc = asc;
    }
}
