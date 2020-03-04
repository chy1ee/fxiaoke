package com.chylee.fxiaoke.common.event.fxiaoke.data.object;

import java.util.ArrayList;
import java.util.List;

/**
 * 回款
 */
public class PaymentObj extends DataObject {
    private String account_id;  //客户* 关联客户ID
    private String payment_time;    //回款日期*
    private String remark;  //备注
    private String payment_term;    //回款方式
    private String notification_time;   //提醒日期
    private String field_MDApg__c;  //单别
    private String field_Ueasn__c;  //单号
    private List<String> owner;     //*

    public String getPayment_time() {
        return payment_time;
    }

    public void setPayment_time(String payment_time) {
        this.payment_time = payment_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPayment_term() {
        return payment_term;
    }

    public void setPayment_term(String payment_term) {
        this.payment_term = payment_term;
    }

    public String getNotification_time() {
        return notification_time;
    }

    public void setNotification_time(String notification_time) {
        this.notification_time = notification_time;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getField_MDApg__c() {
        return field_MDApg__c;
    }

    public void setField_MDApg__c(String field_MDApg__c) {
        this.field_MDApg__c = field_MDApg__c;
    }

    public String getField_Ueasn__c() {
        return field_Ueasn__c;
    }

    public void setField_Ueasn__c(String field_Ueasn__c) {
        this.field_Ueasn__c = field_Ueasn__c;
    }

    public List<String> getOwner() {
        return owner;
    }

    public void setOwner(List<String> owner) {
        this.owner = owner;
    }

    public void addOwner(String owner) {
        if(this.owner == null)
            this.owner = new ArrayList<>();
        this.owner.add(owner);
    }
}
