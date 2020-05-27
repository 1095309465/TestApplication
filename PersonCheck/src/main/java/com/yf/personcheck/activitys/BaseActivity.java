package com.yf.personcheck.activitys;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gyf.barlibrary.ImmersionBar;
import com.yalantis.ucrop.UCrop;
import com.yf.personcheck.utils.ConfigConstants;

import java.io.File;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG;

    protected Context mContext;

    protected ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResId());
        TAG = this.getClass().getSimpleName();
        initImmersionBar();
        ButterKnife.bind(this);
        mContext = this;
        init();

    }

    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarDarkFont(true, 0.2f);
//            mImmersionBar.statusBarDarkFont(true);
        mImmersionBar.init();
    }

    protected void init() {


    }

    protected abstract int setLayoutResId();

    protected void setupToolbar() {
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;

    }


    protected void startAvatarCrop(String file) {
        String filePath = Environment.getExternalStorageDirectory() + File.separator + ConfigConstants.PATH_PICTURE + "/ic_crop_avator.jpg";
        Uri destinationUri = Uri.fromFile(new File(filePath));
        UCrop uCrop = UCrop.of(Uri.fromFile(new File(file)), destinationUri);
        //        UCrop.Options options = new UCrop.Options();
        //        设置裁剪图片可操作的手势
        //        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.NONE, 1);
        //设置toolbar颜色
        //        options.setToolbarColor(ActivityCompat.getColor(this, R.color.white));
        //设置状态栏颜色
        //        options.setStatusBarColor(ActivityCompat.getColor(this, R.color.white));
        //是否能调整裁剪框
        //        options.setFreeStyleCropEnabled(true);
        //        uCrop.withOptions(options);
        uCrop.withAspectRatio(1, 1);
        uCrop.start(this);
    }
}
