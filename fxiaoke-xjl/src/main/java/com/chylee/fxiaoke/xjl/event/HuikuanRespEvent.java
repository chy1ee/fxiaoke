package com.chylee.fxiaoke.xjl.event;

import com.chylee.fxiaoke.common.event.ResponseEvent;
import com.chylee.fxiaoke.common.event.fxiaoke.data.object.OrderPaymentObj;
import com.chylee.fxiaoke.common.event.fxiaoke.data.object.PaymentObj;

import java.util.List;

public class HuikuanRespEvent extends ResponseEvent {
    private PaymentObj paymentObj;
    private List<OrderPaymentObj> orderPaymentObjs;

    public PaymentObj getPaymentObj() {
        return paymentObj;
    }

    public void setPaymentObj(PaymentObj paymentObj) {
        this.paymentObj = paymentObj;
    }

    public List<OrderPaymentObj> getOrderPaymentObjs() {
        return orderPaymentObjs;
    }

    public void setOrderPaymentObjs(List<OrderPaymentObj> orderPaymentObjs) {
        this.orderPaymentObjs = orderPaymentObjs;
    }
}
