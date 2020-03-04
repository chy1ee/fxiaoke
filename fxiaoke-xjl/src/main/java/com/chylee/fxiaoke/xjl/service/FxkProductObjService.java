package com.chylee.fxiaoke.xjl.service;

import com.chylee.fxiaoke.common.event.fxiaoke.data.object.ProductObj;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.exception.CrmDataException;

import java.util.List;

public interface FxkProductObjService {
    ProductObj loadById(String id) throws CrmApiException;
    List<ProductObj> listByProductCode(String productCode) throws CrmDataException, CrmApiException;
}
