package com.yf.personcheck.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Shaun_Xu
 * @description TODO
 * @date 2018/4/13
 */

public class ConfigManager {

    private static final String name = "check";
    private static SharedPreferences sPreferences;
    private static SharedPreferences.Editor editor;

    public static void initPreferenceManager(Context context) {
        sPreferences = context.getSharedPreferences(name, Activity.MODE_PRIVATE);
        editor = sPreferences.edit();
    }

    public static void clearPreference() {
        editor.clear();
        editor.commit();
    }

    public static String getSharedPreferences(String key) {
        return sPreferences.getString(key, "");
    }

    public static String getSharedPreferences(String key, String defaultValue) {
        return sPreferences.getString(key, defaultValue);
    }

    public static void setSharedPreferences(String title, Object content) {
        editor.putString(title, String.valueOf(content));
        editor.commit();
    }

    public static int getSharedPreferences(String key, int defaultValue) {
        return sPreferences.getInt(key, defaultValue);
    }

    public static void setSharedPreferences(String title, int content) {
        editor.putInt(title, content);
        editor.commit();
    }
}
