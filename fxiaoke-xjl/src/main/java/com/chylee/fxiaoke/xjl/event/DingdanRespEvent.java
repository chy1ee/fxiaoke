package com.chylee.fxiaoke.xjl.event;

import com.chylee.fxiaoke.common.event.ResponseEvent;
import com.chylee.fxiaoke.xjl.event.data.object.SalesOrderObj;
import com.chylee.fxiaoke.xjl.event.data.object.SalesOrderProductObj;

import java.util.List;


public class DingdanRespEvent extends ResponseEvent {
    private SalesOrderObj salesOrderObj;
    private List<SalesOrderProductObj> salesOrderProductObjs;

    public SalesOrderObj getSalesOrderObj() {
        return salesOrderObj;
    }

    public void setSalesOrderObj(SalesOrderObj salesOrderObj) {
        this.salesOrderObj = salesOrderObj;
    }

    public List<SalesOrderProductObj> getSalesOrderProductObjs() {
        return salesOrderProductObjs;
    }

    public void setSalesOrderProductObjs(List<SalesOrderProductObj> salesOrderProductObjs) {
        this.salesOrderProductObjs = salesOrderProductObjs;
    }
}
