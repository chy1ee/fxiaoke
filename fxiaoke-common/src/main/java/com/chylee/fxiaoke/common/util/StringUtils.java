package com.chylee.fxiaoke.common.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

public class StringUtils {
    public static boolean isEmpty(String s) {
        if(s == null || "".equals(s.trim()))
            return true;

        return false;
    }

    public static String phone(String p) {
        String s = trim(p, false);
        if (s != null)
            s = s.replace(" ", "").replace("　", "");
        return s;
    }

    public static String trim(String s) {
        return trim(s, false);
    }

    public static String trim(String s, boolean blankable) {
        if (s != null) {
            s = s.trim();
            if ("".equals(s) && !blankable)
                return null;
        }
        return s;
    }

    public static String toDay(String s) {
        if (isEmpty(s) || s.length() < 8)
            return null;

        return String.format("%s-%s-%s", s.substring(0, 4), s.substring(4, 6), s.substring(6, 8));
    }

    public static BigDecimal discountToBigDecimal(String discount) {
        String discountToUse;
        if (discount.endsWith("%"))
            discountToUse = discount.substring(0, discount.length() - 1);
        else
            discountToUse = "100";

        return new BigDecimal(discountToUse).divide(new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP);
    }

    public static String left(String str, int len) {
        if (str == null) {
            return null;
        }

        if (len < 0) {
            return "";
        }
        if (str.length() <= len) {
            return str;
        }
        return str.substring(0, len);
    }

    public static int gbkLength(String str) {
        try {
            return str.getBytes("GBK").length;
        } catch (UnsupportedEncodingException e) {
            return -1;
        }
    }

    public static String gbkLeft(String str, int subSLength)    {
        String  subStr ="";
        try {
            if (str == null)
                return null;
            else{
                int tempSubLength = subSLength;//截取字节数
                subStr = str.substring(0, str.length()<subSLength ? str.length() : subSLength);//截取的子串
                int subStrByetsL = subStr.getBytes("GBK").length;//截取子串的字节长度
                // 说明截取的字符串中包含有汉字
                while (subStrByetsL > tempSubLength){
                    int subSLengthTemp = --subSLength;
                    subStr = str.substring(0, subSLengthTemp>str.length() ? str.length() : subSLengthTemp);
                    subStrByetsL = subStr.getBytes("GBK").length;
                }
            }
        } catch (Exception e) {
            return "";
        }
        return subStr;
    }

    public static boolean isNumber(String str){
        boolean re = true;
        for(int i=0;i<str.length();i++){
            if(!Character.isDigit(str.charAt(i))){
                re = false;
                break;
            }
        }
        return re;
    }
}
