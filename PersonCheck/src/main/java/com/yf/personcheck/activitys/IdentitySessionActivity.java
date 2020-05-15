package com.yf.personcheck.activitys;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yf.personcheck.R;
import com.yf.personcheck.model.IdentitySessionResp;
import com.yf.personcheck.network.ApiUtils;
import com.yf.personcheck.utils.ToastUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class IdentitySessionActivity extends BaseActivity {

    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.iv_show_video)
    ImageView ivShowVideo;
    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ed_idnum)
    EditText edIdnum;
    @BindView(R.id.btn_choose_video)
    Button btnChooseVideo;
    @BindView(R.id.btn_check)
    Button btnCheck;

    @Override
    protected void setupToolbar() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("人证合一动作检测");
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_identity_session;
    }

    @Override
    protected void init() {
        super.init();
        getIdentitySession();
    }

    private void getIdentitySession() {
        ApiUtils.getIdentitySession(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                runOnUiThread(() -> {

                    IdentitySessionResp resp = new Gson().fromJson(data, IdentitySessionResp.class);
                    if (resp == null) {
                        ToastUtil.show("数据为空");
                        return;
                    }
                    if (resp.getCode() == 200 && resp.getResult() != null) {
                        identitySessionResp = resp;
                        tvMsg.setText(resp.getResult().getMsg());
                        ToastUtil.show(resp.getMsg() + "," + resp.getResult());

                    } else {
                        ToastUtil.show(resp.getMsg());
                    }
                });

            }
        });
    }

    private IdentitySessionResp identitySessionResp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    String videoPath = "/storage/emulated/0/YFCamera/video_1586224275689.mp4";


    @OnClick({R.id.btn_choose_video, R.id.btn_check})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_choose_video:
                break;
            case R.id.btn_check:
                startVideoCheck();
                break;
        }
    }

    private void startVideoCheck() {
        if (identitySessionResp == null) {
            ToastUtil.show("请先获取动作描述");
            getIdentitySession();
            return;
        }
        String name = edName.getText().toString();
        String idNum = edIdnum.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtil.show("请输入姓名");
            return;
        }
        if (TextUtils.isEmpty(idNum)) {
            ToastUtil.show("请输入证件号码");
            return;
        }

        ApiUtils.actionCheck(videoPath, identitySessionResp.getResult().getSessionId(), name, idNum, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

}
