package com.yf.personcheck.utils;

import android.widget.Toast;

import com.yf.personcheck.app.MainApplication;

public class ToastUtil {

    public static final boolean FLAG = true;

    public static void show(String content) {
        if (!FLAG) return;
        Toast.makeText(MainApplication.getInstance(), content, Toast.LENGTH_SHORT).show();
    }
}
