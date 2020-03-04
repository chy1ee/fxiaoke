package com.chylee.fxiaoke.xjl.service;

import com.chylee.fxiaoke.xjl.event.AccountRespEvent;
import com.chylee.fxiaoke.xjl.event.HetongReqEvent;

public interface ErpHetongService {
    AccountRespEvent save(HetongReqEvent reqEvent);
}
