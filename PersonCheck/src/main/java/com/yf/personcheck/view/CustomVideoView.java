package com.yf.personcheck.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.VideoView;

public class CustomVideoView extends VideoView {
    public CustomVideoView(Context context) {
        super(context);
        init();
    }

    public CustomVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomVideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private Paint paint;

    private void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
//                    Rect rect = new Rect(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
                    Rect rect = new Rect(0, 0, view.getMeasuredWidth(), 2*view.getMeasuredHeight());
                    outline.setRoundRect(rect, radius);
                }
            });
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setClipToOutline(true);
        }
    }

    private int radius = 0;

    public void turnRound() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            invalidateOutline();
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("绘制边框", "onDraw=" + getWidth() + "," + getHeight() + "," + radius);
        if (paint == null) {
            paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setAntiAlias(true);
            paint.setColor(Color.parseColor("#00FF00"));
        }
        drawBorder(canvas);
    }

    private void drawBorder(Canvas canvas) {
        Log.e("绘制边框", "drawBorder=" + getWidth() + "," + getHeight() + "," + radius);
        if (canvas == null) {
            return;
        }
        paint.setStrokeWidth(2);
        Path drawPath = new Path();
        drawPath.addRoundRect(new RectF(0 + 10, 0 + 10, getWidth() - 10, getHeight() - 10), radius, radius, Path.Direction.CW);
        canvas.drawPath(drawPath, paint);
        Log.e("绘制边框", "drawBorder-drawPath=" + getWidth() + "," + getHeight() + "," + radius);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int min = Math.min(width, height);
        setMeasuredDimension(min, min*2);
        //因为是圆，所以要设置为长宽相等
        radius = (int) (min * 1.0f / 2);
        init();
    }
}
