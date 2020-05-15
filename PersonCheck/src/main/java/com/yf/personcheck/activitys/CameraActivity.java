package com.yf.personcheck.activitys;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cjt2325.cameralibrary.JCameraView;
import com.cjt2325.cameralibrary.listener.CaptureListener;
import com.cjt2325.cameralibrary.listener.ClickListener;
import com.cjt2325.cameralibrary.listener.ErrorListener;
import com.cjt2325.cameralibrary.listener.JCameraListener;
import com.yf.personcheck.R;
import com.yf.personcheck.utils.ConfigConstants;
import com.yf.personcheck.utils.ToastUtil;
import com.yf.personcheck.view.camera.CustomeCameraView;
import com.yf.personcheck.view.camera.listener.CustomeListener;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class CameraActivity extends AppCompatActivity {

    public int mode = 0;
    public static int scense_normal = 1;//normal
    public static int scense_auth = 2;//认证 video
    public static int scense_trends = 3;//动态 video and photo
    private CustomeCameraView jCameraView;
    private TextView tv_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_camera);
        mode = getIntent().getIntExtra("data", 1);
        initCamera();
    }

    private void initCamera() {
        jCameraView = findViewById(R.id.jcameraview);
        tv_time = findViewById(R.id.tv_time);
        //设置视频保存路径
        jCameraView.setSaveVideoPath(Environment.getExternalStorageDirectory().getPath() + File.separator + ConfigConstants.PATH_VIDEO);
        if (mode == scense_auth) {
            jCameraView.setFeatures(JCameraView.BUTTON_STATE_ONLY_RECORDER);
            jCameraView.setTip("长按录制视频");
            jCameraView.setMediaQuality(JCameraView.MEDIA_QUALITY_MIDDLE);
        } else if (mode == scense_trends) {
            jCameraView.setFeatures(JCameraView.BUTTON_STATE_BOTH);
            jCameraView.setTip("轻点拍照，长按录制视频");
            jCameraView.setMediaQuality(JCameraView.MEDIA_QUALITY_MIDDLE);
        }
        jCameraView.setErrorLisenter(new ErrorListener() {
            @Override
            public void onError() {
                ToastUtil.show("录制失败");
                finish();
            }

            @Override
            public void AudioPermissionError() {
                ToastUtil.show("给点录音权限可以");
                finish();
            }
        });

        jCameraView.setJCameraLisenter(new JCameraListener() {
            @Override
            public void captureSuccess(Bitmap bitmap) {
                /*暂时项目还没用到*/
                //获取图片bitmap
                //                Log.i("JCameraView", "bitmap = " + bitmap.getWidth());
                String photo = saveBitmap(bitmap);
//                String path = FileUtil.saveBitmap("JCamera", bitmap);
                Intent intent = new Intent();
                intent.putExtra("photo", photo);

                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void recordSuccess(String url, Bitmap firstFrame) {
                //获取视频封面路径
                String photo = saveBitmap(firstFrame);
//                Log.i("CJT", "url = " + url + ", Bitmap = " + photo);
                Intent intent = new Intent();
                intent.putExtra("photo", photo);
                intent.putExtra("video", url);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        jCameraView.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {
                CameraActivity.this.finish();
            }
        });
        jCameraView.setRightClickListener(new ClickListener() {
            @Override
            public void onClick() {
                //Toast.makeText(CameraActivity.this, "Right", Toast.LENGTH_SHORT).show();
            }
        });
        jCameraView.setCaptureListener(new CaptureListener() {
            @Override
            public void takePictures() {
                tv_time.setVisibility(View.GONE);
            }

            @Override
            public void recordShort(long time) {
                ToastUtil.show("录制至少8秒的视频");
                tv_time.setVisibility(View.GONE);
            }

            @Override
            public void recordStart() {
                tv_time.setVisibility(View.VISIBLE);
            }

            @Override
            public void recordEnd(long time) {
                tv_time.setVisibility(View.GONE);
            }

            @Override
            public void recordZoom(float zoom) {

            }

            @Override
            public void recordError() {
                tv_time.setVisibility(View.GONE);
            }
        });

        jCameraView.setCustomeListener(new CustomeListener() {
            @Override
            public void showTime(long millionTime) {
                tv_time.setVisibility(View.VISIBLE);
                tv_time.setText(millionTime / 1000l + "秒");
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        //全屏显示
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        jCameraView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        jCameraView.onPause();
    }

    public static String saveBitmap(Bitmap b) {
        String jpegName = Environment.getExternalStorageDirectory() + "/" + ConfigConstants.PATH_PICTURE + "/" + System.currentTimeMillis() + ".jpg";
        try {
            FileOutputStream fout = new FileOutputStream(jpegName);
            BufferedOutputStream bos = new BufferedOutputStream(fout);
            b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return jpegName;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }


}
