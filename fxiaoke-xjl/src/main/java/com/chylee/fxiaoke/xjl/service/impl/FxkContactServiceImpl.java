package com.chylee.fxiaoke.xjl.service.impl;

import com.chylee.fxiaoke.common.api.AccessTokenManager;
import com.chylee.fxiaoke.common.event.fxiaoke.crm.data.QueryInfoFilter;
import com.chylee.fxiaoke.common.event.fxiaoke.crm.data.QueryInfoOrder;
import com.chylee.fxiaoke.xjl.event.data.object.ContactObj;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.service.impl.AbstractCrmServiceImpl;
import com.chylee.fxiaoke.xjl.service.FxkContactService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class FxkContactServiceImpl extends AbstractCrmServiceImpl implements FxkContactService {
    public FxkContactServiceImpl(AccessTokenManager accessTokenManager) {
        super(accessTokenManager);
    }

    @Override
    public void save(ContactObj contactObj) throws CrmApiException {
        updateDataObj(contactObj, null, false, true);
    }

    @Override
    public ContactObj loadById(String id) throws CrmApiException {
        return loadDataObj("ContactObj", id,
                false, ContactObj.class);
    }

    @Override
    public List<ContactObj> listByAccounrIdAndName(String accountId, String name) throws CrmApiException {
        Debug("***获取联系人信息[{}][{}]", accountId, name);
        List<QueryInfoFilter> queryInfoFilters = new ArrayList<>();
        queryInfoFilters.add(new QueryInfoFilter("account_id", accountId));
        if (name != null)
            queryInfoFilters.add(new QueryInfoFilter("name", name));

        List<ContactObj> contactObjs = queryDataObj("ContactObj", queryInfoFilters,
                false, ContactObj.class);

        Collections.sort(contactObjs);

        return contactObjs;
    }
}
