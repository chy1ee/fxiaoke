package com.chylee.fxiaoke.common.service;

import com.chylee.fxiaoke.common.event.ResponseEvent;
import com.chylee.fxiaoke.common.event.config.ConfigAdminReqEvent;

import java.util.List;

public interface ConfigAdminService {
    int getStatus();
    List<String> getAdminOpenIds();
    void cleanStatus();
    void cleanAdminOpenIds();
    ResponseEvent saveConfig(ConfigAdminReqEvent reqEvent);
}
