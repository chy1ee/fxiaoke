package com.chylee.fxiaoke.xjl.service;

import com.chylee.fxiaoke.xjl.event.data.object.Object_47F7O__c;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.exception.CrmDataException;

import java.util.List;

public interface FxkHetongMxService {
    List<Object_47F7O__c> listByHtId(String hetongId) throws CrmApiException, CrmDataException;
}
