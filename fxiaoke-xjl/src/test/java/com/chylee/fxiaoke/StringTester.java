package com.chylee.fxiaoke;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringTester {
    @Test
    public void testPaihao() {
        SimpleDateFormat df = new SimpleDateFormat("'a'yyyyMMdd");
        System.out.println(df.format(new Date()));
    }
}
