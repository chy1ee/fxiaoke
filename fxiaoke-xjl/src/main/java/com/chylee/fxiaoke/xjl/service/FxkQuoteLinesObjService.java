package com.chylee.fxiaoke.xjl.service;

import com.chylee.fxiaoke.xjl.event.data.object.QuoteLinesObj;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.exception.CrmDataException;

import java.util.List;

public interface FxkQuoteLinesObjService {
    List<QuoteLinesObj> listByQuoteId(String quoteId) throws CrmApiException, CrmDataException;
}
