package com.yf.personcheck.activitys;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yf.personcheck.R;
import com.yf.personcheck.model.ActionCheckResp;
import com.yf.personcheck.network.ApiUtils;
import com.yf.personcheck.utils.ToastUtil;
import com.yf.personcheck.view.ProgressDialog;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.yf.personcheck.utils.ConfigConstants.FILE.RECORD_REQUEST;

public class VideoCheckActivity extends BaseActivity {

    @BindView(R.id.video)
    StandardGSYVideoPlayer video;
    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ed_idnum)
    EditText edIdnum;
    @BindView(R.id.btn_choose_video)
    Button btnChooseVideo;
    @BindView(R.id.btn_check)
    Button btnCheck;
    @BindView(R.id.tv_msg)
    TextView tvMsg;

    @Override
    protected void setupToolbar() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("静默视频检测");
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_video_check;
    }

    @Override
    protected void init() {
        super.init();
        //设置返回按键功能
        video.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        video.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        video.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }

    public void selectMedia() {

        AndPermission.with(mContext).permission(Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE, Permission.CAMERA, Permission.RECORD_AUDIO).onGranted(new Action<List<String>>() {
            @Override
            public void onAction(List<String> permissions) {
                // TODO what to do
                Intent intent = new Intent(mContext, CameraActivity.class);
                intent.putExtra("needAction", false);
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

    @OnClick({R.id.btn_choose_video, R.id.btn_check})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_choose_video:
                selectMedia();
                break;
            case R.id.btn_check:
                startVideoCheck();
                break;
        }
    }

    String videoPath = "";

    private void startVideoCheck() {

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
        ApiUtils.videoCheck(videoPath, name, idNum, new Callback() {
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
                        tvMsg.setText(resp.getResult().getResultMsg() + ",相似度" + resp.getResult().getSimilarity());

                    } else {
                        tvMsg.setText("检测结果code=" + resp.getCode() + "," + resp.getResult().getResultMsg());
                        ToastUtil.show("检测结果code=" + resp.getCode() + "," + resp.getResult().getResultMsg());
                    }

                });


            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == RECORD_REQUEST) {//拍摄视频
            if (data == null) return;
            videoPath = data.getStringExtra("video");//视频路径

            Log.e("视频录制返回","路径path="+videoPath);
            video.setUp(videoPath, true, "录制视频");
            video.startPlayLogic();

        }
    }
}
