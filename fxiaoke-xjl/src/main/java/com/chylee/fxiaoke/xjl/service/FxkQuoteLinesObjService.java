package com.chylee.fxiaoke.xjl.service;

import com.chylee.fxiaoke.common.event.fxiaoke.data.object.QuoteLinesObj;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.exception.CrmDataException;

import java.util.List;

public interface FxkQuoteLinesObjService {
    List<QuoteLinesObj> listByQuoteId(String quoteId) throws CrmApiException, CrmDataException;
}
