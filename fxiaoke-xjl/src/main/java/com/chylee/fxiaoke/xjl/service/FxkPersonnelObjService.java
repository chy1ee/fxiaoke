package com.chylee.fxiaoke.xjl.service;

import com.chylee.fxiaoke.common.event.fxiaoke.data.object.PersonnelObj;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.exception.CrmDataException;

import java.util.List;

public interface FxkPersonnelObjService {
    PersonnelObj loadByYgbh(String ygbh) throws CrmDataException, CrmApiException;
    PersonnelObj loadById(String dataId) throws CrmDataException, CrmApiException;
    String getNumberByOpenId(String openId) throws CrmDataException;
    List<String> getOwner(String ygbh) throws CrmApiException;
}
