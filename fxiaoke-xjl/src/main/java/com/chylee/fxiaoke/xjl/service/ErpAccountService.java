package com.chylee.fxiaoke.xjl.service;

import com.chylee.fxiaoke.xjl.event.AccountReqEvent;
import com.chylee.fxiaoke.xjl.event.AccountRespEvent;

public interface ErpAccountService {
    AccountRespEvent save(AccountReqEvent reqEvent);
}
