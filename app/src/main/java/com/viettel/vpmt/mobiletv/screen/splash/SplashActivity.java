package com.viettel.vpmt.mobiletv.screen.splash;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.log.Logger;
import com.viettel.vpmt.mobiletv.common.pref.PrefManager;
import com.viettel.vpmt.mobiletv.common.util.DeviceUtils;
import com.viettel.vpmt.mobiletv.network.ServiceBuilder;
import com.viettel.vpmt.mobiletv.network.callback.BaseCallback;
import com.viettel.vpmt.mobiletv.network.dto.AuthenData;
import com.viettel.vpmt.mobiletv.screen.home.HomeBoxActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final java.lang.String TAG = SplashActivity.class.getSimpleName();
    private static final int WAIT_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            if ((intent.getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
                // Activity was brought to front and not created,
                // Thus finishing this will get us to the last viewed activity
                finish();
                return;
            }
        }
        Logger.d("TAG", "DPI " + DeviceUtils.getDpi(this));

        setContentView(R.layout.activity_splash);
        autoLogin();
    }

    /**
     * Splash wait time
     */
    private void waitForSeconds() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(WAIT_TIME);
                    goHome();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Go to home page
     */
    private void goHome() {
        startActivity(new Intent(SplashActivity.this, HomeBoxActivity.class));
        SplashActivity.this.finish();
    }

    /**
     * Auto-login
     */
    private void autoLogin() {
        if (PrefManager.getMsisdn(this) == null) {
            ServiceBuilder.getService().authorize("auto_login", "841687551243").enqueue(mCallback);
        } else {
            // Already logged in, just wait for 3 seconds
            waitForSeconds();
        }
    }

    private BaseCallback<AuthenData> mCallback = new BaseCallback<AuthenData>() {
        @Override
        public void onError(String errorCode, String errorMessage) {
            Logger.e(TAG, "Authorize error " + errorMessage);
            goHome();
        }

        @Override
        public void onResponse(AuthenData data) {
            Context context = SplashActivity.this;
            PrefManager.saveUserInfo(context, data.getAccessToken(), data.getRefressToken(),
                    data.getMsisdn(), data.getExpiredTime());
            goHome();
        }
    };
}
