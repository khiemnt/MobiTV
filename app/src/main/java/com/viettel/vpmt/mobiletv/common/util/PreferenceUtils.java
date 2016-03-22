package com.viettel.vpmt.mobiletv.common.util;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Preferences.
 * Created by hoapham on 16/02/2016.
 */
public class PreferenceUtils {
    private static final String USER_INFO_PREFERENCE = "user_Info";
    private static final String MY_PREFERENCES = "pref";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String REFRESH_TOKEN = "refresh_token";

    public static SharedPreferences getPreference(Context context) {
        return context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
    }

    public static void clearData(Context context) {
        getPreference(context).edit().clear().commit();
    }
}
