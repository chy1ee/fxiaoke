package com.chylee.fxiaoke.xjl.service;

import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.xjl.event.data.object.Object_1sCfv__c;

/**
 * 报价单对接结果
 */
public interface FxkBaojiadanDjService {
    Object_1sCfv__c getSuccess(String baojiadan) throws CrmApiException;
    void upload() throws CrmApiException;
    void save(String baojiadan, String db, String dh, String error, boolean result) throws CrmApiException;
}
