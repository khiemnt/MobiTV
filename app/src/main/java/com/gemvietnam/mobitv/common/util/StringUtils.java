package com.gemvietnam.mobitv.common.util;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String Utils
 * Created by neo on 2/16/2016.
 */
public class StringUtils {
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean validateEmail(final String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static String convertListToString(List<UUID> words){
        String result ="";
        for(int i =0;i<words.size();i++){
            if(i==words.size() -1)
                result = result + words.get(i).toString();
            else
                result = result + words.get(i).toString()+",";

        }

        return result;
    }

}
