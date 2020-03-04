package com.chylee.fxiaoke.common.event.fxiaoke.msg;

public class Form {
    private String label;
    private String value;

    public Form() {}

    public Form(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
