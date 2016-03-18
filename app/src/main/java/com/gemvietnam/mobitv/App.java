package com.gemvietnam.mobitv;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

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
