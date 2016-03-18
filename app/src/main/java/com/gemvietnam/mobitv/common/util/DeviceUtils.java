package com.gemvietnam.mobitv.common.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.telephony.TelephonyManager;
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
}
