package com.chylee.fxiaoke.xjl.service.impl;

import com.chylee.fxiaoke.common.api.AccessTokenManager;
import com.chylee.fxiaoke.xjl.event.data.object.QuoteObj;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.exception.CrmDataException;
import com.chylee.fxiaoke.common.service.impl.AbstractCrmServiceImpl;
import com.chylee.fxiaoke.common.service.FxkObjectService;
import com.chylee.fxiaoke.xjl.service.FxkQuoteObjService;
import org.springframework.stereotype.Service;

@Service
public class FxkQuoteObjServiceImpl extends AbstractCrmServiceImpl implements FxkQuoteObjService {
    private final FxkObjectService objectService;

    public FxkQuoteObjServiceImpl(AccessTokenManager accessTokenManager, FxkObjectService objectService) {
        super(accessTokenManager);
        this.objectService = objectService;
    }

    @Override
    public void update(String dataId, String db, String dh) throws CrmApiException {
        QuoteObj quoteObjToUpdate = new QuoteObj();
        quoteObjToUpdate.setDataObjectApiName("QuoteObj");
        quoteObjToUpdate.set_id(dataId);
        quoteObjToUpdate.setField_kq20e__c(db);
        quoteObjToUpdate.setField_SS32r__c(dh);
        updateDataObj(quoteObjToUpdate);
    }

    @Override
    public QuoteObj loadById(String id) throws CrmApiException, CrmDataException {
        QuoteObj quoteObj = loadDataObj("QuoteObj", id, false, QuoteObj.class);
        if (quoteObj == null)
            throw new CrmDataException("报价单不存在["+id+"]");

        //报价币种
        if (quoteObj.getField_FrJQF__c() != null) {
            quoteObj.setField_FrJQF__c(
                    objectService.selectOneMap("QuoteObj", "field_FrJQF__c").loadByLabel(quoteObj.getField_FrJQF__c()));
        }

        return quoteObj;
    }
}
