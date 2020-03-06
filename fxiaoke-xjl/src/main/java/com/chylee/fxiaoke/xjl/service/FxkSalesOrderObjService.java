package com.chylee.fxiaoke.xjl.service;

import com.chylee.fxiaoke.xjl.event.data.object.SalesOrderObj;
import com.chylee.fxiaoke.xjl.event.data.object.SalesOrderProductObj;
import com.chylee.fxiaoke.common.exception.CrmApiException;

import java.util.List;
import java.util.Map;

public interface FxkSalesOrderObjService {
    void save(SalesOrderObj salesOrderObj, Map<String, List<SalesOrderProductObj>> detail) throws CrmApiException;
    List<SalesOrderObj> listByDbAndDh(String db, String dh) throws CrmApiException;
}
