package com.chylee.fxiaoke.xjl.service;

import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.xjl.event.data.object.Object_h18X2__c;

public interface FxkBaoxiaoDjService {
    Object_h18X2__c getSuccess(String baoxiao) throws CrmApiException;
    void save(String baojiadan, String db, String dh, String error, boolean result) throws CrmApiException;
}
