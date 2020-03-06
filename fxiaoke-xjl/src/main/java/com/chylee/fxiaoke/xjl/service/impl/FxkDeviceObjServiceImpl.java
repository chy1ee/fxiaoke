package com.chylee.fxiaoke.xjl.service.impl;

import com.chylee.fxiaoke.common.api.AccessTokenManager;
import com.chylee.fxiaoke.xjl.event.data.object.DeviceObj;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.service.impl.AbstractCrmServiceImpl;
import com.chylee.fxiaoke.xjl.service.FxkDeviceObjService;
import org.springframework.stereotype.Service;

@Service
public class FxkDeviceObjServiceImpl extends AbstractCrmServiceImpl implements FxkDeviceObjService {
    public FxkDeviceObjServiceImpl(AccessTokenManager accessTokenManager) {
        super(accessTokenManager);
    }

    @Override
    public void save(DeviceObj deviceObj) throws CrmApiException {
        updateDataObj(deviceObj, null, false, true);
    }
}
