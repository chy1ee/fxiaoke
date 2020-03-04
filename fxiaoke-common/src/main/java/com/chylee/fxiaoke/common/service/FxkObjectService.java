package com.chylee.fxiaoke.common.service;

import com.chylee.fxiaoke.common.cache.SelectOneCacher;
import com.chylee.fxiaoke.common.event.fxiaoke.crm.object.ObjectRespEvent;
import com.chylee.fxiaoke.common.exception.AccessTokenException;

public interface FxkObjectService {
    ObjectRespEvent list() throws AccessTokenException;
    com.chylee.fxiaoke.common.event.fxiaoke.ObjectRespEvent describe2(String apiName) throws AccessTokenException;
    ObjectRespEvent describe(String apiName) throws AccessTokenException;
    SelectOneCacher selectOneMap(String objName, String apiName);
    void clearCacher(String objName, String apiName);
}
