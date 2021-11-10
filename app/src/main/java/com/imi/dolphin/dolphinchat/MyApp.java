package com.imi.dolphin.dolphinchat;

import android.app.Application;

import com.imi.dolphin.dolphinlib.connector.DolphinCore;

import timber.log.Timber;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        DolphinCore.init(
                this,
                BuildConfig.CLIENT_ID,
                BuildConfig.CLIENT_SECRET,
                BuildConfig.BASE_URL
        );

        DolphinCore.setTriggerMenu("halo");
        DolphinCore.setShowTriggerMenuMessage(true);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}