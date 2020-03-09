package com.chylee.fxiaoke.xjl.service;

import com.chylee.fxiaoke.xjl.event.data.object.QuoteObj;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.exception.CrmDataException;

public interface FxkQuoteObjService {
    void update(String dataId, String db, String dh) throws CrmApiException;
    QuoteObj loadById(String id) throws CrmApiException, CrmDataException;
}
