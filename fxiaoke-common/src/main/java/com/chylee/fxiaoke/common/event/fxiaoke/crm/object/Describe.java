package com.chylee.fxiaoke.common.event.fxiaoke.crm.object;

import java.util.Map;

public class Describe {
    private String api_name;
    private Map<String, Field> fields;

    public String getApi_name() {
        return api_name;
    }

    public void setApi_name(String api_name) {
        this.api_name = api_name;
    }

    public Map<String, Field> getFields() {
        return fields;
    }

    public void setFields(Map<String, Field> fields) {
        this.fields = fields;
    }
}
