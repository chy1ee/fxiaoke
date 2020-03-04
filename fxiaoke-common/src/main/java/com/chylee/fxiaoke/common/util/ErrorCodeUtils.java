package com.chylee.fxiaoke.common.util;

import java.util.HashMap;
import java.util.Map;

public class ErrorCodeUtils {
    private static Map<Integer, String> errors = new HashMap<>();

    public static String getMessage(int code) {
        return errors.get(code);
    }

    public static void addToErrors(Map<Integer, String> errorsToAdd) {
        errors.putAll(errorsToAdd);
    }
}
