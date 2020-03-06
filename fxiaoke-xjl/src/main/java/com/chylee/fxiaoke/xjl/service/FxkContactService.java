package com.chylee.fxiaoke.xjl.service;

import com.chylee.fxiaoke.xjl.event.data.object.ContactObj;
import com.chylee.fxiaoke.common.exception.CrmApiException;

import java.util.List;

public interface FxkContactService {
    void save(ContactObj contactObj) throws CrmApiException;
    ContactObj loadById(String id) throws CrmApiException;
    List<ContactObj> listByAccounrIdAndName(String accountId, String name) throws CrmApiException;
}
