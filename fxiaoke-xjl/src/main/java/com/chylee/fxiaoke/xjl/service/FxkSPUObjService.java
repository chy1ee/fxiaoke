package com.chylee.fxiaoke.xjl.service;

import com.chylee.fxiaoke.common.event.fxiaoke.data.object.SPUObj;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.exception.CrmDataException;

public interface FxkSPUObjService {
    SPUObj loadById(String id) throws CrmApiException, CrmDataException;
}
