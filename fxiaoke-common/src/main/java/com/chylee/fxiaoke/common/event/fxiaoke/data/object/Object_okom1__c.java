package com.chylee.fxiaoke.common.event.fxiaoke.data.object;

import java.math.BigDecimal;
import java.util.List;

/**
 * 差旅费用报销
 */
public class Object_okom1__c extends DataObject {
    private String name;    //编号
    private BigDecimal field_fpB2k__c;  //报销单总金额
    private String field_14yG2__c;  //报销主题
    private String field_246FG__c;  //客户 关联ID
    private String field_N7e3j__c;  //报销人
    private String field_EHyt1__c;  //单别
    private String field_8GijD__c;  //单号
    private List<String> owner;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getField_fpB2k__c() {
        return field_fpB2k__c;
    }

    public void setField_fpB2k__c(BigDecimal field_fpB2k__c) {
        this.field_fpB2k__c = field_fpB2k__c;
    }

    public String getField_14yG2__c() {
        return field_14yG2__c;
    }

    public void setField_14yG2__c(String field_14yG2__c) {
        this.field_14yG2__c = field_14yG2__c;
    }

    public String getField_246FG__c() {
        return field_246FG__c;
    }

    public void setField_246FG__c(String field_246FG__c) {
        this.field_246FG__c = field_246FG__c;
    }

    public String getField_EHyt1__c() {
        return field_EHyt1__c;
    }

    public void setField_EHyt1__c(String field_EHyt1__c) {
        this.field_EHyt1__c = field_EHyt1__c;
    }

    public String getField_8GijD__c() {
        return field_8GijD__c;
    }

    public void setField_8GijD__c(String field_8GijD__c) {
        this.field_8GijD__c = field_8GijD__c;
    }

    public String getField_N7e3j__c() {
        return field_N7e3j__c;
    }

    public void setField_N7e3j__c(String field_N7e3j__c) {
        this.field_N7e3j__c = field_N7e3j__c;
    }

    public List<String> getOwner() {
        return owner;
    }

    public void setOwner(List<String> owner) {
        this.owner = owner;
    }
}
