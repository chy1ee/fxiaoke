package com.chylee.fxiaoke.xjl.service.impl;

import com.chylee.fxiaoke.common.api.AccessTokenManager;
import com.chylee.fxiaoke.common.event.fxiaoke.crm.data.QueryInfoFilter;
import com.chylee.fxiaoke.common.event.fxiaoke.data.object.Object_79pYP__c;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.exception.CrmDataException;
import com.chylee.fxiaoke.common.service.impl.AbstractCrmServiceImpl;
import com.chylee.fxiaoke.xjl.service.FxkObject79pYPService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FxkObject79pYPServiceImpl extends AbstractCrmServiceImpl implements FxkObject79pYPService {
    public FxkObject79pYPServiceImpl(AccessTokenManager accessTokenManager) {
        super(accessTokenManager);
    }

    @Override
    public List<Object_79pYP__c> listByPzId(String pzId) throws CrmApiException, CrmDataException {
        List<QueryInfoFilter> queryInfoFilters = new ArrayList<>();
        queryInfoFilters.add(new QueryInfoFilter("field_3Cng6__c", pzId));
        List<Object_79pYP__c> pzmxs = this.queryDataObj("object_79pYP__c", queryInfoFilters,
                true, Object_79pYP__c.class);

        if(pzmxs == null || pzmxs.isEmpty())
            throw new CrmDataException("凭证明细不能为空["+pzId+"]");

        return pzmxs;
    }
}
