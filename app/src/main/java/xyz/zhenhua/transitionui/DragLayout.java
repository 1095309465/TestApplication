package xyz.zhenhua.transitionui;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.FloatRange;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

/**
 * Created by zachary on 2018/2/8.
 */

public class DragLayout extends RelativeLayout {
    private RelativeLayout containerView;
    private VelocityTracker vt = null;
    private LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

    private int x;  // 触摸点横坐标
    private int y;  // 触摸点纵坐标
    private float dX = 0f;  // x轴位移
    private float dY = 0f;  // y轴位移
    private float downX;    // 触摸点横坐标
    private float downY;    // 触摸点纵坐标
    private float moveX;
    private float moveY;
    private int screenHeight;
    private float scaleRatio;
    private float minScaleFactor = 0.4f;    // 最小缩放系数

    private boolean isTrigger = false;

    private float STIFFNESS = 300f; // 弹性动画硬度
    private float DAMPING_RATIO = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY;    // 弹性动画阻尼
    private int maxSpeed = 500;
    private int maxDistance = 700;

    private SpringAnimation xAnimation; // x方向回弹动画
    private SpringAnimation yAnimation; // y方向回弹动画
    private SpringAnimation scaleAnimationX;
    private SpringAnimation scaleAnimationY;

    private SlideableListener slideableListener; // 监听

    public DragLayout(Context context) {
        super(context);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        containerView = new RelativeLayout(context);
        this.screenHeight = getScreenHeight();
    }

    // 进行初始化工作
    public void init() {
        // 限制直接子view的数量为1
        if (getChildCount() != 1) {
            throw new RuntimeException("SlideableView must have and just one direct childview（SlideableView有且只能有一个直接子View)");
        }

        View childView = getChildAt(0);
        ViewGroup.LayoutParams childLp = childView.getLayoutParams();
        removeView(childView);
        containerView.addView(childView, childLp);

        addView(containerView, layoutParams);

        containerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                xAnimation = createSpringAnimation(containerView, SpringAnimation.X, containerView.getX(), STIFFNESS, DAMPING_RATIO);
                yAnimation = createSpringAnimation(containerView, SpringAnimation.Y, containerView.getY(), STIFFNESS, DAMPING_RATIO);
                scaleAnimationX = createSpringAnimation(containerView, SpringAnimation.SCALE_X, 1f, STIFFNESS, DAMPING_RATIO);
                scaleAnimationY = createSpringAnimation(containerView, SpringAnimation.SCALE_Y, 1f, STIFFNESS, DAMPING_RATIO);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    containerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    containerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 触摸位置
        x = (int) event.getRawX();
        y = (int) event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                double speed = Math.sqrt(Math.pow(vt.getXVelocity(), 2) + Math.pow(vt.getYVelocity(), 2));
                double distance = Math.sqrt(Math.pow(moveX, 2) + Math.pow(moveY, 2));

                // 如果滑动速度或滑动距离满足条件，则关闭动画
                if (speed > maxSpeed || distance > maxDistance) {
                    if (slideableListener != null) {
                        slideableListener.onDismiss();
                    }
                    break;
                }

                // 开始弹性动画
                startSpringAnimation();

                if (slideableListener != null) {
                    slideableListener.onSpringStart();
                }
                break;
            case MotionEvent.ACTION_DOWN:
                isTrigger = false;
                cancleSpringAnimation();

                dX = containerView.getX() - event.getRawX();
                dY = containerView.getY() - event.getRawY();

                downX = event.getRawX();
                downY = event.getRawY();

                // 初始化velocityTracker的对象 vt 用来监测motionevent的动作
                if (vt == null) {
                    vt = VelocityTracker.obtain();
                } else {
                    vt.clear();
                }
                vt.addMovement(event);

                if (slideableListener != null) {
                    slideableListener.onSlideStart();
                }

                break;
            case MotionEvent.ACTION_MOVE:
                vt.addMovement(event);
                vt.computeCurrentVelocity(100);
                moveX = event.getRawX() - downX;
                moveY = event.getRawY() - downY;

                // 计算缩放率，只有向下滑动才缩放，向上滑动不缩放
                if (moveY > 0) {
                    scaleRatio = 1 - (Math.abs(moveY) / (screenHeight / 2));
                }

                // 过滤手指接触屏幕后直接向上滑动的情况，只有先向下滑动，触发缩放后，才能向上滑动
                if (isTrigger) {    // 触发缩放后可以任意滑动
                    startTranslationY();
                    // 缩放率最小不能低于 minScaleFactor
                    if (scaleRatio > minScaleFactor) {
                        startScaleAnimation();
                    }
                } else {    // 未触发缩放，只能先向下滑动来触发缩放
                    if (moveY > 0) {
                        startTranslationY();
                        if (scaleRatio > minScaleFactor) {
                            startScaleAnimation();
                        }
                        isTrigger = true;
                    }
                }
                startTranslationX();
                break;
            default:
                break;
        }
        return true;
    }

    public void startScaleAnimation() {
        containerView.animate().scaleX(scaleRatio).scaleY(scaleRatio).setDuration(0).start();
    }

    public void startTranslationX() {
        containerView.animate().x(x + dX).setDuration(0).start();
    }

    public void startTranslationY() {
        containerView.animate().y(y + dY).setDuration(0).start();
    }

    public void startSpringAnimation() {
        xAnimation.start();
        yAnimation.start();
        scaleAnimationX.start();
        scaleAnimationY.start();
    }

    public void cancleSpringAnimation() {
        xAnimation.cancel();
        yAnimation.cancel();
        scaleAnimationX.cancel();
        scaleAnimationY.cancel();
    }

    // 创建弹性动画
    private SpringAnimation createSpringAnimation(View view,
                                                  DynamicAnimation.ViewProperty property,
                                                  Float finalPosition,
                                                  @FloatRange(from = 0.0) Float stiffness,
                                                  @FloatRange(from = 0.0) Float dampingRatio) {
        SpringAnimation animation = new SpringAnimation(view, property);
        SpringForce spring = new SpringForce(finalPosition);

        spring.setStiffness(stiffness);
        spring.setDampingRatio(dampingRatio);
        animation.setSpring(spring);

        return animation;
    }

    public void setSlideableListener(SlideableListener slideableListener) {
        this.slideableListener = slideableListener;
    }

    public interface SlideableListener {
        public void onSlideStart();

        public void onSpringStart();

        public void onSpringStop();

        public void onDismiss();
    }

    public int getScreenHeight() {
        WindowManager manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.heightPixels;
    }
}
