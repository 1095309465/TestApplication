package com.yf.personcheck.view.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Shaun_Xu
 * @description TODO
 * @date 2018/4/13
 */

public class FUManager {

    private static final String name = "yf_yj";
    private static SharedPreferences sPreferences;
    private static SharedPreferences.Editor editor;

    public static void initPreferenceManager(Context context) {
        if (sPreferences == null || editor == null) {
            sPreferences = context.getSharedPreferences(name, Activity.MODE_PRIVATE);
            editor = sPreferences.edit();
        }

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

    public static int getIntPreferences(String key, int defaultValue) {
        return sPreferences.getInt(key, defaultValue);
    }

    public static void setIntPreferences(String title, int var) {
        editor.putInt(title, var);
        editor.commit();
    }


    public static float getFloatPreferences(String key, float defaultValue) {
        return sPreferences.getFloat(key, defaultValue);
    }

    public static void setFloatPreferences(String title, float var) {
        editor.putFloat(title, var);
        editor.commit();
    }


}
