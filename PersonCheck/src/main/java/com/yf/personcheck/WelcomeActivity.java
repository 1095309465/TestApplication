package com.yf.personcheck;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yf.personcheck.activitys.BaseActivity;
import com.yf.personcheck.activitys.PhotoCheckActivity;
import com.yf.personcheck.activitys.VideoActionActivity;
import com.yf.personcheck.activitys.VideoEasyActivity;
import com.yf.personcheck.model.TokenResp;
import com.yf.personcheck.network.ApiUtils;
import com.yf.personcheck.utils.ConfigConstants;
import com.yf.personcheck.utils.ToastUtil;
import com.yf.personcheck.view.ProgressDialog;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WelcomeActivity extends BaseActivity {


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
    @BindView(R.id.btn_photo_check)
    Button btnPhotoCheck;
    @BindView(R.id.btn_video_check)
    Button btnVideoCheck;
    @BindView(R.id.btn_photo_compare)
    Button btnPhotoCompare;
    @BindView(R.id.btn_slide_mode)
    Button btnSlideMode;
    @BindView(R.id.btn_action)
    Button btnAction;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(R.id.top_view).init();
    }

    @Override
    protected void init() {
        getPermission();
        getToekn();
    }

    private void getPermission() {
        AndPermission.with(this).permission(Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE, Permission.READ_PHONE_STATE, Permission.CAMERA).onGranted(new Action<List<String>>() {
            @Override
            public void onAction(List<String> permissions) {

                Log.i("custome", "timer onGranted");
            }
        }).onDenied(permissions -> ToastUtil.show("未获取权限会影响认证检测=" + permissions.toString())).start();
    }


    private void getToekn() {
        ProgressDialog.getInstance().show(this);
        ApiUtils.getToken(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ProgressDialog.getInstance().dismiss();
                        ToastUtil.show(e.getMessage());
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                ProgressDialog.getInstance().dismiss();
                TokenResp resp = new Gson().fromJson(json, TokenResp.class);
                if (resp != null && resp.getCode() == 200) {
                    ConfigConstants.ACCESS_TOKEN = resp.getResult().getAccessToken();
                    Log.e(TAG, "保存token" + ConfigConstants.ACCESS_TOKEN);

                    runOnUiThread(() -> {
                        ToastUtil.show("获取token成功");
                    });


                }

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_photo_check, R.id.btn_video_check, R.id.btn_photo_compare, R.id.btn_slide_mode, R.id.btn_action})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_photo_check://
                ToastUtil.show("暂未开放");

                break;
            case R.id.btn_video_check:
                ToastUtil.show("暂未开放");

                break;
            case R.id.btn_photo_compare:
                WelcomeActivity2.enterActivity(this, PhotoCheckActivity.class);

                break;
            case R.id.btn_slide_mode:
                WelcomeActivity2.enterActivity(this, VideoEasyActivity.class);

                break;
            case R.id.btn_action://
                WelcomeActivity2.enterActivity(this, VideoActionActivity.class);
                break;
        }
    }
}
