package com.chylee.fxiaoke.service;

import com.chylee.fxiaoke.common.event.ResponseEvent;

public interface FxkDataService {
    ResponseEvent sendTestMessage();
    ResponseEvent getV2(String api, String id, boolean custom);
    ResponseEvent queryV2(String api, String name, String value, boolean custom);
}
