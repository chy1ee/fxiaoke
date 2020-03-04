package com.chylee.fxiaoke.core.event.config;

import com.chylee.fxiaoke.common.event.RequestEvent;

public class ConfigSeqReqEvent extends RequestEvent {
    public static final int TYPE = 1003;

    private ConfigSeq apiQuoteObj;
    private ConfigSeq apiObject_snPZx__c;
    private ConfigSeq apiObject_okom1__c;

    public ConfigSeq getApiQuoteObj() {
        return apiQuoteObj;
    }

    public void setApiQuoteObj(ConfigSeq apiQuoteObj) {
        this.apiQuoteObj = apiQuoteObj;
    }

    public ConfigSeq getApiObject_snPZx__c() {
        return apiObject_snPZx__c;
    }

    public void setApiObject_snPZx__c(ConfigSeq apiObject_snPZx__c) {
        this.apiObject_snPZx__c = apiObject_snPZx__c;
    }

    public ConfigSeq getApiObject_okom1__c() {
        return apiObject_okom1__c;
    }

    public void setApiObject_okom1__c(ConfigSeq apiObject_okom1__c) {
        this.apiObject_okom1__c = apiObject_okom1__c;
    }
}
