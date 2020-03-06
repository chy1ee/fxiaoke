package com.chylee.fxiaoke.xjl.event;

import com.chylee.fxiaoke.common.event.ResponseEvent;
import com.chylee.fxiaoke.xjl.event.data.object.DeviceObj;

import java.util.List;

public class ShebeiRespEvent extends ResponseEvent {
    private List<DeviceObj> deviceObjs;

    public List<DeviceObj> getDeviceObjs() {
        return deviceObjs;
    }

    public void setDeviceObjs(List<DeviceObj> deviceObjs) {
        this.deviceObjs = deviceObjs;
    }
}
