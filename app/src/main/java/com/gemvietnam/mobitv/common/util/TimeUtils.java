package com.gemvietnam.mobitv.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Convert time - string
 * Created by hoapham on 24/02/2016.
 */
public class TimeUtils {
    public static String getFormatTime() {
        return "dd,MMM yyyy";
    }

    public static String toStringDate(long milisec) {
        String dateString = new SimpleDateFormat(getFormatTime()).format(new Date(milisec));
        return dateString;
    }

    public static long toTime(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(getFormatTime());
        try {
            Date date = dateFormat.parse(dateString);
            return date.getTime();
        } catch (ParseException e) {
            return 0;
        }
    }
}
