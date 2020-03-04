package com.chylee.fxiaoke.xjl.service;

import com.chylee.fxiaoke.common.event.fxiaoke.data.object.Object_47F7O__c;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.exception.CrmDataException;

import java.util.List;

public interface FxkObject47F7OService {
    List<Object_47F7O__c> listByHtId(String hetongId) throws CrmApiException, CrmDataException;
}
