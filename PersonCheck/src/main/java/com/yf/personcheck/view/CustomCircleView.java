package com.yf.personcheck.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.yf.personcheck.utils.MyUtils;

public class CustomCircleView extends View {

    private Paint paint;

    public CustomCircleView(Context context) {
        super(context);
    }

    public CustomCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private int radius = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (paint == null) {
            paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setAntiAlias(true);
            paint.setColor(Color.parseColor("#EEEEEE"));
        }

        drawBorder(canvas);
    }

    private Path mPath;

    private void drawBorder(Canvas canvas) {
        Log.e("绘制边框", "drawBorder=" + getWidth() + "," + getHeight() + "," + radius);
        if (canvas == null) {
            return;
        }
        paint.setStrokeWidth(2);
        if (mPath == null) {
            mPath = new Path();
        }
        RectF backgroundRectF = new RectF(getWidth() / 2 - MyUtils.dp2px(100) + 2, MyUtils.dp2px(75) + 2, getWidth() / 2 + MyUtils.dp2px(100) - 2, MyUtils.dp2px(75) + 2 * MyUtils.dp2px(100));
        radius = getMeasuredWidth() / 2;
        mPath.addRoundRect(backgroundRectF, radius, radius, Path.Direction.CW);
        canvas.drawPath(mPath, paint);
        Log.e("绘制边框", "drawBorder-drawPath=" + getWidth() + "," + getHeight() + "," + radius);

        // 设置 Xfermode 为 SRC_OUT
        paint.setStyle(Paint.Style.FILL);
        PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);
        paint.setXfermode(porterDuffXfermode);

        canvas.drawRoundRect(new RectF(0 + 2, 0 + 2, getWidth() - 2, getHeight() - 2), 10, 10, paint);
        Log.e("绘制边框", "drawBorder-drawRoundRect=" + getWidth() + "," + getHeight() + "," + radius);

    }

//    private void drawBorder2(Canvas canvas) {
//        Log.e("绘制边框", "drawBorder=" + getWidth() + "," + getHeight() + "," + radius);
//        if (canvas == null) {
//            return;
//        }
//        if (mPath == null) {
//            mPath = new Path();
//        }
//        mPath.addCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, Path.Direction.CCW);
//        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(Color.parseColor("#FFFFFF"));
//        paint.setStrokeWidth(2);
//        canvas.clipPath(mPath);
//        Log.e("绘制边框", "drawBorder-drawPath=" + getWidth() + "," + getHeight() + "," + radius);
//    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int width = getMeasuredWidth();
//        int height = getMeasuredHeight();
//        int min = Math.min(width, height);
//        setMeasuredDimension(min, min);
//        //因为是圆，所以要设置为长宽相等
//        radius = (int) (min * 1.0f / 2);
//    }
}
