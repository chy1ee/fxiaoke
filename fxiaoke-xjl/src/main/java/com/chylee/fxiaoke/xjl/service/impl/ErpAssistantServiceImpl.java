package com.chylee.fxiaoke.xjl.service.impl;

import com.chylee.fxiaoke.xjl.event.DbnowRespEvent;
import com.chylee.fxiaoke.common.util.DateUtils;
import com.chylee.fxiaoke.xjl.service.ErpAssistantService;
import com.chylee.fxiaoke.xjl.mapper.NowMapper;
import org.springframework.stereotype.Service;

@Service
public class ErpAssistantServiceImpl implements ErpAssistantService {

    private final NowMapper nowMapper;

    public ErpAssistantServiceImpl(NowMapper nowMapper) {
        this.nowMapper = nowMapper;
    }

    @Override
    public DbnowRespEvent dbnow() {
        DbnowRespEvent respEvent = new DbnowRespEvent();
        respEvent.setNow(nowMapper.now());
        return respEvent;
    }
}
