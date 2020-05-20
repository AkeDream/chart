package com.wifieye.akechartdemo.matrixtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;


/**
 * Create by ake on 2019/12/6
 * Describe:
 */
public class BezierThumberView extends View {

    private Paint mPaint;

    private Path mPath;
    private float mPreX;
    private float mPreY;


    public BezierThumberView(Context context) {
        super(context);
        init();
    }

    public BezierThumberView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BezierThumberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BezierThumberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init(){

        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        mPath=new Path();


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath,mPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                mPath.moveTo(event.getX(),event.getY());
                mPreX = event.getX();
                mPreY = event.getY();

                return true;
                case  MotionEvent.ACTION_MOVE:
//                    mPath.lineTo(event.getX(),event.getY());

                    float endX = (mPreX + event.getX()) / 2;
                    float endY = (mPreY + event.getY()) / 2;
                    mPath.quadTo(mPreX,mPreY,endX,endY);

                    mPreX=event.getX();
                    mPreY=event.getY();

                    postInvalidate();
                    break;
        }
        return super.onTouchEvent(event);
    }

    public void reset(){
        mPath.reset();
        invalidate();

    }



}
