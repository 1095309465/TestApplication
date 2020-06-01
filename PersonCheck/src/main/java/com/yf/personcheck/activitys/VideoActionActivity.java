package com.yf.personcheck.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.KeyboardUtils;
import com.flyco.roundview.RoundTextView;
import com.google.gson.Gson;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yf.personcheck.R;
import com.yf.personcheck.model.ActionCheckResp;
import com.yf.personcheck.model.IdentitySessionResp;
import com.yf.personcheck.network.ApiUtils;
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

import static com.yf.personcheck.utils.ConfigConstants.FILE.RECORD_REQUEST;

public class VideoActionActivity extends BaseActivity {


    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.header)
    LinearLayout header;
    @BindView(R.id.tv_step_one)
    TextView tvStepOne;
    @BindView(R.id.tv_step_two)
    TextView tvStepTwo;
    @BindView(R.id.tv_step_three)
    TextView tvStepThree;
    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ed_idnum)
    EditText edIdnum;
    @BindView(R.id.btn_bext)
    RoundTextView btnBext;
    @BindView(R.id.step_one)
    LinearLayout stepOne;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.tv_action)
    TextView tvAction;
    @BindView(R.id.btn_next_two)
    RoundTextView btnNextTwo;
    @BindView(R.id.step_two)
    LinearLayout stepTwo;
    @BindView(R.id.iv_show_end)
    ImageView ivShowEnd;
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.btn_ok)
    RoundTextView btnOk;
    @BindView(R.id.step_three)
    LinearLayout stepThree;
    @BindView(R.id.btn_again)
    RoundTextView btnAgain;
    @BindView(R.id.btn_back)
    RoundTextView btnBack;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(R.id.top_view).statusBarDarkFont(true).init();
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_video_action;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        super.init();
        edName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkStr();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edIdnum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkStr();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        initData();
    }

    private void checkStr() {
        String name = edName.getText().toString();
        String idNum = edIdnum.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(idNum)) {
            btnBext.getDelegate().setBackgroundColor(getResources().getColor(R.color.app_content_third));
            btnBext.setEnabled(false);
        }
        if (!TextUtils.isEmpty(idNum) && !TextUtils.isEmpty(idNum)) {
            btnBext.getDelegate().setBackgroundColor(getResources().getColor(R.color.colorAccent));
            btnBext.setEnabled(true);
        }
    }

    private void initData() {
        getIdentitySession();
    }

    private IdentitySessionResp identitySessionResp;

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
                        if (resp.getResult().getType() == 1) {//动作
                            tvTip.setText("请在录制中做出以下动作:");
                            tvAction.setText(resp.getResult().getMsg());
                        } else if (resp.getResult().getType() == 2) {//数字
                            tvTip.setText("请在录制中念出如下数字:");
                            tvAction.setText(resp.getResult().getMsg());
                        }


                    } else {
                        ToastUtil.show(resp.getMsg());
                    }
                });

            }
        });
    }

    private void nextStep() {
        KeyboardUtils.hideSoftInput(this);
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

        ProgressDialog.getInstance().show(this);
        ApiUtils.simpleCheck(name, idNum, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    ProgressDialog.getInstance().dismiss();
                    ToastUtil.show(e.getMessage());
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                ActionCheckResp resp = new Gson().fromJson(data, ActionCheckResp.class);
                runOnUiThread(() -> {
                    ProgressDialog.getInstance().dismiss();
                    if (resp == null) {
                        ToastUtil.show("数据为空");
                        return;
                    }
//                    if (resp.getCode() == 200 && resp.getResult() != null && "0000000".equals(resp.getResult().getResultDetail())) {
//                        tvStepOne.setTextColor(Color.parseColor("#333333"));
//                        tvStepTwo.setTextColor(Color.parseColor("#154AFE"));
//                        tvStepThree.setTextColor(Color.parseColor("#333333"));
//                        stepOne.setVisibility(View.GONE);
//
//                    } else {
//                        ToastUtil.show("检测结果code=" + resp.getCode() + "," + resp.getResult().getResultMsg());
//                    }

                    if (resp.getCode() == 200 && resp.getResult() != null) {
                        tvMsg.setText(resp.getResult().getResultMsg() );


                        ivShowEnd.setVisibility(View.VISIBLE);
                        btnOk.setVisibility(View.VISIBLE);
                    } else {

                        ivShowEnd.setVisibility(View.VISIBLE);
                        ToastUtil.show("身份信息匹配失败");
                        btnOk.setVisibility(View.VISIBLE);
                    }
                    if ("0000000".equals(resp.getResult().getResultDetail())) {
                        ivShowEnd.setImageResource(R.mipmap.ic_check_ok);
                        btnOk.setVisibility(View.VISIBLE);
                        btnAgain.setVisibility(View.GONE);
                        btnBack.setVisibility(View.GONE);
                        tvMsg.setText("验证已通过");
                        stepOne.setVisibility(View.GONE);
                    } else {
                        ivShowEnd.setImageResource(R.mipmap.ic_check_no);
                        btnOk.setVisibility(View.GONE);
                        btnAgain.setVisibility(View.VISIBLE);
                        btnBack.setVisibility(View.VISIBLE);
                    }

                });
            }
        });
