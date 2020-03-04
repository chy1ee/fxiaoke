package com.chylee.fxiaoke.xjl.service.impl;

import com.chylee.fxiaoke.common.api.AccessTokenManager;
import com.chylee.fxiaoke.common.event.fxiaoke.crm.data.QueryInfoFilter;
import com.chylee.fxiaoke.common.event.fxiaoke.data.object.QuoteLinesObj;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.exception.CrmDataException;
import com.chylee.fxiaoke.common.service.impl.AbstractCrmServiceImpl;
import com.chylee.fxiaoke.xjl.service.FxkQuoteLinesObjService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FxkQuoteLinesObjServiceImpl extends AbstractCrmServiceImpl implements FxkQuoteLinesObjService {
    public FxkQuoteLinesObjServiceImpl(AccessTokenManager accessTokenManager) {
        super(accessTokenManager);
    }

    @Override
    public List<QuoteLinesObj> listByQuoteId(String quoteId) throws CrmApiException, CrmDataException {
        List<QueryInfoFilter> queryInfoFilters = new ArrayList<>();
        queryInfoFilters.add(new QueryInfoFilter("quote_id", quoteId));
        List<QuoteLinesObj> quoteLinesObjs = queryDataObj("QuoteLinesObj", queryInfoFilters,
                false, QuoteLinesObj.class);

        if (quoteLinesObjs == null || quoteLinesObjs.isEmpty())
            throw new CrmDataException("报价单明细不能为空["+quoteId+"]");

        return quoteLinesObjs;
    }
}
