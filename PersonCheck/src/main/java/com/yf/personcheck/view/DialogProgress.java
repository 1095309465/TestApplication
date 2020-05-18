package com.yf.personcheck.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.yf.personcheck.R;


public class DialogProgress extends Dialog {


    public DialogProgress(Context context) {
        super(context, R.style.DialogTheme);
        init(context);
    }

    private Activity activity;

    private void init(Context context) {
        activity = (Activity) context;
        if (activity == null) {
            return;
        }
        View view = View.inflate(context, R.layout.layout_progressgidloa, null);
        this.setContentView(view);

        Window window = getWindow();
        //设置弹出位置
        window.setGravity(Gravity.CENTER);
        //设置弹出动画
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }

}
