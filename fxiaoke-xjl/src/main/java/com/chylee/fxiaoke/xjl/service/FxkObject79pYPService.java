package com.chylee.fxiaoke.xjl.service;

import com.chylee.fxiaoke.common.event.fxiaoke.data.object.Object_79pYP__c;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.exception.CrmDataException;

import java.util.List;

public interface FxkObject79pYPService {
    List<Object_79pYP__c> listByPzId(String pzId) throws CrmApiException, CrmDataException;
}
