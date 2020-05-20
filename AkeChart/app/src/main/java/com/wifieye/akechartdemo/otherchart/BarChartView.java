package com.wifieye.akechartdemo.otherchart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.Nullable;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by ake on 2019/12/30
 * Describe:
 */
public class BarChartView extends View {

    private Paint paint;

    private int mWidth;
    private int mHeight;

    private int pdBottom=dp2px(20);

    private int pdLeft=dp2px(20);
    private int pdRight=dp2px(20);

    private int xItem=dp2px(30);
    private int xScale=dp2px(60);

    private Integer[] xLab={1,2,3,4,5,6,7,8,9,10};
    private int[] yLab={20,50,70,10,40,60,50,90,15,25};
    private List<Float> xValues=new ArrayList<>();

    private int firstPositionX;
    private GestureDetector gestureDetector;

    private int dp2px(int i) {

        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (density*i+0.5f);
    }

    public BarChartView(Context context) {
        super(context);
        init();
    }

    public BarChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BarChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);

        gestureDetector=new GestureDetector(getContext(),new MyGesture());

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=getWidth();
        mHeight=getHeight();
        firstPositionX=pdLeft;
        startAnimator();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBar(canvas);
    }

    private void drawBar(Canvas canvas) {

        int yItem = (mHeight - pdBottom) / 100;

        for (int i = 0; i < xLab.length; i++) {

            RectF rectF = new RectF(firstPositionX+i*xScale-xItem/2,mHeight-pdBottom-yLab[i]*yItem*dV,
                    firstPositionX+i*xScale+xItem/2,mHeight-pdBottom);
            canvas.drawRect(rectF,paint);

        }

    }

    private class  MyGesture extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            distanceX=-distanceX;
            firstPositionX= (int) (firstPositionX+distanceX);
            invalidate();

            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

    private float dV;
    public void startAnimator(){

        ValueAnimator valueAnimator= ValueAnimator.ofFloat(0, 1f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
               dV = (float) animation.getAnimatedValue();
               postInvalidate();
            }
        });
        valueAnimator.setInterpolator(new OvershootInterpolator());
        valueAnimator.setDuration(2000);
        valueAnimator.start();

    }


}
