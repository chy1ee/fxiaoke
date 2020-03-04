package com.chylee.fxiaoke.common.event.fxiaoke.crm.approval;

import java.util.List;

public class QueryResult {
    private int total;
    private List<Instance> instanceList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Instance> getInstanceList() {
        return instanceList;
    }

    public void setInstanceList(List<Instance> instanceList) {
        this.instanceList = instanceList;
    }
}
