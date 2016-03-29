package com.viettel.vpmt.mobiletv.common.util;

/**
 * String Utils
 * Created by neo on 2/16/2016.
 */
public class StringUtils {
    public static boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    public static boolean isEmpty(CharSequence s) {
        return s == null || s.length() == 0;
    }
}
