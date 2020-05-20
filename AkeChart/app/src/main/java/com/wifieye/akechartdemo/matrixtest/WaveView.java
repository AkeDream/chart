package com.wifieye.akechartdemo.matrixtest;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import static android.graphics.Color.GREEN;

/**
 * Create by ake on 2019/12/6
 * Describe:
 */
public class WaveView extends View {

    private Paint mPaint;
    private Path mPath;
    private int mItemWaveLength=400;
    private int dx;
    public WaveView(Context context) {
        super(context);
        init();
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){

        mPaint=new Paint();
        mPaint.setColor(GREEN);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mPath=new Path();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.reset();
        int originY=300;
        int halfWaveLen=mItemWaveLength/2;
        mPath.moveTo(-mItemWaveLength+dx,originY);
        for (int i=-mItemWaveLength;i<=getWidth()+mItemWaveLength;i+=mItemWaveLength){

            mPath.rQuadTo(halfWaveLen/2,-50,halfWaveLen,0);
            mPath.rQuadTo(halfWaveLen/2,50,halfWaveLen,0);
        }

        mPath.lineTo(getWidth(),getHeight());
        mPath.lineTo(0,getHeight());
        mPath.close();

        canvas.drawPath(mPath,mPaint);


    }

    public void startAnim(){

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, mItemWaveLength);
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx= (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        valueAnimator.start();

    }

}
