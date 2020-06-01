package com.yf.personcheck.activitys;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import com.bumptech.glide.Glide;
import com.flyco.roundview.RoundTextView;
import com.google.gson.Gson;
import com.yalantis.ucrop.UCrop;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.FileProvider;
import com.yanzhenjie.permission.Permission;
import com.yf.personcheck.Dialog.SelectPhotoMenuPopup;
import com.yf.personcheck.R;
import com.yf.personcheck.model.ActionCheckResp;
import com.yf.personcheck.model.IdentityPhotoCheckResp;
import com.yf.personcheck.network.ApiUtils;
import com.yf.personcheck.utils.ConfigConstants;
import com.yf.personcheck.utils.FileUtils;
import com.yf.personcheck.utils.ToastUtil;
import com.yf.personcheck.view.ProgressDialog;
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

public class PhotoCheckActivity extends BaseActivity implements SelectPhotoMenuPopup.OnSelectPhotoMenuClickListener {

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
    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ed_idnum)
    EditText edIdnum;
    @BindView(R.id.btn_bext)
    RoundTextView btnBext;
    @BindView(R.id.tv_step_one)
    TextView tvStepOne;
    @BindView(R.id.tv_step_two)
    TextView tvStepTwo;
    @BindView(R.id.tv_step_three)
    TextView tvStepThree;
    @BindView(R.id.step_one)
    LinearLayout stepOne;
    @BindView(R.id.iv_show1)
    ImageView ivShow1;
    @BindView(R.id.step_two)
    LinearLayout stepTwo;
    @BindView(R.id.btn_upload)
    RoundTextView btnUpload;
    @BindView(R.id.btn_next_two)
    RoundTextView btnNextTwo;
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.btn_ok)
    RoundTextView btnOk;
    @BindView(R.id.step_three)
    LinearLayout stepThree;
    @BindView(R.id.iv_show_end)
    ImageView ivShowEnd;
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
    protected void init() {
        super.init();
        btnNextTwo.setVisibility(View.GONE);
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

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_photo_check;
    }


    @OnClick({R.id.iv_back, R.id.btn_bext, R.id.btn_upload, R.id.btn_next_two, R.id.btn_ok, R.id.btn_again, R.id.btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_bext:
                nextStep();
                break;

            case R.id.btn_upload://上传照片
                tvTitle.setText("上传目标照片");
                pieckPhoto();
                break;

            case R.id.btn_next_two:
                tvTitle.setText("验证结果");
                check();
                break;

            case R.id.btn_ok:
                finish();

                break;
            case R.id.btn_again:
                tvTitle.setText("填写用户信息");
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

    private void check() {
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
        if (avator == null) {
            ToastUtil.show("请上传有人脸的照片");
            return;
        }
        ProgressDialog.getInstance().show(this);

        new Thread(() -> {
            String base64 = FileUtils.getImageBase64(avator.getAbsolutePath());
            ApiUtils.postIdentityPhotoCheck(base64, name, idNum, new Callback() {
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
                    IdentityPhotoCheckResp resp = new Gson().fromJson(data, IdentityPhotoCheckResp.class);
                    runOnUiThread(() -> {
                        ProgressDialog.getInstance().dismiss();

                        if (resp == null) {
                            ToastUtil.show("数据为空");
                            return;
                        }
                        if (resp.getCode() == 200 && resp.getResult() != null) {
                            tvMsg.setText(resp.getResult().getResultMsg());


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
                        } else {
                            tvMsg.setText("验证失败:活体检测未通过,请重试");
                            ivShowEnd.setImageResource(R.mipmap.ic_check_no);
                            btnOk.setVisibility(View.GONE);
                            btnAgain.setVisibility(View.VISIBLE);
                            btnBack.setVisibility(View.VISIBLE);
                        }

                    });

                }
            });


        }).start();

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
                    if (resp.getCode() == 200 && resp.getResult() != null && "0000000".equals(resp.getResult().getResultDetail())) {
                        tvStepOne.setTextColor(Color.parseColor("#333333"));
                        tvStepTwo.setTextColor(Color.parseColor("#154AFE"));
                        tvStepThree.setTextColor(Color.parseColor("#333333"));
                        stepOne.setVisibility(View.GONE);
                    } else {
                        ToastUtil.show("检测结果code=" + resp.getCode() + "," + resp.getResult().getResultMsg());
                    }

                });
            }
        });
//


    }

    private SelectPhotoMenuPopup selectPhotoMenuPopup;

    private void pieckPhoto() {
        if (selectPhotoMenuPopup == null) {
            selectPhotoMenuPopup = new SelectPhotoMenuPopup(this);
            selectPhotoMenuPopup.setOnSelectPhotoMenuClickListener(this);
        }
        selectPhotoMenuPopup.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
                    ToastUtil.show(Permission.transformText(this, permissions) + "获取失败");
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

    private Compressor compressedImage;
    private File avator;

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
                                .into(ivShow1);
                        btnNextTwo.setVisibility(View.VISIBLE);
                        btnUpload.setText("重新上传照片");
                        btnUpload.getDelegate().setBackgroundColor(Color.parseColor("FFFFFF"));

                    }, throwable -> {
                        throwable.printStackTrace();
//                            ToastManager.showErrorToast(mContext, throwable.getMessage(), true);
                    });

        } else if (requestCode == UCrop.RESULT_ERROR) {
            ToastUtil.show("裁剪失败");

        }
    }


}
