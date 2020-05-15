package com.yf.personcheck.activitys;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.yalantis.ucrop.UCrop;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.FileProvider;
import com.yanzhenjie.permission.Permission;
import com.yf.personcheck.Dialog.SelectPhotoMenuPopup;
import com.yf.personcheck.R;
import com.yf.personcheck.model.IdentityPhotoCheckResp;
import com.yf.personcheck.network.ApiUtils;
import com.yf.personcheck.utils.ConfigConstants;
import com.yf.personcheck.utils.FileUtils;
import com.yf.personcheck.utils.ToastUtil;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.utils.PathUtils;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.yf.personcheck.utils.ConfigConstants.FILE.REQUEST_CODE_CAMERA;
import static com.yf.personcheck.utils.ConfigConstants.FILE.REQUEST_CODE_CHOOSE;

public class IdentityActivity extends BaseActivity implements SelectPhotoMenuPopup.OnSelectPhotoMenuClickListener {


    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ed_idnum)
    EditText edIdnum;
    @BindView(R.id.iv_show)
    ImageView ivShow;
    @BindView(R.id.btn_check)
    Button btnCheck;
    @BindView(R.id.tv_msg)
    TextView tvMsg;

    @Override
    protected void setupToolbar() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("人证合一");
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_video;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private SelectPhotoMenuPopup selectPhotoMenuPopup;

    @OnClick({R.id.iv_show, R.id.btn_check})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_show:
                pieckPhoto();

                break;
            case R.id.btn_check:
                startCheck();
                break;
        }
    }

    private void startCheck() {
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
        if (avator == null) {
            ToastUtil.show("请上传有人脸的照片");
            return;
        }
        new Thread(() -> {
            String base64 = FileUtils.getImageBase64(avator.getAbsolutePath());
            ApiUtils.postIdentityPhotoCheck(base64, name, idNum, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() -> ToastUtil.show(e.getMessage()));

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String data = response.body().string();
                    IdentityPhotoCheckResp resp = new Gson().fromJson(data, IdentityPhotoCheckResp.class);
                    runOnUiThread(() -> {

                        if (resp == null) {
                            ToastUtil.show("数据为空");
                            return;
                        }
                        if (resp.getCode() == 200 && resp.getResult() != null) {
                            tvMsg.setText(resp.getResult().getResultMsg() + ",相似度" + resp.getResult().getSimilarity());

                        } else {
                            ToastUtil.show("检测结果code=" + resp.getCode() + "," + resp.getResult().getResultMsg());
                        }

                    });

                }
            });


        }).start();


    }

    private Compressor compressedImage;
    private File avator;

    private void pieckPhoto() {
        if (selectPhotoMenuPopup == null) {
            selectPhotoMenuPopup = new SelectPhotoMenuPopup(this);
            selectPhotoMenuPopup.setOnSelectPhotoMenuClickListener(this);
        }
        selectPhotoMenuPopup.show();
    }

    @Override
    public void onShootClick() {
        AndPermission.with(this)
                .permission(Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE, Permission.CAMERA)
                .onGranted(permissions -> {
                    // TODO what to do.
                    File file = new File(Environment.getExternalStorageDirectory() + File.separator + ConfigConstants.PATH_PICTURE + "/ic_avator.jpg");
                    if (file.exists()) {
                        boolean isDelete = file.delete();
                        Log.i("custome", "file delete is " + isDelete);
                    }
                    File cropFile = new File(Environment.getExternalStorageDirectory() + File.separator + ConfigConstants.PATH_PICTURE + "/ic_crop_avator.jpg");
                    if (cropFile.exists()) {
                        boolean isDelete = cropFile.delete();
                        Log.i("custome", "cropFile delete is " + isDelete);
                    }

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Uri imageUri;
                    if (Build.VERSION.SDK_INT >= 24) {
                        imageUri = FileProvider.getUriForFile(mContext, getPackageName() + ".fileprovider", file);
                    } else {
                        imageUri = Uri.fromFile(file);
                    }

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, REQUEST_CODE_CAMERA);
                })
                .onDenied(permissions -> {
                    // TODO what to do
                    ToastUtil.show(Permission.transformText(IdentityActivity.this, permissions) + "获取失败");
                }).start();

    }


    @Override
    public void onAlbumClick() {
        AndPermission.with(mContext)
                .permission(Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE)
                .onGranted(permissions -> {

                    Matisse.from((Activity) mContext)
                            .choose(MimeType.ofImage())
                            .showSingleMediaType(true)
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                            .thumbnailScale(1f)
                            .imageEngine(new GlideEngine())
                            .forResult(REQUEST_CODE_CHOOSE);
                })
                .onDenied(permissions -> {
                    // TODO what to do
                    ToastUtil.show(Permission.transformText(mContext, permissions) + "获取失败");
                }).start();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            for (int i = 0; i < Matisse.obtainResult(data).size(); i++) {
//                ToastManager.showSuccessToast(mContext, PathUtils.getPath(this, Matisse.obtainResult(data).get(i)), false);
            }

            File chooseFile = new File(PathUtils.getPath(this, Matisse.obtainResult(data).get(0)));
            if (!chooseFile.exists()) {
                ToastUtil.show("选择的图片文件不存在");
            } else {
                Log.e(TAG, "选择的图片地址是=" + chooseFile.getAbsolutePath());
            }
            startAvatarCrop(chooseFile.getAbsolutePath());


        } else if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {
            File actualImage = new File(Environment.getExternalStorageDirectory() + File.separator + ConfigConstants.PATH_PICTURE + "/ic_avator.jpg");
            if (!actualImage.exists())
                return;

            startAvatarCrop(Environment.getExternalStorageDirectory() + File.separator + ConfigConstants.PATH_PICTURE + "/ic_avator.jpg");

        } else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {

            File cropFile = new File(Environment.getExternalStorageDirectory() + File.separator + ConfigConstants.PATH_PICTURE + "/ic_crop_avator.jpg");
            if (!cropFile.exists()) {
                return;
            }

            if (compressedImage == null) {
                compressedImage = new Compressor(this)
                        .setDestinationDirectoryPath(Environment.getExternalStorageDirectory() + File.separator + ConfigConstants.PATH_PICTURE);
            }

            compressedImage
                    .compressToFileAsFlowable(cropFile, System.currentTimeMillis() + ".jpg")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(file -> {

                        avator = file;
                        Glide.with(mContext)
                                .load(file)
                                .into(ivShow);
                        btnCheck.setVisibility(View.VISIBLE);

                    }, throwable -> {
                        throwable.printStackTrace();
//                            ToastManager.showErrorToast(mContext, throwable.getMessage(), true);
                    });

        } else if (requestCode == UCrop.RESULT_ERROR) {
            ToastUtil.show("裁剪失败");

        }
    }
}
