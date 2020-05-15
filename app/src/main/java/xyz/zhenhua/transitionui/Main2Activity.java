package xyz.zhenhua.transitionui;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

public class Main2Activity extends AppCompatActivity {

    private VideoPresenter presenter;
    String source1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";

    public void btn(View view) {
        finishAfterTransition();
    }

    OrientationUtils orientationUtils;

    private StandardGSYVideoPlayer video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        presenter = new VideoPresenter(this);
        presenter.setupWindowAnimations();
        video = findViewById(R.id.video);
        video.setUp(source1, true, "测试视频");


    }


    /**
     * 播放
     */
    public void play(View view) {
        video.startPlayLogic();

    }

    /**
     * 设置封面
     */
    public void setThumbImageView(View view) {
        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageResource(R.mipmap.ic_launcher);
        video.setThumbImageView(imageView);

    }

    /**
     * 设置旋转
     *
     * @param view
     */
    public void orientation(View view) {
        //设置旋转
        orientationUtils = new OrientationUtils(this, video);
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        video.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orientationUtils.resolveByClick();
            }
        });
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
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onBackPressed() {
        //先返回正常状态
        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            video.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        video.setVideoAllCallBack(null);
        super.onBackPressed();
    }
}
