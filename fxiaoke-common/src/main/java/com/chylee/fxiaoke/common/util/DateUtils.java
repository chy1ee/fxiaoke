package com.chylee.fxiaoke.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static DateFormat df = new SimpleDateFormat("yyyyMMdd");
    private static DateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    public static String toString(Date date) {
        if(date == null)
            return null;
        return df.format(date);
    }

    public static String toLongString(Date date) {
        if(date == null)
            return null;
        return df2.format(date);
    }

    public static String toTimestamp(String date) {
        if(date == null)
            return null;

        try {
            return Long.toString(df.parse(date).getTime());
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date fromString(String date) {
        try {
            return df.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }


    public static Date fromLogString(String date) throws ParseException {
        return df2.parse(date);
    }
}
