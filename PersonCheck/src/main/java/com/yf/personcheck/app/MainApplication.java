package com.yf.personcheck.app;

import android.app.Application;

import com.yf.personcheck.utils.ConfigManager;
import com.yf.personcheck.utils.MyUtils;

public class MainApplication extends Application {
    private static MainApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        MyUtils.init(this);

        ConfigManager.initPreferenceManager(this);

    }

    public synchronized static MainApplication getInstance() {
        if (instance == null) {
            instance = new MainApplication();
        }
        return instance;
    }
}
