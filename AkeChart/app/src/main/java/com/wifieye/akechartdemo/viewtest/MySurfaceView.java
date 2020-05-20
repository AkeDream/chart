package com.wifieye.akechartdemo.viewtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Create by ake on 2019/12/10
 * Describe:
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback ,Runnable{

    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private boolean mIsDrawing;//控制绘画线程的标志位
    private Path mPath;
    private Paint mPaint;

    public MySurfaceView(Context context) {
        super(context);
        init();
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mHolder=getHolder();
        mHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);

        mPath=new Path();

        mPaint=new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        mPaint.setAntiAlias(true);

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        mIsDrawing=true;
        new Thread(this).start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing=false;
    }

    @Override
    public void run() {

        while (mIsDrawing){



            //取得更新之前的时间
            long startTime = System.currentTimeMillis();

            //在这里加上线程安全锁
            synchronized (mHolder){


                draw();

            }

            //取得更新结束时间
            long endTime = System.currentTimeMillis();

            //计算一次更新的毫秒数
            int diffTime = (int) (endTime - startTime);

            while (diffTime<=TIME_IN_FRAME){
                diffTime=(int)(System.currentTimeMillis()-startTime);
                Thread.yield();//线程等待
            }

        }

    }

    //没30帧刷新一次屏幕
    public static final int TIME_IN_FRAME=30;

    //绘图操作
    private void draw() {

        try {
            //draw 绘制过程
            mCanvas=mHolder.lockCanvas();

            mCanvas.drawColor(Color.WHITE);
            mCanvas.drawPath(mPath,mPaint);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);//保证每次都将绘图内容提交
            }
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x= (int) event.getX();
        int y= (int) event.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(x,y);
                break;
             case MotionEvent.ACTION_MOVE:
                 mPath.lineTo(x,y);
                 break;
            case MotionEvent.ACTION_UP:

                break;

        }

        return true;
    }



}
