package com.yf.personcheck.activitys;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yf.personcheck.R;
import com.yf.personcheck.model.ActionCheckResp;
import com.yf.personcheck.network.ApiUtils;
import com.yf.personcheck.utils.ToastUtil;
import com.yf.personcheck.view.ProgressDialog;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SimpleCheckActivity extends BaseActivity {


    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ed_idnum)
    EditText edIdnum;
    @BindView(R.id.btn_check)
    Button btnCheck;
    @BindView(R.id.tv_msg)
    TextView tvMsg;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_simple_check;
    }

    @Override
    protected void setupToolbar() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("人证合一简单检测-身份证姓名检测");
    }

    @Override
    protected void init() {
        super.init();
    }

    public void btn(View view) {
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
                    if (resp.getCode() == 200 && resp.getResult() != null) {
                        tvMsg.setText(resp.getResult().getResultMsg());

                    } else {
                        tvMsg.setText("检测结果code=" + resp.getCode() + "," + resp.getResult().getResultMsg());
                        ToastUtil.show("检测结果code=" + resp.getCode() + "," + resp.getResult().getResultMsg());
                    }

                });
            }
        })
        ;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
