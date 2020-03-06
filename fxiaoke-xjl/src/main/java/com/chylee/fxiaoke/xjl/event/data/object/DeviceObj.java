package com.chylee.fxiaoke.xjl.event.data.object;

import com.chylee.fxiaoke.common.event.fxiaoke.data.object.DataObject;

import java.util.List;

public class DeviceObj extends DataObject {
    private String device_code;     //设备序列号
    private String db;              //单别
    private String dh;              //单号
    private List<String> owner;

    private String device_customer_id;  //客户
    private String contact_id;          //联系人
    private String device_product_id;   //产品
    private String buy_time;            //购买日期
    private String field_nA2gP__c;      //设备出厂日期
    private String field_kwf17__c;      //设备机型
    private String device_name;         //设备名称

    public String getDevice_code() {
        return device_code;
    }

    public void setDevice_code(String device_code) {
        this.device_code = device_code;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getDh() {
        return dh;
    }

    public void setDh(String dh) {
        this.dh = dh;
    }

    public List<String> getOwner() {
        return owner;
    }

    public void setOwner(List<String> owner) {
        this.owner = owner;
    }

    public String getDevice_customer_id() {
        return device_customer_id;
    }

    public void setDevice_customer_id(String device_customer_id) {
        this.device_customer_id = device_customer_id;
    }

    public String getContact_id() {
        return contact_id;
    }

    public void setContact_id(String contact_id) {
        this.contact_id = contact_id;
    }

    public String getDevice_product_id() {
        return device_product_id;
    }

    public void setDevice_product_id(String device_product_id) {
        this.device_product_id = device_product_id;
    }

    public String getBuy_time() {
        return buy_time;
    }

    public void setBuy_time(String buy_time) {
        this.buy_time = buy_time;
    }

    public String getField_kwf17__c() {
        return field_kwf17__c;
    }

    public void setField_kwf17__c(String field_kwf17__c) {
        this.field_kwf17__c = field_kwf17__c;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getField_nA2gP__c() {
        return field_nA2gP__c;
    }

    public void setField_nA2gP__c(String field_nA2gP__c) {
        this.field_nA2gP__c = field_nA2gP__c;
    }
}
