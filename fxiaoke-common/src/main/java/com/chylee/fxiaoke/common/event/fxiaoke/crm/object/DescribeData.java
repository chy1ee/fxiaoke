package com.chylee.fxiaoke.common.event.fxiaoke.crm.object;

import java.util.List;

public class DescribeData {
    private Describe describe;
    private List<ObjectItem> objects;

    public Describe getDescribe() {
        return describe;
    }

    public void setDescribe(Describe describe) {
        this.describe = describe;
    }

    public List<ObjectItem> getObjects() {
        return objects;
    }

    public void setObjects(List<ObjectItem> objects) {
        this.objects = objects;
    }
}
