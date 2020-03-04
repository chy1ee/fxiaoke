package com.chylee.fxiaoke.common.cache;

import java.util.HashMap;
import java.util.Map;

public class SelectOneCacher extends HashMap<String, String> {

    public void cache(String label, String value) {
        this.put(label, value);
    }

    public String loadByLabel(String label) {
        return this.get(label);
    }

    public String loadByValue(String value) {
        for (Map.Entry entry : this.entrySet()) {
            if (value != null && value.equals(entry.getValue()))
                return (String) entry.getKey();
        }

        return null;
    }
}
