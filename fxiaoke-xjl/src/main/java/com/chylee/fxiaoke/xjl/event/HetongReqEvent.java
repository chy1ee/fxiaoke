package com.chylee.fxiaoke.xjl.event;

import com.chylee.fxiaoke.xjl.event.data.object.Object_47F7O__c;
import com.chylee.fxiaoke.xjl.event.data.object.Object_snPZx__c;

public class HetongReqEvent extends AccountReqEvent {

    private Object_snPZx__c ht;
    private Object_47F7O__c htmx;

    public Object_snPZx__c getHt() {
        return ht;
    }

    public void setHt(Object_snPZx__c ht) {
        this.ht = ht;
    }

    public Object_47F7O__c getHtmx() {
        return htmx;
    }

    public void setHtmx(Object_47F7O__c htmx) {
        this.htmx = htmx;
    }
}
