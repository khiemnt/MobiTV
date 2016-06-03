package com.viettel.vpmt.mobiletv.common.util;

import com.viettel.vpmt.mobiletv.base.log.Logger;
import com.viettel.vpmt.mobiletv.network.dto.Box;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;

/**
 * Compatibility Util
 * Created by neo on 6/1/2016.
 */
public class CompatibilityUtil {
    public static final float MARGIN_PERCENT  = 0.031f;
    public static final float SPACING_PERCENT = 0.01f;

    public static final int NUMBER_VIDEO_MOBILE = 2;
    public static final int NUMBER_VIDEO_TABLET = 4;
    public static final int NUMBER_FILM_MOBILE = 3;
    public static final int NUMBER_FILM_TABLET = 6;

    /** Get the current Android API level. */
    public static int getSdkVersion() {
        return Build.VERSION.SDK_INT;
    }

    /** Determine if the device is running API level 8 or higher. */
    public static boolean hasFroyo() {
        return getSdkVersion() >= Build.VERSION_CODES.FROYO;
    }

    /** Determine if the device is running API level 11 or higher. */
    public static boolean hasHoneycomb() {
        return getSdkVersion() >= Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * Determine if the device is a tablet (i.e. it has a large screen).
     *
     * @param context The calling context.
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * Determine if the device is a HoneyComb tablet.
     *
     * @param context The calling context.
     */
    public static boolean hasHoneycombTablet(Context context) {
        return hasHoneycomb() && isTablet(context);
    }

    public static int getScreenMargin(Context context) {
        return (int) (DeviceUtils.getDeviceSize((Activity) context).x * MARGIN_PERCENT);
    }

    public static int getItemSpacing(Context context) {
        return (int) (DeviceUtils.getDeviceSize((Activity) context).x * SPACING_PERCENT);
    }

    public static int getNumberItem(Context context, Box.Type boxType) {
        if (boxType == Box.Type.LIVETV || boxType == Box.Type.FILM) {
            return isTablet(context) ? NUMBER_FILM_TABLET : NUMBER_FILM_MOBILE;
        } else {
            return isTablet(context) ? NUMBER_VIDEO_TABLET: NUMBER_VIDEO_MOBILE;
        }
    }

    public static int getWidthItemHasSpacing(Context context, Box.Type boxType) {
        if (boxType == Box.Type.FILM || boxType == Box.Type.LIVETV) {
            return getWidthFilmItem(context);
        } else {
            return getVideoItemWidth(context);
        }
    }

    public static int getWidthItemNoSpacing(Context context, Box.Type boxType) {
        if (boxType == Box.Type.FILM || boxType == Box.Type.LIVETV) {
            return getFilmItemNoSpacingWidth(context);
        } else {
            return getVideoItemNoSpacingWidth(context);
        }
    }

    private static int getWidthFilmItem(Context context) {
        int screenWidth = DeviceUtils.getDeviceSize((Activity) context).x;
        float margin = screenWidth * MARGIN_PERCENT;
        float spacing = screenWidth * SPACING_PERCENT;

        int numberItem = isTablet(context) ? NUMBER_FILM_TABLET : NUMBER_FILM_MOBILE;
        int width = (int) ((screenWidth - (numberItem - 1) * spacing - margin * 2) / numberItem);
        Logger.e("@@@", "screen " + screenWidth);
        Logger.e("@@@", "margin " + margin);
        Logger.e("@@@", "spacing " + spacing);
        Logger.e("@@@", "numberItem " + numberItem);
        Logger.e("@@@@", " width " + width);

        return width;
    }

    private static int getFilmItemNoSpacingWidth(Context context) {
        int screenWidth = DeviceUtils.getDeviceSize((Activity) context).x;
        float margin = screenWidth * MARGIN_PERCENT;

        int numberItem = isTablet(context) ? NUMBER_FILM_TABLET : NUMBER_FILM_MOBILE;

        return (int) ((screenWidth - margin * 2) / numberItem);
    }

    private static int getVideoItemWidth(Context context) {
        int screenWidth = DeviceUtils.getDeviceSize((Activity) context).x;
        int margin = (int) (screenWidth * MARGIN_PERCENT);
        int spacing = (int) (screenWidth * SPACING_PERCENT);

        int numberItem = isTablet(context) ? NUMBER_VIDEO_TABLET : NUMBER_VIDEO_MOBILE;

        return (screenWidth - (numberItem - 1) * spacing - margin * 2) / numberItem;
    }

    private static int getVideoItemNoSpacingWidth(Context context) {
        int screenWidth = DeviceUtils.getDeviceSize((Activity) context).x;
        int margin = (int) (screenWidth * MARGIN_PERCENT);

        int numberItem = isTablet(context) ? NUMBER_VIDEO_TABLET : NUMBER_VIDEO_MOBILE;

        return (screenWidth - margin * 2) / numberItem;
    }

    /** This class can't be instantiated. */
    private CompatibilityUtil() { }
}
