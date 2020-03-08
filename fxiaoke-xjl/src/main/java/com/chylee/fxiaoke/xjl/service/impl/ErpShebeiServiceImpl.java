package com.chylee.fxiaoke.xjl.service.impl;

import com.chylee.fxiaoke.xjl.event.data.object.DeviceObj;
import com.chylee.fxiaoke.xjl.event.ErpJobRespEvent;
import com.chylee.fxiaoke.xjl.event.ShebeiRespEvent;
import com.chylee.fxiaoke.xjl.mapper.CoptgMapper;
import com.chylee.fxiaoke.xjl.mapper.CopthMapper;
import com.chylee.fxiaoke.xjl.model.Coptg;
import com.chylee.fxiaoke.xjl.model.Copth;
import com.chylee.fxiaoke.xjl.service.ErpShebeiService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ErpShebeiServiceImpl implements ErpShebeiService {

    private final CoptgMapper coptgMapper;
    private final CopthMapper copthMapper;

    public ErpShebeiServiceImpl(CoptgMapper coptgMapper, CopthMapper copthMapper) {
        this.coptgMapper = coptgMapper;
        this.copthMapper = copthMapper;
    }

    @Override
    public ErpJobRespEvent list(String startTime, String endTime) {
        ErpJobRespEvent respEvent = new ErpJobRespEvent();
        try {
            List<Coptg> data = coptgMapper.list(startTime, endTime);
            respEvent.setDataIdList(
                    data.stream().map(coptg -> coptg.getTG001() + "-" + coptg.getTG002())
                            .collect(Collectors.toList()));
        }
        catch(Exception e) {
            respEvent.setError(-1, e.getMessage());
        }

        return respEvent;
    }

    @Override
    public ShebeiRespEvent loadShebei(String db, String dh) {
        ShebeiRespEvent respEvent = new ShebeiRespEvent();

        List<Copth> copths = copthMapper.list(db, dh);
        if(copths != null && !copths.isEmpty()) {
            respEvent.setDeviceObjs(
                    copths.stream().map(copth -> toDeviceObj(copth)).collect(Collectors.toList()));
        }

        return respEvent;
    }

    private DeviceObj toDeviceObj(Copth copth) {
        DeviceObj obj = new DeviceObj();

        obj.setDevice_code(copth.getTH017());
        obj.setField_nA2gP__c(copth.getTG003());
        obj.setDb(copth.getTD017());
        obj.setDh(copth.getTD018());
        obj.setTG006(copth.getTG006());

        return obj;
    }
}
