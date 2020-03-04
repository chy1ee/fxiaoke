package com.chylee.fxiaoke.xjl.service;

import com.chylee.fxiaoke.common.event.ResponseEvent;
import com.chylee.fxiaoke.xjl.event.PingzhengReqEvent;

public interface ErpPingzhengService {
    ResponseEvent save(PingzhengReqEvent reqEvent);
}
