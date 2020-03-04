package com.chylee.fxiaoke.common.service;

import com.chylee.fxiaoke.common.model.SysConfig;

public interface SysConfigService {
    SysConfig loadByType(int type);
}
