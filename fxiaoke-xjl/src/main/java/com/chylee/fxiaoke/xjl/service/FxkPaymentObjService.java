package com.chylee.fxiaoke.xjl.service;

import com.chylee.fxiaoke.xjl.event.data.object.OrderPaymentObj;
import com.chylee.fxiaoke.xjl.event.data.object.PaymentObj;
import com.chylee.fxiaoke.common.exception.CrmApiException;

import java.util.List;
import java.util.Map;

public interface FxkPaymentObjService {
    void save(PaymentObj paymentObj, Map<String, List<OrderPaymentObj>> detail) throws CrmApiException;
}
