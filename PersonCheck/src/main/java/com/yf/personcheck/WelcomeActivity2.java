package com.yf.personcheck;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.yf.personcheck.activitys.BaseActivity;
import com.yf.personcheck.utils.ConfigManager;
import com.yf.personcheck.utils.ToastUtil;
import com.yf.personcheck.view.XieYiDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity2 extends BaseActivity {


    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.header)
    LinearLayout header;
    @BindView(R.id.btn_check)
    RoundTextView btnCheck;
    @BindView(R.id.check)
    CheckBox check;
    @BindView(R.id.tv_all)
    TextView tvAll;

    private Class className;

    public static void enterActivity(Activity activity, Class className) {
        Intent intent = new Intent(activity, WelcomeActivity2.class);
        intent.putExtra("className", className);
        activity.startActivity(intent);
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_welcome2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(R.id.top_view).statusBarDarkFont(true).init();
    }

    @Override
    protected void init() {
        super.init();
        className = getIntent() == null ? null : (Class) getIntent().getSerializableExtra("className");
        if (ConfigManager.getSharedPreferences(Allow, 0) == 1) {
            startActivity(new Intent(this, className));
            finish();
        }
    }

    public final String Allow = "allow";

    @OnClick({R.id.btn_check, R.id.tv_all, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_check:
                if (!check.isChecked()) {
                    ToastUtil.show("请选同意用户协议");
                    return;
                }
                if (className != null) {
                    ConfigManager.setSharedPreferences(Allow, 1);
                    startActivity(new Intent(this, className));
                    finish();
                }


                break;
            case R.id.tv_all:
                new XieYiDialog(this).show();
                break;
            case R.id.iv_back://返回
                finish();

                break;
        }
    }
}
