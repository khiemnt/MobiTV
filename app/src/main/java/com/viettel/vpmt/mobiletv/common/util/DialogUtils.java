package com.viettel.vpmt.mobiletv.common.util;

import com.viettel.vpmt.mobiletv.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.view.Gravity;
import android.widget.TextView;


/**
 * Dialog Utils
 * Created by neo on 2/15/2016.
 */
public class DialogUtils {
    private static AlertDialog sAlert;
    private static ProgressDialog sProgress;

    /**
     * Show alert message with message id
     */
    public static void showAlert(Context context, int msgId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msgId);
        builder.create().show();
    }

    /**
     * Show alert message with message id
     */
    public static void showErrorAlert(Context context, String message) {
        dismissProgressDialog();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle(R.string.title_error);
        builder.setPositiveButton(R.string.ok, null);
        builder.create().show();
    }

    /**
     * Show dialog progress.
     *
     * @param activity the context is running.
     */
    public static void showProgressDialog(final Activity activity) {
        try {
            if (activity != null && !activity.isFinishing()) {
                if (sAlert != null && sAlert.isShowing()) {
                    sAlert.dismiss();
                }
                if (sProgress != null && sProgress.isShowing()) {
                    sProgress.dismiss();
                }
                sProgress = new ProgressDialog(activity);
                sProgress.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Show progress dialog
     */
    public static void showProgressDialog(final Context context) {
        try {
            if (sAlert != null && sAlert.isShowing()) {
                sAlert.dismiss();
            }
            if (sProgress != null && sProgress.isShowing()) {
                sProgress.dismiss();
            }
            sProgress = new ProgressDialog(context);
            sProgress.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Dismiss progress.
     *
     * @param activity the context is running.
     */
    public static void dismissProgressDialog(final Activity activity) {
        try {
            if (activity != null && !activity.isFinishing()) {
                if (sProgress != null && sProgress.isShowing()) {
                    sProgress.dismiss();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Dismiss progress dialog
     */
    public static void dismissProgressDialog() {
        try {
            if (sProgress != null && sProgress.isShowing()) {
                sProgress.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Show alert dialog with action
     */
    public static void showAlertAction(Context context, int message, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getResources().getString(message));
        builder.setPositiveButton(R.string.ok, listener);
        builder.setNegativeButton(R.string.cancel, null);
        builder.create().show();
    }

    /**
     * @param context  the context is running.
     * @param title    of dialog.
     * @param message  of dialog.
     * @param listener callback when clicked button ok.
     * @param isCancel true logParams button cancel.
     */
    public static void showDialogMessage(final Context context,
                                         final String title, final String message,
                                         final DialogInterface.OnClickListener listener, final boolean isCancel) {
        dismissProgressDialog();

        // Check context valid
        if (!isValidContext(context)) {
            return;
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (title != null)
            builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null) {
                    listener.onClick(dialog, 12);
                }
                dialog.dismiss();
            }
        });
        if (isCancel) {
            builder.setNegativeButton(R.string.cancel,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (listener != null) {
                                listener.onClick(dialog, 13);
                            }
                            dialog.dismiss();
                        }
                    });
        }
        AlertDialog sAlert = builder.create();
        sAlert.show();
        TextView messageView = (TextView) sAlert
                .findViewById(android.R.id.message);
        if (messageView != null) {
            messageView.setGravity(Gravity.CENTER);
        }
    }


    /**
     * Show dialog when network error
     *
     * @param activity Activity logParams dialog
     */
    public static void showNetworkErrorDialog(final Activity activity) {
        dismissProgressDialog();

        // Check context valid
        if (!isValidContext(activity)) {
            return;
        }

        // Open Wireless setting
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!activity.isFinishing()) {
                    activity.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                }
            }
        };

        // Show dialog
        showDialogMessage(activity, activity.getString(R.string.title_network_lost),
                activity.getString(R.string.msg_network_lost), listener,
                false);
    }

    /**
     * Check validation of context
     */
    private static boolean isValidContext(Context context) {
        // Context null
        if (context == null) {
            return false;
        }

        // Activity is finishing
        if (context instanceof Activity
                && ((Activity) context).isFinishing()) {
            return false;
        }

        // Otherwise, context is valid to show dialog
        return true;
    }

}