package com.yf.app2;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Scroller;

import androidx.annotation.Nullable;

public class SmoothScrollView extends LinearLayout {

    private Scroller mScroller;
    int startX;
    int startY;

    public SmoothScrollView(Context context) {
        super(context);
        init(context);
    }

    public SmoothScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SmoothScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public SmoothScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(context);
    }

    public void smoothScroll(int dx, int dy, int duration) {
        //获取滑动起点坐标
        startX = getScrollX();
        startY = getScrollY();
        mScroller.startScroll(startX, startY, dx, dy, duration);
        invalidate();


    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        boolean flag = mScroller.computeScrollOffset();
        if (flag == false)
            return;
        else {


            //mScroller.getCurrX(),mScroller.getCurrY()记录的是此刻要滑动达到的目标坐标
            this.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
        }
        invalidate();
    }
}
