package com.chylee.fxiaoke.xjl.service;

import com.chylee.fxiaoke.common.event.fxiaoke.data.object.PriceBookProductObj;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.exception.CrmDataException;

import java.util.List;

public interface FxkPriceBookProductObjService {
    String PRICEBOOK_ID = "5d92fb98bc1b350001934616";

    List<PriceBookProductObj> listByProductCode(String product_code) throws CrmApiException, CrmDataException;
}
