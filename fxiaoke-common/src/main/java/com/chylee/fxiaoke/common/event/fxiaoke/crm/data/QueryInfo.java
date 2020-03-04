package com.chylee.fxiaoke.common.event.fxiaoke.crm.data;

import java.util.ArrayList;
import java.util.List;

public class QueryInfo {
    private int limit = 100;
    private int offset = 0;
    private List<String> fieldProjection;
    private List<QueryInfoFilter> filters;
    private List<QueryInfoOrder> orders;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void addFieldProjection (String fieldProjection) {
        if (this.fieldProjection == null)
            this.fieldProjection = new ArrayList<>();
        this.fieldProjection.add(fieldProjection);
    }

    public List<String> getFieldProjection() {
        return fieldProjection;
    }

    public void setFieldProjection(List<String> fieldProjection) {
        this.fieldProjection = fieldProjection;
    }

    public void addFilter(QueryInfoFilter filter) {
        if (filters == null)
            filters = new ArrayList<>();
        this.filters.add(filter);
    }

    public List<QueryInfoFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<QueryInfoFilter> filters) {
        this.filters = filters;
    }

    public void addOrder(QueryInfoOrder order) {
        if (orders == null)
            orders = new ArrayList<>();
        orders.add(order);
    }

    public List<QueryInfoOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<QueryInfoOrder> orders) {
        this.orders = orders;
    }
}
