package com.chylee.fxiaoke.common.service;

import com.chylee.fxiaoke.common.event.ResponseEvent;
import com.chylee.fxiaoke.common.event.config.ConfigApiReqEvent;

public interface ConfigApiService {
    ConfigApiReqEvent getConfig();
    ResponseEvent saveConfig(ConfigApiReqEvent reqEvent);
}
