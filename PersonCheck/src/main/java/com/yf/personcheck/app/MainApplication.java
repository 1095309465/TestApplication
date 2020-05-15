package com.yf.personcheck.app;

import android.app.Application;

public class MainApplication  extends Application {
    private static MainApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public synchronized static MainApplication getInstance() {
        if (instance == null) {
            instance = new MainApplication();
        }
        return instance;
    }
}
