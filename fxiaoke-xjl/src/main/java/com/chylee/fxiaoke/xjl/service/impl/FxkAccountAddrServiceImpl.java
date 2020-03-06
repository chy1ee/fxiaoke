package com.chylee.fxiaoke.xjl.service.impl;

import com.chylee.fxiaoke.common.api.AccessTokenManager;
import com.chylee.fxiaoke.common.event.fxiaoke.crm.data.QueryInfoFilter;
import com.chylee.fxiaoke.xjl.event.data.object.AccountAddrObj;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.service.impl.AbstractCrmServiceImpl;
import com.chylee.fxiaoke.xjl.service.FxkAccountAddrService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FxkAccountAddrServiceImpl extends AbstractCrmServiceImpl implements FxkAccountAddrService {
    public FxkAccountAddrServiceImpl(AccessTokenManager accessTokenManager) {
        super(accessTokenManager);
    }

    @Override
    public List<AccountAddrObj> listAccountAddrByAccountId(String accountId) throws CrmApiException {
        List<QueryInfoFilter> queryInfoFilters = new ArrayList<>();
        queryInfoFilters.add(new QueryInfoFilter("account_id", accountId));
        return queryDataObj("AccountAddrObj", queryInfoFilters,
                false, AccountAddrObj.class);
    }
}
