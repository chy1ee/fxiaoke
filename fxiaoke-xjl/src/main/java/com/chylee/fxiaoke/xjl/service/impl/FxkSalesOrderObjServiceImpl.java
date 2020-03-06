package com.chylee.fxiaoke.xjl.service.impl;

import com.chylee.fxiaoke.common.api.AccessTokenManager;
import com.chylee.fxiaoke.common.event.fxiaoke.crm.data.QueryInfoFilter;
import com.chylee.fxiaoke.xjl.event.data.object.SalesOrderObj;
import com.chylee.fxiaoke.xjl.event.data.object.SalesOrderProductObj;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.exception.CrmDataException;
import com.chylee.fxiaoke.common.service.impl.AbstractCrmServiceImpl;
import com.chylee.fxiaoke.xjl.service.FxkSalesOrderObjService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FxkSalesOrderObjServiceImpl extends AbstractCrmServiceImpl implements FxkSalesOrderObjService {
    public FxkSalesOrderObjServiceImpl(AccessTokenManager accessTokenManager) {
        super(accessTokenManager);
    }

    @Override
    public void save(SalesOrderObj salesOrderObj, Map<String, List<SalesOrderProductObj>> detail) throws CrmApiException {
        updateDataObj(salesOrderObj, detail, false, true);
    }

    @Override
    public List<SalesOrderObj> listByDbAndDh(String db, String dh) throws CrmApiException {
        List<QueryInfoFilter> queryInfoFilters = new ArrayList<>();
        queryInfoFilters.add(new QueryInfoFilter("field_4m6gr__c", db));
        queryInfoFilters.add(new QueryInfoFilter("field_4xWXT__c", dh));
        return queryDataObj("SalesOrderObj", queryInfoFilters, false, SalesOrderObj.class);
    }
}
