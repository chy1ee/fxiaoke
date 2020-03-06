package com.chylee.fxiaoke.xjl.service;

import com.chylee.fxiaoke.xjl.event.data.object.DeviceObj;
import com.chylee.fxiaoke.common.exception.CrmApiException;

public interface FxkDeviceObjService {
    void save(DeviceObj deviceObj) throws CrmApiException;
}
