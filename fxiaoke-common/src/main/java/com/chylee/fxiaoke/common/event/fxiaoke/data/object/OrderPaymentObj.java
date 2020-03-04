package com.chylee.fxiaoke.common.event.fxiaoke.data.object;

import java.math.BigDecimal;

/**
 * 回款明细
 */
public class OrderPaymentObj extends DataObject{
    private String remark;  //备注
    private String payment_id;  //汇款单号 主表ID
    private String order_id;    //订单ID
    private BigDecimal payment_amount;  //本次回款金额
    private String order_lb;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public BigDecimal getPayment_amount() {
        return payment_amount;
    }

    public void setPayment_amount(BigDecimal payment_amount) {
        this.payment_amount = payment_amount;
    }

    public String getOrder_lb() {
        return order_lb;
    }

    public void setOrder_lb(String order_lb) {
        this.order_lb = order_lb;
    }
}
