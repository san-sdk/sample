package com.san.sansample;

import android.app.Application;

import com.san.api.SanAdSdk;

public class SanSampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            SanAdSdk.init(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