//


    }

    @OnClick({R.id.iv_back, R.id.btn_next_two, R.id.btn_ok, R.id.btn_bext, R.id.btn_again, R.id.btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_next_two:
                selectMedia();
                break;
            case R.id.btn_ok:
                finish();
                break;

            case R.id.btn_bext:
                nextStep();
                break;
            case R.id.btn_again:
                stepOne.setVisibility(View.VISIBLE);
                stepTwo.setVisibility(View.VISIBLE);
                tvStepOne.setTextColor(Color.parseColor("#333333"));
                tvStepTwo.setTextColor(Color.parseColor("#333333"));
                tvStepThree.setTextColor(Color.parseColor("#154AFE"));
                btnOk.setVisibility(View.GONE);
                btnAgain.setVisibility(View.GONE);
                btnBack.setVisibility(View.GONE);


                break;
            case R.id.btn_back:
                finish();
                break;
        }
    }

    public void selectMedia() {

        AndPermission.with(mContext).permission(Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE, Permission.CAMERA, Permission.RECORD_AUDIO).onGranted(new Action<List<String>>() {
            @Override
            public void onAction(List<String> permissions) {
                // TODO what to do
                Intent intent = new Intent(mContext, CameraActivity.class);
                intent.putExtra("needAction", true);
                intent.putExtra("data", identitySessionResp);
                startActivityForResult(intent, RECORD_REQUEST);
            }
        }).onDenied(new Action<List<String>>() {
            @Override
            public void onAction(List<String> permissions) {
                // TODO what to do
                ToastUtil.show(Permission.transformText(mContext, permissions) + "获取失败");
            }
        }).start();

    }

    String videoPath = "";
    private String sessionId = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == RECORD_REQUEST) {//拍摄视频
            if (data == null) return;
            videoPath = data.getStringExtra("video");//视频路径
            sessionId = data.getStringExtra("id");//获取动作id
            if (TextUtils.isEmpty(sessionId)) {
                sessionId = "";
            }
            Log.e(TAG, "接收到视频路径=" + videoPath + ",sessionId=" + sessionId);


            startVideoCheck();


        }
    }

    private void startVideoCheck() {
        if (TextUtils.isEmpty(videoPath)) {
            return;
        }
        stepTwo.setVisibility(View.GONE);
        tvStepOne.setTextColor(Color.parseColor("#333333"));
        tvStepTwo.setTextColor(Color.parseColor("#333333"));
        tvStepThree.setTextColor(Color.parseColor("#154AFE"));

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

        ProgressDialog.getInstance().show(this);
        ApiUtils.actionCheck(videoPath, sessionId, name, idNum, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    ProgressDialog.getInstance().dismiss();
                    ToastUtil.show(e.getMessage());
                    btnOk.setVisibility(View.GONE);
                    btnAgain.setVisibility(View.VISIBLE);
                    btnBack.setVisibility(View.VISIBLE);
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                ActionCheckResp resp = new Gson().fromJson(data, ActionCheckResp.class);
                runOnUiThread(() -> {
                    ProgressDialog.getInstance().dismiss();
                    if (resp == null) {
                        ToastUtil.show("数据为空");
                        return;
                    }
                    if (resp.getCode() == 200 && resp.getResult() != null) {
                        tvMsg.setText(resp.getResult().getResultMsg() + resp.getResult().getSimilarity());


                        ivShowEnd.setVisibility(View.VISIBLE);
                    } else {
                        ivShowEnd.setVisibility(View.VISIBLE);
                        ToastUtil.show("身份信息匹配失败");
                        btnOk.setVisibility(View.GONE);
                        btnAgain.setVisibility(View.VISIBLE);
                        btnBack.setVisibility(View.VISIBLE);
                    }
                    if ("0000000".equals(resp.getResult().getResultDetail())) {
                        ivShowEnd.setImageResource(R.mipmap.ic_check_ok);
                        btnOk.setVisibility(View.VISIBLE);
                        btnAgain.setVisibility(View.GONE);
                        btnBack.setVisibility(View.GONE);
                        tvMsg.setText("验证已通过");
                    } else {
                        ivShowEnd.setImageResource(R.mipmap.ic_check_no);
                        btnOk.setVisibility(View.GONE);
                        btnAgain.setVisibility(View.VISIBLE);
                        btnBack.setVisibility(View.VISIBLE);
                    }

                });


            }
        });
    }


}
