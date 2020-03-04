package com.chylee.fxiaoke.service;

import com.chylee.fxiaoke.common.event.fxiaoke.data.object.AccountObj;
import com.chylee.fxiaoke.common.exception.CrmApiException;

import java.util.List;

public interface TestAccountService {
    List<AccountObj> getAccounts() throws CrmApiException;
    void updateAccount();
}
