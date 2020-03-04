package com.chylee.fxiaoke.xjl.service;

import com.chylee.fxiaoke.common.event.fxiaoke.data.object.Object_okom1__c;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.exception.CrmDataException;

public interface FxkObjectOkom1Service {
    void save(Object_okom1__c pz) throws CrmApiException;
    Object_okom1__c loadById(String id) throws CrmDataException, CrmApiException;
}
