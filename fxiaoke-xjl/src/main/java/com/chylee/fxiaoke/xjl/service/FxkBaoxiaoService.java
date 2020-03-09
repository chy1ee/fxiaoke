package com.chylee.fxiaoke.xjl.service;

import com.chylee.fxiaoke.xjl.event.data.object.Object_okom1__c;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.exception.CrmDataException;

public interface FxkBaoxiaoService {
    void update(String dataId, String db, String dh) throws CrmApiException;
    Object_okom1__c loadById(String id) throws CrmDataException, CrmApiException;
}
