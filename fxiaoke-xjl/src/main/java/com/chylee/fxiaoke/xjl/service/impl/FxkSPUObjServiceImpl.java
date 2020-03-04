package com.chylee.fxiaoke.xjl.service.impl;

import com.chylee.fxiaoke.common.api.AccessTokenManager;
import com.chylee.fxiaoke.common.event.fxiaoke.data.object.SPUObj;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.exception.CrmDataException;
import com.chylee.fxiaoke.common.service.impl.AbstractCrmServiceImpl;
import com.chylee.fxiaoke.xjl.service.FxkSPUObjService;
import org.springframework.stereotype.Service;

@Service
public class FxkSPUObjServiceImpl extends AbstractCrmServiceImpl implements FxkSPUObjService {
    public FxkSPUObjServiceImpl(AccessTokenManager accessTokenManager) {
        super(accessTokenManager);
    }

    @Override
    public SPUObj loadById(String id) throws CrmApiException, CrmDataException {
        SPUObj spuObj = loadDataObj("SPUObj", id, false, SPUObj.class);
        if (spuObj == null)
            throw new CrmDataException("SPUObj不能为空["+id+"]");
        return spuObj;
    }
}
