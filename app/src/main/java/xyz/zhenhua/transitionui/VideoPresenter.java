package xyz.zhenhua.transitionui;

import android.app.Activity;
import android.transition.Slide;
import android.transition.Visibility;
import android.view.Gravity;

public class VideoPresenter {

    private Activity context;

    public VideoPresenter(Activity context) {
        this.context = context;
    }

    public void setupWindowAnimations() {
        // 首次进入显示的动画
        Visibility visibility = buildEnterTransition();
        context.getWindow().setEnterTransition(visibility);
        // 启动新 Activity ，此页面退出的动画
        visibility = buildReturnTransition();
        context.getWindow().setExitTransition(visibility);
    }

    private Visibility buildEnterTransition() {
        Slide enterTransition = new Slide();
        enterTransition.setSlideEdge(Gravity.BOTTOM);
        enterTransition.setDuration(500);
        // 此视图将不会受到输入过渡动画的影响
        // enterTransition.excludeTarget(R.id.square_red, true);
        return enterTransition;
    }


    private Visibility buildReturnTransition() {
        Slide visibility = new Slide();
        visibility.setSlideEdge(Gravity.TOP);
        visibility.setDuration(500);
        return visibility;
    }

}
