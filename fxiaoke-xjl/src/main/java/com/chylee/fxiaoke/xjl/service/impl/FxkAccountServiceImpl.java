package com.chylee.fxiaoke.xjl.service.impl;

import com.chylee.fxiaoke.common.api.AccessTokenManager;
import com.chylee.fxiaoke.common.event.fxiaoke.crm.data.QueryInfoFilter;
import com.chylee.fxiaoke.xjl.event.data.object.AccountObj;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.exception.CrmDataException;
import com.chylee.fxiaoke.common.service.impl.AbstractCrmServiceImpl;
import com.chylee.fxiaoke.xjl.service.FxkAccountService;
import com.chylee.fxiaoke.common.service.FxkObjectService;
import com.chylee.fxiaoke.xjl.service.FxkPersonnelObjService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FxkAccountServiceImpl extends AbstractCrmServiceImpl implements FxkAccountService {
    private final FxkObjectService objectService;
    private final FxkPersonnelObjService personnelObjService;

    public FxkAccountServiceImpl(AccessTokenManager accessTokenManager, FxkObjectService objectService,
                                 FxkPersonnelObjService personnelObjService) {
        super(accessTokenManager);
        this.objectService = objectService;
        this.personnelObjService = personnelObjService;
    }

    @Override
    public AccountObj loadByKhbh(String khbh) throws CrmDataException, CrmApiException {
        if(khbh == null)
            throw new CrmDataException("客户编号为空");

        List<QueryInfoFilter> queryInfoFilters = new ArrayList<>();
        queryInfoFilters.add(new QueryInfoFilter("field_AlGoN__c", khbh.trim()));
        List<AccountObj> accountObjs = queryDataObj("AccountObj", queryInfoFilters, false, AccountObj.class);
        if (accountObjs == null || accountObjs.isEmpty())
            throw new CrmDataException("客户不存在khbh=" + khbh);

        if(accountObjs.size() != 1)
            throw new CrmDataException("客户编码重复khbh=" + khbh);

        AccountObj accountObj = accountObjs.get(0);

        return handleSelectOne(accountObj);
    }

    @Override
    public AccountObj loadById(String dataId) throws CrmDataException, CrmApiException {
        if (dataId == null)
            throw new CrmDataException("accountID为空");

        AccountObj accountObj = this.loadDataObj("AccountObj", dataId, false, AccountObj.class);
        if (accountObj == null)
            throw new CrmDataException("客户不存：dataId=" + dataId);

        return handleSelectOne(accountObj);
    }

    private AccountObj handleSelectOne(AccountObj accountObj) throws CrmDataException {
        accountObj.setYwry(personnelObjService.getNumberByOpenId(accountObj.getOwner().get(0)));

        //客户名称首个拼音字母
        if (accountObj.getField_p3Bv1__c() != null) {
            accountObj.setField_p3Bv1__c(
                    objectService.selectOneMap("AccountObj", "field_p3Bv1__c").loadByLabel(accountObj.getField_p3Bv1__c()));
        }

        //客户所属区域代码
        if (accountObj.getField_PlekP__c() != null) {
            accountObj.setField_PlekP__c(
                    objectService.selectOneMap("AccountObj", "field_PlekP__c").loadByLabel(accountObj.getField_PlekP__c()));
        }

        return accountObj;
    }

    @Override
    public void updateKhbhById(String dataId, String khbh) throws CrmApiException {
        AccountObj accountObjToUpdate = new AccountObj();
        accountObjToUpdate.setDataObjectApiName("AccountObj");
        accountObjToUpdate.set_id(dataId);
        accountObjToUpdate.setField_AlGoN__c(khbh);
        try {
            updateDataObj(accountObjToUpdate);
        } catch (CrmApiException e) {
            throw new CrmApiException(e.getMessage() + "[" + khbh + "]");
        }
    }
}
