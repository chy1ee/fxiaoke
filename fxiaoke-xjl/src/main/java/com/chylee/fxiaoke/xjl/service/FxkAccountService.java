package com.chylee.fxiaoke.xjl.service;

import com.chylee.fxiaoke.xjl.event.data.object.AccountObj;
import com.chylee.fxiaoke.common.exception.AccessTokenException;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.exception.CrmDataException;

public interface FxkAccountService {
    AccountObj loadByKhbh(String khbh) throws CrmDataException, CrmApiException;
    AccountObj loadById(String id) throws CrmDataException, CrmApiException;
    void updateKhbhById(String id, String khbh) throws CrmApiException;
}
