package com.yf.personcheck.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.yf.personcheck.R;


/**
 * @author Shaun_Xu
 * @description TODO
 * @date 2018/6/19
 * 改进：WS  可以只有一个button,可以传自定义布局(但View的id和类型不可改变)
 */

public class MessageDialog extends Dialog implements View.OnClickListener {

    private TextView tv_message;
    private MessageDialog.BaseDialogInterface dialogListener;
    private String message, left, right, title;

    private View.OnClickListener chaClickListener;

    public MessageDialog setChaClickListener(View.OnClickListener chaClickListener) {
        this.chaClickListener = chaClickListener;
        return this;
    }

    public MessageDialog(Context context) {
        //super(context, R.style.NotDaddingDialog);
        super(context);
        setOwnerActivity((Activity) context);
    }

    private boolean onlyOk = false;

    public MessageDialog(Context context, String message) {
        //super(context, R.style.NotDaddingDialog);
        super(context);
        setOwnerActivity((Activity) context);
        this.message = message;
        onlyOk = true;
    }

    public MessageDialog(Context context, String message, String right, boolean oklyOk) {
        //super(context, R.style.NotDaddingDialog);
        super(context);
        setOwnerActivity((Activity) context);
        this.message = message;
        this.right = right;
        onlyOk = oklyOk;
    }

    public MessageDialog(Context context, String positiveButtonText, String negativeButtonText) {
        //super(context, R.style.NotDaddingDialog);
        super(context);
        setOwnerActivity((Activity) context);
        this.left = positiveButtonText;
        this.right = negativeButtonText;
    }

    private boolean canCancle = true;

    public MessageDialog(Context context, String message, boolean canCancle) {
        //super(context, R.style.NotDaddingDialog);
        super(context);
        setOwnerActivity((Activity) context);
        this.message = message;
        this.canCancle = canCancle;

        onlyOk = true;

    }

    public MessageDialog(Context context, String message, String positiveButtonText, String negativeButtonText) {
        //super(context, R.style.NotDaddingDialog);
        super(context);
        setOwnerActivity((Activity) context);
        this.message = message;
        this.left = positiveButtonText;
        this.right = negativeButtonText;
    }

    public MessageDialog(Context context, String title, String message, String positiveButtonText, String negativeButtonText) {
        //super(context, R.style.NotDaddingDialog);
        super(context);
        setOwnerActivity((Activity) context);
        this.title = title;
        this.message = message;
        this.left = positiveButtonText;
        this.right = negativeButtonText;
    }

    private boolean leftRed;

    public MessageDialog(Context context, String message, String positiveButtonText, String negativeButtonText, boolean leftRed) {
        //super(context, R.style.NotDaddingDialog);
        super(context);
        setOwnerActivity((Activity) context);
        this.message = message;
        this.left = positiveButtonText;
        this.right = negativeButtonText;
        this.leftRed = leftRed;

    }

    public MessageDialog(Context context, int layoutResId) {
        //super(context, R.style.NotDaddingDialog);
        super(context);
        setOwnerActivity((Activity) context);
        this.layoutResId = layoutResId;
    }

    public MessageDialog(Context context, int layoutResId, String message, String positiveButtonText, String negativeButtonText) {
        //super(context, R.style.NotDaddingDialog);
        super(context);
        setOwnerActivity((Activity) context);
        this.layoutResId = layoutResId;
        this.message = message;
        this.left = positiveButtonText;
        this.right = negativeButtonText;
    }

    private int layoutResId = R.layout.view_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(layoutResId);
        this.setCancelable(canCancle);
        this.setCanceledOnTouchOutside(canCancle);


        TextView tv_title = findViewById(R.id.tv_title);
        if (tv_title != null) {
            if (TextUtils.isEmpty(title)) {
                tv_title.setVisibility(View.GONE);
            } else {
                tv_title.setVisibility(View.VISIBLE);
                tv_title.setText(title);
            }
        }


//        findViewById(R.id.ll_dlayout).setVisibility(View.VISIBLE);
        Button btn_left = (Button) findViewById(R.id.btn_dleft);
        Button btn_right = (Button) findViewById(R.id.btn_dright);


        if (btn_left != null) {
            btn_left.setOnClickListener(this);

            if (TextUtils.isEmpty(left)) {
                btn_left.setVisibility(View.GONE);
            } else {
                btn_left.setVisibility(View.VISIBLE);
                btn_left.setText(left);
            }
        }

        if (btn_right != null) { //如果指定的布局里就有两个按钮
            btn_right.setText(right);
            btn_right.setOnClickListener(this);

            //只有左边一个按钮，右边negativeButtonText按钮为空，
            if (TextUtils.isEmpty(right)) {
                findViewById(R.id.line).setVisibility(View.GONE);
                btn_right.setVisibility(View.GONE);
            }
        }
        if (onlyOk) {
            btn_right.setVisibility(View.VISIBLE);
            btn_left.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(right)) {
                btn_right.setText(right);
            } else {
                btn_right.setText("确认");
            }


        }


        if (leftRed) {
            btn_left.setTextColor(Color.parseColor("#FF6376"));
        }

        tv_message = (TextView) findViewById(R.id.tv_message);
        if (tv_message != null) {
            if (!TextUtils.isEmpty(message)) {
//            tv_message.setVisibility(View.VISIBLE);
                tv_message.setText(message);
            }
        }

        windowDeploy();
    }


    @SuppressWarnings("deprecation")
    public void windowDeploy() {
        Window window = getWindow(); // 得到对话框
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams wl = window.getAttributes();
        //wl.gravity = Gravity.CENTER;
        //wl.gravity = Gravity.CENTER_HORIZONTAL;
        WindowManager m = window.getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        wl.width = ViewGroup.LayoutParams.WRAP_CONTENT; //这样才能居中显示，才能点击外面dialog消失
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;//这样才能居中显示，才能点击外面dialog消失
        //		wl.width = d.getWidth();
        //		wl.height = d.getHeight();
        window.setAttributes(wl);


        //wl.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_dleft:// 确定按钮点击
                if (dialogListener != null) dialogListener.onLeftButtonClicked(v);
                break;
            case R.id.btn_dright:// 取消按钮点击
                if (dialogListener != null) dialogListener.onRightButtonClicked(v);
                break;
            default:
                break;
        }
        dismiss();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        message = null;
        left = null;
        right = null;
    }

    public MessageDialog setMessage(String message) {
        this.message = message;
        return this;
    }

    public MessageDialog setPositiveButtonText(String text) {
        this.left = text;
        return this;
    }

    public MessageDialog setNegativeButtonText(String text) {
        this.right = text;
        return this;
    }

    public void show(MessageDialog.BaseDialogInterface dialogListener) {
        this.dialogListener = dialogListener;
        show();
    }

    public void registerListener(MessageDialog.BaseDialogInterface listener) {
        dialogListener = listener;
    }


    public interface BaseDialogInterface {

        public void onRightButtonClicked(View v);

        public void onLeftButtonClicked(View v);

    }

}
