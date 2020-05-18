package com.yf.personcheck.activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.google.gson.Gson;
import com.yf.personcheck.R;
import com.yf.personcheck.model.IdentitySessionResp;
import com.yf.personcheck.network.ApiUtils;
import com.yf.personcheck.utils.ConfigConstants;
import com.yf.personcheck.utils.ToastUtil;
import com.yf.personcheck.view.camera.CustomeCameraView;
import com.yf.personcheck.view.camera.listener.CustomeListener;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CameraActivity extends AppCompatActivity {
    @BindView(R.id.jcameraview)
    CustomeCameraView jCameraView;
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.tv_time)
    TextView tv_time;

    private boolean needAction=false;//是否需要做动作

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);
        needAction=getIntent()==null?true:getIntent().getBooleanExtra("needAction",true);
        init();
        initCamera();
        if(needAction){
            getIdentitySession();
        }

    }

    private void initCamera() {
        jCameraView = findViewById(R.id.jcameraview);
        tv_time = findViewById(R.id.tv_time);
        //设置视频保存路径
        jCameraView.setSaveVideoPath(Environment.getExternalStorageDirectory().getPath() + File.separator + ConfigConstants.PATH_VIDEO);
        jCameraView.setFeatures(JCameraView.BUTTON_STATE_ONLY_RECORDER);
        jCameraView.setTip("长按录制视频");
        jCameraView.setMediaQuality(JCameraView.MEDIA_QUALITY_MIDDLE);
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
                if (identitySessionResp != null) {
                    intent.putExtra("id", identitySessionResp.getResult().getSessionId());
                }

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
                if (identitySessionResp != null) {
                    intent.putExtra("id", identitySessionResp.getResult().getSessionId());
                }
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
                ToastUtil.show("录制至少3秒的视频");
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
                            tvMsg.setText("请依次作出如下动作：" + resp.getResult().getMsg());
                        } else if (resp.getResult().getType() == 2) {//数字
                            tvMsg.setText("请依次念出如下数字：" + resp.getResult().getMsg());
                        }


                    } else {
                        ToastUtil.show(resp.getMsg());
                    }
                });

            }
        });
    }


    private void init() {
    }


    private String TAG = CameraActivity.class.getSimpleName();
}
