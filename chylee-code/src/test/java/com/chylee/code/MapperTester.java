package com.chylee.code;

import org.junit.Test;

public class MapperTester {
    @Test
    public void xml() {
        Mapper.toXml("com.chylee.fxiaoke.xjl.mapper", "ACTTB", Acttb.class);
    }
}
