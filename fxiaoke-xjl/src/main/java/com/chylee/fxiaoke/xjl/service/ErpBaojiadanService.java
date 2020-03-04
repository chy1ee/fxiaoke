package com.chylee.fxiaoke.xjl.service;

import com.chylee.fxiaoke.xjl.event.AccountRespEvent;
import com.chylee.fxiaoke.xjl.event.BaojiadanReqEvent;

public interface ErpBaojiadanService {
    AccountRespEvent save(BaojiadanReqEvent reqEvent);
}
