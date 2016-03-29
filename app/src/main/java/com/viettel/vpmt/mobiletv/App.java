package com.viettel.vpmt.mobiletv;

import com.crashlytics.android.Crashlytics;

import android.app.Application;

import io.fabric.sdk.android.Fabric;

/**
 * Mailbox application
 * Created by neo on 3/3/2016.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Fabric
        Fabric.with(this, new Crashlytics());
    }
}
