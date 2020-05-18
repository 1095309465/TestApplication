package com.yf.personcheck.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yf.personcheck.Dialog.SelectMediaMenuPopup;
import com.yf.personcheck.R;
import com.yf.personcheck.model.ActionCheckResp;
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

public class IdentitySessionActivity extends BaseActivity {

    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ed_idnum)
    EditText edIdnum;
    @BindView(R.id.btn_choose_video)
    Button btnChooseVideo;
    @BindView(R.id.btn_check)
    Button btnCheck;
    @BindView(R.id.video)
    StandardGSYVideoPlayer video;
    @BindView(R.id.tv_msg)
    TextView tvMsg;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    String videoPath = "";


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

    private SelectMediaMenuPopup selectMediaMenuPopup; //上传动态弹窗
    private int RECORD_REQUEST = 101;
    private String video_auth_photo; //视频的图片（封面
    private String video_auth_video; //视频地址 本地


    public void selectMedia() {

        AndPermission.with(mContext).permission(Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE, Permission.CAMERA, Permission.RECORD_AUDIO).onGranted(new Action<List<String>>() {
            @Override
            public void onAction(List<String> permissions) {
                // TODO what to do
                Intent intent = new Intent(mContext, CameraActivity.class);
                startActivityForResult(intent, RECORD_REQUEST);
            }
        }).onDenied(new Action<List<String>>() {
            @Override
            public void onAction(List<String> permissions) {
                // TODO what to do
                ToastUtil.show(Permission.transformText(mContext, permissions) + "获取失败");
            }
        }).start();

//
////        photoFiles.clear();
//        video_auth_video = null;
//        video_auth_photo = null;
//        if (selectMediaMenuPopup == null) {
//            selectMediaMenuPopup = new SelectMediaMenuPopup(this);
//            //隐藏选择照片条目
//            selectMediaMenuPopup.getPopupWindowView().findViewById(R.id.album).setVisibility(View.GONE);
//
//            selectMediaMenuPopup.setListener(new SelectMediaMenuPopup.OnSelectMediaMenuClickListener() {
//                @Override
//                public void onShootClick() {
//                    AndPermission.with(mContext).permission(Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE, Permission.CAMERA, Permission.RECORD_AUDIO).onGranted(new Action<List<String>>() {
//                        @Override
//                        public void onAction(List<String> permissions) {
//                            // TODO what to do
//                            Intent intent = new Intent(mContext, CameraActivity.class);
//                            startActivityForResult(intent, RECORD_REQUEST);
//                        }
//                    }).onDenied(new Action<List<String>>() {
//                        @Override
//                        public void onAction(List<String> permissions) {
//                            // TODO what to do
//                            ToastUtil.show(Permission.transformText(mContext, permissions) + "获取失败");
//                        }
//                    }).start();
//                }
//
//                @Override
//                public void onAlbumClick() {
//                    AndPermission.with(mContext).permission(Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE).onGranted(new Action<List<String>>() {
//                        @Override
//                        public void onAction(List<String> permissions) {
//                            // TODO what to do.
//                            Matisse.from(IdentitySessionActivity.this).choose(MimeType.ofImage()).countable(true).maxSelectable(9).showSingleMediaType(true)
//                                    //                                        .capture(true)
//                                    //                                        .captureStrategy(
//                                    //                                                new CaptureStrategy(true, "com.yf.jy.fileprovider"))
//                                    //                        .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                                    //                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
//                                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT).thumbnailScale(1f).imageEngine(new GlideEngine()).forResult(REQUEST_CODE_CHOOSE);
//                        }
//                    }).onDenied(new Action<List<String>>() {
//                        @Override
//                        public void onAction(List<String> permissions) {
//                            // TODO what to do
//                            ToastUtil.show(Permission.transformText(mContext, permissions) + "获取失败");
//                        }
//                    }).start();
//
//                }
//
//                @Override
//                public void onVideoClick() {
//
//                    Matisse.from(IdentitySessionActivity.this).choose(MimeType.ofVideo()).showSingleMediaType(true).maxSelectable(1).addFilter(new VideoSizeFilter())
//                            //                                        .capture(true)
//                            //                                        .captureStrategy(
//                            //                                                new CaptureStrategy(true, "com.yf.jy.fileprovider"))
//                            //                        .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                            //                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
//                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT).thumbnailScale(1f).imageEngine(new GlideEngine()).forResult(110);
//
//                }
//            });
//            selectMediaMenuPopup.showPopupWindow();
//        } else selectMediaMenuPopup.showPopupWindow();
    }

    private String sessionId = "";

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
        ApiUtils.actionCheck(videoPath, sessionId, name, idNum, new Callback() {
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
            sessionId = data.getStringExtra("id");//获取动作id
            if (TextUtils.isEmpty(sessionId)) {
                sessionId = "";
            }
            Log.e(TAG, "接收到视频路径=" + videoPath + ",sessionId=" + sessionId);


            video.setUp(videoPath, true, "录制视频");
            video.startPlayLogic();

        }
    }
}
