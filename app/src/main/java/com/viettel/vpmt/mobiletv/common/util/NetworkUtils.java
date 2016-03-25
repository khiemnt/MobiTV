package com.viettel.vpmt.mobiletv.common.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Network utilities
 * Created by neo on 3/23/2016.
 */
public class NetworkUtils {

    public static boolean checkNetwork(final Activity activity) {
        boolean isCon = isOnline(activity);
        if (!isCon) {
            DialogUtils.showNetworkErrorDialog(activity);
        }
        return isCon;
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
