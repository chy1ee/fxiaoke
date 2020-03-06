package com.chylee.fxiaoke.xjl.event.data.object;

import com.chylee.fxiaoke.common.event.fxiaoke.data.object.DataObject;

import java.util.List;

/**
 * 合同对接结果
 */
public class Object_qlu3s__c extends DataObject {
    private String field_0R1xn__c;  //合同
    private String field_161LU__c;  //单别
    private String field_55sOU__c;  //单号
    private String field_m55dZ__c;  //错误信息
    private boolean field_mv48u__c; //成功
    private List<String> owner;

    public String getField_0R1xn__c() {
        return field_0R1xn__c;
    }

    public void setField_0R1xn__c(String field_0R1xn__c) {
        this.field_0R1xn__c = field_0R1xn__c;
    }

    public String getField_161LU__c() {
        return field_161LU__c;
    }

    public void setField_161LU__c(String field_161LU__c) {
        this.field_161LU__c = field_161LU__c;
    }

    public String getField_55sOU__c() {
        return field_55sOU__c;
    }

    public void setField_55sOU__c(String field_55sOU__c) {
        this.field_55sOU__c = field_55sOU__c;
    }

    public String getField_m55dZ__c() {
        return field_m55dZ__c;
    }

    public void setField_m55dZ__c(String field_m55dZ__c) {
        this.field_m55dZ__c = field_m55dZ__c;
    }

    public boolean isField_mv48u__c() {
        return field_mv48u__c;
    }

    public void setField_mv48u__c(boolean field_mv48u__c) {
        this.field_mv48u__c = field_mv48u__c;
    }

    public List<String> getOwner() {
        return owner;
    }

    public void setOwner(List<String> owner) {
        this.owner = owner;
    }
}
