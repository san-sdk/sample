package com.san.sansample;

import com.san.api.SanAdSdk;

import androidx.multidex.MultiDexApplication;

public class SanSampleApplication extends MultiDexApplication {

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
