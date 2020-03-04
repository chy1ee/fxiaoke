package com.chylee.fxiaoke.xjl.service.impl;

import com.chylee.fxiaoke.common.api.AccessTokenManager;
import com.chylee.fxiaoke.common.event.fxiaoke.data.object.OrderPaymentObj;
import com.chylee.fxiaoke.common.event.fxiaoke.data.object.PaymentObj;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.service.impl.AbstractCrmServiceImpl;
import com.chylee.fxiaoke.xjl.service.FxkPaymentObjService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FxkPaymentObjServiceImpl extends AbstractCrmServiceImpl implements FxkPaymentObjService {
    public FxkPaymentObjServiceImpl(AccessTokenManager accessTokenManager) {
        super(accessTokenManager);
    }

    @Override
    public void save(PaymentObj paymentObj, Map<String, List<OrderPaymentObj>> detail) throws CrmApiException {
        updateDataObj(paymentObj, detail, false, true);
    }
}
