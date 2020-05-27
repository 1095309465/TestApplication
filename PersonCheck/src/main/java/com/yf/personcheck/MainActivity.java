package com.yf.personcheck;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yf.personcheck.activitys.BaseActivity;
import com.yf.personcheck.activitys.FaceCheckActivity;
import com.yf.personcheck.activitys.IdentityActivity;
import com.yf.personcheck.activitys.IdentitySessionActivity;
import com.yf.personcheck.activitys.SimpleCheckActivity;
import com.yf.personcheck.activitys.VideoCheckActivity;
import com.yf.personcheck.model.TokenResp;
import com.yf.personcheck.network.ApiUtils;
import com.yf.personcheck.utils.ConfigConstants;
import com.yf.personcheck.utils.ToastUtil;
import com.yf.personcheck.view.ProgressDialog;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends BaseActivity {

    @Override
    protected void init() {
        getPermission();
        getToekn();
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setupToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("人证检测");
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
                        ((Button) findViewById(R.id.btn_token)).setText("获取token成功");
                    });


                }

            }
        });
    }

    public void btn(View view) {
        switch (view.getId()) {
            case R.id.btn_token://获取token
                getToekn();
                break;
            case R.id.btn_face_check://人脸识别
                if (TextUtils.isEmpty(ConfigConstants.ACCESS_TOKEN)) {
                    ToastUtil.show("请点击第一个按钮，获取Token");
                }

                Intent intent = new Intent(this, FaceCheckActivity.class);
                startActivity(intent);

                break;

            case R.id.btn_identity://人证合一
                if (TextUtils.isEmpty(ConfigConstants.ACCESS_TOKEN)) {
                    ToastUtil.show("请点击第一个按钮，获取Token");
                }
                Intent inten2 = new Intent(this, IdentityActivity.class);
                startActivity(inten2);


                break;

            case R.id.btn_identity_session://拍摄动作序列视频,IdentitySessionActivity
                if (TextUtils.isEmpty(ConfigConstants.ACCESS_TOKEN)) {
                    ToastUtil.show("请点击第一个按钮，获取Token");
                }
                Intent inten3 = new Intent(this, IdentitySessionActivity.class);
                startActivity(inten3);


                break;

            case R.id.btn_id_check://身份检测
                if (TextUtils.isEmpty(ConfigConstants.ACCESS_TOKEN)) {
                    ToastUtil.show("请点击第一个按钮，获取Token");
                }
                Intent inten4 = new Intent(this, SimpleCheckActivity.class);
                startActivity(inten4);
                break;

            case R.id.btn_video_check:
                if (TextUtils.isEmpty(ConfigConstants.ACCESS_TOKEN)) {
                    ToastUtil.show("请点击第一个按钮，获取Token");
                }
                Intent inten5 = new Intent(this, VideoCheckActivity.class);
                startActivity(inten5);

                break;


        }
    }


}
