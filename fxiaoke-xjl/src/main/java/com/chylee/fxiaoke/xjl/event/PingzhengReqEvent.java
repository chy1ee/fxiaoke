package com.chylee.fxiaoke.xjl.event;

import com.chylee.fxiaoke.common.event.RequestEvent;
import com.chylee.fxiaoke.common.event.fxiaoke.data.object.Object_79pYP__c;
import com.chylee.fxiaoke.common.event.fxiaoke.data.object.Object_okom1__c;

import java.util.List;

public class PingzhengReqEvent extends RequestEvent {
    private Object_okom1__c pz;
    private List<Object_79pYP__c> pzmx;

    public Object_okom1__c getPz() {
        return pz;
    }

    public void setPz(Object_okom1__c pz) {
        this.pz = pz;
    }

    public List<Object_79pYP__c> getPzmx() {
        return pzmx;
    }

    public void setPzmx(List<Object_79pYP__c> pzmx) {
        this.pzmx = pzmx;
    }
}
