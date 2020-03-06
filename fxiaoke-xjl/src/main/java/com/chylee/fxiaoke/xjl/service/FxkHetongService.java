package com.chylee.fxiaoke.xjl.service;

import com.chylee.fxiaoke.xjl.event.data.object.Object_snPZx__c;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.exception.CrmDataException;
import com.chylee.fxiaoke.common.exception.ErpDataException;

import java.util.List;

public interface FxkHetongService {
    void save(Object_snPZx__c ht) throws CrmApiException;
    Object_snPZx__c loadById(String id) throws CrmApiException, CrmDataException, ErpDataException;
    List<Object_snPZx__c> listByDbAndDh(String db, String dh) throws CrmApiException, CrmDataException;
}
