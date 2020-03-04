package com.chylee.fxiaoke.xjl.service;

import com.chylee.fxiaoke.common.event.fxiaoke.data.object.AccountAddrObj;
import com.chylee.fxiaoke.common.exception.CrmApiException;

import java.util.List;

public interface FxkAccountAddrService {
    List<AccountAddrObj> listAccountAddrByAccountId(String accountId) throws CrmApiException;
}
