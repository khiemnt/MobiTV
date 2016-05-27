package com.viettel.vpmt.mobiletv.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;

/**
 * Device Utils
 * Created by neo on 2/16/2016.
 */
public class DeviceUtils {
    public static String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public static Point getDeviceSize(Activity context) {
        Display display = context.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public static int getDpi(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) (metrics.density * 160f);
    }

    public static boolean isLandscape(Activity activity) {
        return activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    public static boolean isAutoRotate(Activity activity) {
        return activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_UNDEFINED;
    }

    /**
     * Force set the orientation of activity
     * @param activity target activity
     * @param orientation 1 of those values
     *                    Configuration.ORIENTATION_LANDSCAPE
     *                    or Configuration.ORIENTATION_PORTRAIT
     *                    or Configuration.ORIENTATION_UNDEFINED
     */
    public static void forceRotateScreen(Activity activity, int orientation) {
        switch (orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case Configuration.ORIENTATION_UNDEFINED:
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                break;
        }
    }
}
