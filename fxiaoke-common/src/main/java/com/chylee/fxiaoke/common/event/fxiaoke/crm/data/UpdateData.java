package com.chylee.fxiaoke.common.event.fxiaoke.crm.data;

import com.chylee.fxiaoke.common.event.fxiaoke.data.object.DataObject;

import java.util.List;
import java.util.Map;

public class UpdateData<T  extends DataObject> {
    private DataObject object_data;
    private Map<String, List<T>> details;

    public DataObject getObject_data() {
        return object_data;
    }

    public void setObject_data(DataObject object_data) {
        this.object_data = object_data;
    }

    public Map<String, List<T>> getDetails() {
        return details;
    }

    public void setDetails(Map<String, List<T>> details) {
        this.details = details;
    }
}
