package com.chylee.fxiaoke.service.impl;

import com.chylee.fxiaoke.common.api.AccessTokenManager;
import com.chylee.fxiaoke.common.event.fxiaoke.crm.data.*;
import com.chylee.fxiaoke.common.event.fxiaoke.data.object.AccountObj;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.service.impl.AbstractCrmServiceImpl;
import com.chylee.fxiaoke.service.TestAccountService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TestAccountServiceImpl extends AbstractCrmServiceImpl implements TestAccountService {
    public TestAccountServiceImpl(AccessTokenManager accessTokenManager) {
        super(accessTokenManager);
    }

    @Override
    public List<AccountObj> getAccounts() throws CrmApiException {
        List<QueryInfoFilter> queryInfoFilters = new ArrayList<>();
        queryInfoFilters.add(new QueryInfoFilter("last_modified_time", "1582905600000", "GT"));
        queryInfoFilters.add(new QueryInfoFilter("last_modified_time", "1582992000000", "LT"));
        //List<AccountObj> accountObjs = queryDataObj("AccountObj", queryInfoFilters, false, AccountObj.class);

        QueryInfo queryInfo = new QueryInfo();
        queryInfo.setFilters(queryInfoFilters);
        queryInfo.setOrders(null);
        queryInfo.setFieldProjection(Arrays.asList(new String[]{"_id", "name", "field_AlGoN__c"}));
        QueryData data = new QueryData();
        data.setDataObjectApiName("AccountObj");
        data.setSearch_query_info(queryInfo);
        QueryReqEvent reqEvent = new QueryReqEvent();
        reqEvent.setData(data);
        QueryRespEvent<AccountObj> respEvent=  doPost("/cgi/crm/v2/data/query", reqEvent, QueryRespEvent.class, AccountObj.class);

        return respEvent.getData().getDataList();
    }

    @Override
    public void updateAccount() {
        String[] dataId = new String[]{"5e53841bac18140001f781c8","5e533f5f1a747b000156886f",
                "5e537483c9d1e70001d5125d", "5e5374d827f03e000115a81c"};
        String[] khbh = new String[]{"14Y276", "21G085", "21Y278", "17Y277"};
        String[] error = new String[]{"14Y270", "21G082", "21Y269", "17Y268"};

        for (int i=0;i<4;i++) {
            try {
                AccountObj accountObj = this.loadDataObj("AccountObj", dataId[i], false, AccountObj.class);
                if (error[i].equals(accountObj.getField_AlGoN__c())) {
                    AccountObj accountObjToUpdate = new AccountObj();
                    accountObjToUpdate.setDataObjectApiName("AccountObj");
                    accountObjToUpdate.set_id(dataId[i]);
                    accountObjToUpdate.setField_AlGoN__c(khbh[i]);
                    updateDataObj(accountObjToUpdate);
                }
            } catch (CrmApiException e) {
                logger.error("{},{},{}",e.getMsg(),dataId[i],error[i]);
            }
        }

    }
}
