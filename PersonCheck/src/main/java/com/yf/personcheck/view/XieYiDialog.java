package com.yf.personcheck.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yf.personcheck.R;

public class XieYiDialog extends Dialog {
    public XieYiDialog(@NonNull Context context) {
        super(context);
        setOwnerActivity((Activity) context);
    }

    public XieYiDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setOwnerActivity((Activity) context);
    }

    protected XieYiDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        setOwnerActivity((Activity) context);
    }

    private int layoutResId = R.layout.dialog_xieyi;

    private boolean canCancle = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResId);
        this.setCancelable(canCancle);
        this.setCanceledOnTouchOutside(canCancle);


        findViewById(R.id.btn_dright).setOnClickListener(v -> dismiss());
    }
}
