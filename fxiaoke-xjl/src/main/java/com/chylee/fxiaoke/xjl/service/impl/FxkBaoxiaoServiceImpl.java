package com.chylee.fxiaoke.xjl.service.impl;

import com.chylee.fxiaoke.common.api.AccessTokenManager;
import com.chylee.fxiaoke.xjl.event.data.object.Object_okom1__c;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.exception.CrmDataException;
import com.chylee.fxiaoke.common.service.impl.AbstractCrmServiceImpl;
import com.chylee.fxiaoke.xjl.service.FxkBaoxiaoService;
import org.springframework.stereotype.Service;

@Service
public class FxkBaoxiaoServiceImpl extends AbstractCrmServiceImpl implements FxkBaoxiaoService {
    public FxkBaoxiaoServiceImpl(AccessTokenManager accessTokenManager) {
        super(accessTokenManager);
    }

    @Override
    public void update(String dataId, String db, String dh) throws CrmApiException {
        Object_okom1__c toUpdate = new Object_okom1__c();
        toUpdate.setDataObjectApiName("object_okom1__c");
        toUpdate.set_id(dataId);
        toUpdate.setField_EHyt1__c(db);
        toUpdate.setField_8GijD__c(dh);
        updateDataObj(toUpdate, null , true, false);
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
