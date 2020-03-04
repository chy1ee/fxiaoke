package com.chylee.fxiaoke.xjl.service.impl;

import com.chylee.fxiaoke.common.api.AccessTokenManager;
import com.chylee.fxiaoke.common.event.fxiaoke.data.object.Object_okom1__c;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.exception.CrmDataException;
import com.chylee.fxiaoke.common.service.impl.AbstractCrmServiceImpl;
import com.chylee.fxiaoke.xjl.service.FxkObjectOkom1Service;
import org.springframework.stereotype.Service;

@Service
public class FxkObjectOkom1ServiceImpl extends AbstractCrmServiceImpl implements FxkObjectOkom1Service {
    public FxkObjectOkom1ServiceImpl(AccessTokenManager accessTokenManager) {
        super(accessTokenManager);
    }

    @Override
    public void save(Object_okom1__c pz) throws CrmApiException {
        updateDataObj(pz, null , true, false);
    }

    @Override
    public Object_okom1__c loadById(String id) throws CrmDataException, CrmApiException {
        Object_okom1__c pz = this.loadDataObj("object_okom1__c", id,
                true, Object_okom1__c.class);

        if (pz == null)
            throw new CrmDataException("凭证不存在["+id+"]");

        return pz;
    }
}
