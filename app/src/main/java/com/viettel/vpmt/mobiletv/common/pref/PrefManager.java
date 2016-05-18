package com.viettel.vpmt.mobiletv.common.pref;

import com.google.android.exoplayer.C;

import com.viettel.vpmt.mobiletv.base.log.Logger;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Shared Preferences Manager.
 * Created by neo on 16/02/2016.
 */
public class PrefManager {
    private static final String MY_PREFERENCES = "Pref";
    public static final String ACCESS_TOKEN = "accessToken";
    public static final String REFRESS_TOKEN = "refressToken";
    public static final String MSISDN = "msisdn";
    public static final String EXPIRED_TIME = "expiredTime"; // Timeout of Access token (In SECONDS)

    public static SharedPreferences getPreference(Context context) {
        return context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
    }

    public static void saveUserInfo(Context context, String accessToken, String refreshToken, String msisdn, Long expiredTime) {
        if (context == null) {
            return;
        }
        SharedPreferences.Editor editor = getPreference(context).edit();
        editor.putString(ACCESS_TOKEN, accessToken);
        editor.putString(REFRESS_TOKEN, refreshToken);
        editor.putString(MSISDN, msisdn);
        editor.putLong(EXPIRED_TIME, expiredTime);
        editor.apply();
        Logger.i("Saved user infor " + msisdn);
        Logger.i("Saved user token " + accessToken);
    }

    /**
     * Get user access token
     */
    public static String getAccessToken(Context context) {
        return getPreference(context).getString(ACCESS_TOKEN, null);
    }

    /**
     * Get header for OAuth 2.0 request
     */
    public static String getHeader(Context context) {
        return "Bearer " + PrefManager.getAccessToken(context);
    }

    /**
     * Get user refresh token
     */
    public static String getRefreshToken(Context context) {
        return getPreference(context).getString(REFRESS_TOKEN, null);
    }

    /**
     * Get user MSISDN
     */
    public static String getMsisdn(Context context) {
        return getPreference(context).getString(MSISDN, null);
    }

    /**
     * Get Access Token expired time
     */
    public static long getExpiredTime(Context context) {
        return getPreference(context).getLong(EXPIRED_TIME, 0L);
    }

    public static void clearData(Context context) {
        getPreference(context).edit().clear().commit();
    }
}
