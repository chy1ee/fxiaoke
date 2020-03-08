package com.chylee.fxiaoke;

import com.chylee.fxiaoke.xjl.model.Copma;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringTester {
    @Test
    public void testPaihao() {
        SimpleDateFormat df = new SimpleDateFormat("'a'yyyyMMdd");
        System.out.println(df.format(new Date()));
    }

    @Test
    public void testInvoke() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Copma copma = new Copma();
        Method method = Copma.class.getDeclaredMethod("setMA001", new Class[]{String.class});
        method.invoke(copma, "");
    }
}
