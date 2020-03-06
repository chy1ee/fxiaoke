/**
 * 合同明细
 */
package com.chylee.fxiaoke.xjl.service.impl;

import com.chylee.fxiaoke.common.api.AccessTokenManager;
import com.chylee.fxiaoke.common.event.fxiaoke.crm.data.QueryInfoFilter;
import com.chylee.fxiaoke.xjl.event.data.object.Object_47F7O__c;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.exception.CrmDataException;
import com.chylee.fxiaoke.common.service.impl.AbstractCrmServiceImpl;
import com.chylee.fxiaoke.xjl.service.FxkHetongMxService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FxkHetongMxServiceImpl extends AbstractCrmServiceImpl implements FxkHetongMxService {
    public FxkHetongMxServiceImpl(AccessTokenManager accessTokenManager) {
        super(accessTokenManager);
    }

    @Override
    public List<Object_47F7O__c> listByHtId(String htId) throws CrmApiException, CrmDataException {
        List<QueryInfoFilter> queryInfoFilters = new ArrayList<>();
        queryInfoFilters.add(new QueryInfoFilter("field_tyl2o__c", htId));
        List<Object_47F7O__c> result = queryDataObj("object_47F7O__c", queryInfoFilters,
                true, Object_47F7O__c.class);

        if (result == null || result.isEmpty())
            throw new CrmDataException("合同明细不能为空["+htId+"]");

        return result;
    }
}
