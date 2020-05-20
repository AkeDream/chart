package com.wifieye.akechartdemo.viewtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Create by ake on 2019/12/11
 * Describe:
 */
public class SurfaceViewTemplate extends SurfaceView implements Runnable {
    private Thread mThread;
    //多线程的同步
    private volatile boolean isRunning;
    //画笔
    private Paint mPaint;

    //定义圆的属性
    private int mMinRadius;
    private int mMaxRadius;
    private int mRadius;
    private int mFlag;

    public SurfaceViewTemplate(Context context, AttributeSet attrs) {
        super(context, attrs);

        /*
         * 三、如何使用SurfaceView
         1、获取SurfaceHolder对象
         2、监听Surface的创建
         3、开启异步线程进行while循环（子线程可以不停地绘制）
         4、通过SurfaceHolder获取Canvas进行绘制
         */
        //1.获取SurfaceHolder对象
        SurfaceHolder holder = getHolder();
        //2.监听Surface创建
        holder.addCallback(new SurfaceHolder.Callback() {


            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //让线程开启
                isRunning = true;
                //监听Surface创建完毕
                mThread = new Thread(SurfaceViewTemplate.this);
                //执行run方法
                mThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                isRunning = false;
            }
        });

        //SurfaceView设置属性
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);
        //初始化画笔
        initPaint();

    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setDither(true);
        //抗锯齿
        mPaint.setAntiAlias(true);
        //空心圆
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(6);
        mPaint.setColor(Color.GREEN);
    }

    /**
     * 控件大小发生改变时调用 系统调用
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //-20 随意写的
        mMaxRadius = Math.min(w, h) / 2 - 20;
        mRadius = mMinRadius = 30;

        Log.e("TAG", "onSizeChanged: "+mMaxRadius+"radius"+mRadius );
    }

    @Override
    public void run() {
        while (isRunning) {
            //不断的绘制
            drawSelf();
        }
    }

    /**
     * 3、开启异步线程进行while循环（子线程可以不停地绘制）
     * 4、通过SurfaceHolder获取Canvas进行绘制
     */
    private void drawSelf() {
        Canvas canvas = null;
        try {
            canvas = getHolder().lockCanvas();
            if (canvas != null) {
                drawCircle(canvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) {
                //释放canvas
                getHolder().unlockCanvasAndPost(canvas);
            }
        }
    }

    /**
     * 画圆的方法
     * 因为放到while里要不断循环绘制视图
     * 1.第一次先画个白色背景
     * 2.背景之上画个圆
     * 循环1.2 动态改变 圆的半径
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        //绘制背景(覆盖之前的圆)
        canvas.drawColor(Color.WHITE);
        //绘制圆
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mRadius, mPaint);
        //动态改变 圆的半径
        if (mRadius >= mMaxRadius) {
            mFlag = -1;
        } else if (mRadius <= mMinRadius) {
            mFlag = 1;
        }
        //达到最大值递减 2个像素 达到最小值递减2个像素
        mRadius += mFlag * 2;
        Log.e("TAG", "drawCircle: "+mMaxRadius+"radius"+mRadius );
    }
}
