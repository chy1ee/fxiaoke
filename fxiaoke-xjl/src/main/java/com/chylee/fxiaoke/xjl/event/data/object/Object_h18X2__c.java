package com.chylee.fxiaoke.xjl.event.data.object;

import com.chylee.fxiaoke.common.event.fxiaoke.data.object.DataObject;

import java.util.List;

public class Object_h18X2__c extends DataObject {
    private String field_wqn9g__c;  //报销单
    private String field_1r0bN__c;  //单别
    private String field_g6j6m__c;  //单号
    private String field_8amY2__c;  //错误信息
    private boolean field_w9nvZ__c;  //成功
    private List<String> owner;

    public String getField_wqn9g__c() {
        return field_wqn9g__c;
    }

    public void setField_wqn9g__c(String field_wqn9g__c) {
        this.field_wqn9g__c = field_wqn9g__c;
    }

    public String getField_1r0bN__c() {
        return field_1r0bN__c;
    }

    public void setField_1r0bN__c(String field_1r0bN__c) {
        this.field_1r0bN__c = field_1r0bN__c;
    }

    public String getField_g6j6m__c() {
        return field_g6j6m__c;
    }

    public void setField_g6j6m__c(String field_g6j6m__c) {
        this.field_g6j6m__c = field_g6j6m__c;
    }

    public String getField_8amY2__c() {
        return field_8amY2__c;
    }

    public void setField_8amY2__c(String field_8amY2__c) {
        this.field_8amY2__c = field_8amY2__c;
    }

    public boolean isField_w9nvZ__c() {
        return field_w9nvZ__c;
    }

    public void setField_w9nvZ__c(boolean field_w9nvZ__c) {
        this.field_w9nvZ__c = field_w9nvZ__c;
    }

    public List<String> getOwner() {
        return owner;
    }

    public void setOwner(List<String> owner) {
        this.owner = owner;
    }
}
