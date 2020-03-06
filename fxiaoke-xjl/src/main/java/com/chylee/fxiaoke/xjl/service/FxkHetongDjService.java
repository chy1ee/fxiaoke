package com.chylee.fxiaoke.xjl.service;

import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.xjl.event.data.object.Object_qlu3s__c;

public interface FxkHetongDjService {
    Object_qlu3s__c getSuccess(String hetong) throws CrmApiException;
    void upload() throws CrmApiException;
    void save(String hetong, String db, String dh, String error, boolean result) throws CrmApiException;
}
