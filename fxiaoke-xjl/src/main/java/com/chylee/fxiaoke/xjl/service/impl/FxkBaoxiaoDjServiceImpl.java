package com.chylee.fxiaoke.xjl.service.impl;

import com.chylee.fxiaoke.common.api.AccessTokenManager;
import com.chylee.fxiaoke.common.event.fxiaoke.crm.data.QueryInfoFilter;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.service.impl.AbstractCrmServiceImpl;
import com.chylee.fxiaoke.xjl.event.data.object.Object_h18X2__c;
import com.chylee.fxiaoke.xjl.service.FxkBaoxiaoDjService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FxkBaoxiaoDjServiceImpl extends AbstractCrmServiceImpl implements FxkBaoxiaoDjService {
    public FxkBaoxiaoDjServiceImpl(AccessTokenManager accessTokenManager) {
        super(accessTokenManager);
    }

    @Override
    public Object_h18X2__c getSuccess(String baoxiao) throws CrmApiException {
        List<QueryInfoFilter> queryInfoFilters = new ArrayList<>();
        queryInfoFilters.add(new QueryInfoFilter("field_wqn9g__c", baoxiao));
        queryInfoFilters.add(new QueryInfoFilter("field_w9nvZ__c", true));
        List<Object_h18X2__c> result = queryDataObj("object_h18X2__c", queryInfoFilters, true, Object_h18X2__c.class);
        return result == null || result.isEmpty() ? null : result.get(0);
    }

    @Override
    public void save(String baoxiao, String db, String dh, String error, boolean result) throws CrmApiException {
        Object_h18X2__c object = new Object_h18X2__c();
        object.setDataObjectApiName("object_h18X2__c");
        object.setOwner(Arrays.asList(getAdminOpenId()));
        object.setField_IM32I__c(baoxiao);
        object.setField_1r0bN__c(db);
        object.setField_g6j6m__c(dh);
        object.setField_8amY2__c(error);
        object.setField_w9nvZ__c(result);
        updateDataObj(object, null, true, true);
    }
}
