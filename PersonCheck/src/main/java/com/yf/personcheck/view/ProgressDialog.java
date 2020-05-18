package com.yf.personcheck.view;

import android.content.Context;
import android.content.DialogInterface;

/**
 * @author azheng
 * @date 2018/5/24.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：圆形进度条Dialog
 */
public class ProgressDialog {

    private static volatile ProgressDialog instance;

    private ProgressDialog() {
    }

    public static ProgressDialog getInstance() {
        if (instance == null) {
            synchronized (ProgressDialog.class) {
                if (instance == null) {
                    instance = new ProgressDialog();
                }
            }
        }
        return instance;
    }

    private DialogProgress progressDialog;

    public void show(Context mContext, DialogInterface.OnCancelListener onCancelListener) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        progressDialog = new DialogProgress(mContext);
        progressDialog.setOnCancelListener(onCancelListener);
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    public void show(Context mContext) {
//
        if (progressDialog != null && progressDialog.isShowing()) {
            return;
        }
        progressDialog = new DialogProgress(mContext);
        progressDialog.show();


    }

    public void dismiss() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }


    }


}
