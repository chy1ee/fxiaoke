package com.chylee.fxiaoke.xjl.event.data.object;

import com.chylee.fxiaoke.common.event.fxiaoke.data.object.DataObject;

import java.util.List;

/**
 * 报价单对接结果
 */
public class Object_1sCfv__c extends DataObject {
    private String field_o2t9J__c;  //报价单ID
    private String field_GI813__c;  //单别
    private String field_88n41__c;  //单号
    private String field_ZWCn2__c;  //错误信息
    private boolean field_1xf8a__c; //结果
    private List<String> owner;

    public String getField_o2t9J__c() {
        return field_o2t9J__c;
    }

    public void setField_o2t9J__c(String field_o2t9J__c) {
        this.field_o2t9J__c = field_o2t9J__c;
    }

    public String getField_GI813__c() {
        return field_GI813__c;
    }

    public void setField_GI813__c(String field_GI813__c) {
        this.field_GI813__c = field_GI813__c;
    }

    public String getField_88n41__c() {
        return field_88n41__c;
    }

    public void setField_88n41__c(String field_88n41__c) {
        this.field_88n41__c = field_88n41__c;
    }

    public String getField_ZWCn2__c() {
        return field_ZWCn2__c;
    }

    public void setField_ZWCn2__c(String field_ZWCn2__c) {
        this.field_ZWCn2__c = field_ZWCn2__c;
    }

    public boolean isField_1xf8a__c() {
        return field_1xf8a__c;
    }

    public void setField_1xf8a__c(boolean field_1xf8a__c) {
        this.field_1xf8a__c = field_1xf8a__c;
    }

    public List<String> getOwner() {
        return owner;
    }

    public void setOwner(List<String> owner) {
        this.owner = owner;
    }
}
