package com.viettel.vpmt.mobiletv.screen.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.viettel.vpmt.mobiletv.R;
import com.viettel.vpmt.mobiletv.base.log.Logger;
import com.viettel.vpmt.mobiletv.common.util.DeviceUtils;
import com.viettel.vpmt.mobiletv.screen.home.HomeBoxActivity;

public class SplashActivity extends AppCompatActivity {

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

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    startActivity(new Intent(SplashActivity.this, HomeBoxActivity.class));
                    SplashActivity.this.finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void autoLogin() {

    }
}
