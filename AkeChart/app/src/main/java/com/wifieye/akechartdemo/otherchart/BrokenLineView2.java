package com.wifieye.akechartdemo.otherchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by ake on 2019/12/30
 * Describe:
 */
public class BrokenLineView2 extends View {

    private Paint whitePaint,bluePaint,backPaint,textPaint,dashPaint;

    private int mWidth;
    private int mHeight;

    private int pdLeft=dp2px(80);
    private int pdBottom=dp2px(50);
    private int pdRight=dp2px(30);
    private int pdTop=dp2px(30);

    private int originX;
    private int originY;

    private int scaleItem=dp2px(40);//x轴单位长度

    private int itemExtra=scaleItem/3;//单位偏移

    private int lineLength=dp2px(5);

    private int firstPositionX;
    private int firstMinX; // 移动时第一个点的最小x值
    private int firstMaxX; //移动时第一个点的最大x值

    private Integer[] yLabels= {1,6,11,16,21,26};
    private float maxY=26f;
    private float minY=1f;
    private List<String> xValues=new ArrayList<>();
    private List<Float> yValues=new ArrayList<>();

    private GestureDetector gestureDetector;

    public List<String> getxValues() {
        return xValues;
    }

    public void setxValues(List<String> xValues) {
        this.xValues = xValues;
    }

    public List<Float> getyValues() {
        return yValues;
    }

    public void setyValues(List<Float> yValues) {
        this.yValues = yValues;
    }

    public BrokenLineView2(Context context) {
        super(context);
        init();
    }

    public BrokenLineView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        gestureDetector=new GestureDetector(getContext(),new MyGesture());

        whitePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        whitePaint.setStyle(Paint.Style.STROKE);
        whitePaint.setColor(Color.WHITE);
        whitePaint.setStrokeWidth(2);

        bluePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        bluePaint.setStrokeWidth(2);
        bluePaint.setColor(Color.BLUE);
        bluePaint.setStyle(Paint.Style.STROKE);

        textPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(24);
        textPaint.setStyle(Paint.Style.FILL);

        dashPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        dashPaint.setColor(Color.WHITE);
        dashPaint.setStrokeWidth(2);
        dashPaint.setStyle(Paint.Style.STROKE);
        dashPaint.setPathEffect(new DashPathEffect(new float[]{30f,10f},0));

        backPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        backPaint.setColor(Color.parseColor("#272727"));
        backPaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mWidth=getWidth();
        mHeight=getHeight();

        originX=pdLeft;
        originY=mHeight-pdBottom;

        firstPositionX=originX+itemExtra;
        firstMinX = mWidth - originX - (xValues.size() - 1) * scaleItem - itemExtra;
        firstMaxX = firstPositionX;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawX(canvas);
        drawLine(canvas);
        drawY(canvas);

    }

    private void drawLine(Canvas canvas) {


        int yLine= (int) (((yLabels.length-1)*scaleItem)/(maxY-minY));
        Path path = new Path();
        path.moveTo(firstPositionX,mHeight-pdBottom-itemExtra-(yValues.get(0)-1)*yLine);
        for (int i = 1; i < yValues.size(); i++) {

            path.lineTo(firstPositionX+scaleItem*i,mHeight-pdBottom-itemExtra-(yValues.get(i)-1)*yLine);

        }

        canvas.drawPath(path,bluePaint);


        //将折线超出x轴坐标的部分截取掉（左边）
        backPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        RectF rectF = new RectF(0, 0, originX, mHeight);
        canvas.drawRect(rectF, backPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (xValues.size()<5){
            return false;
        }
        gestureDetector.onTouchEvent(event);
        return true;
    }

    private void drawX(Canvas canvas) {

        Path path = new Path();
        path.moveTo(originX,originY);
        path.lineTo(mWidth-pdRight,originY);
        canvas.drawPath(path,whitePaint);

        for (int i = 0; i < xValues.size(); i++) {

            //刻度
            canvas.drawLine(firstPositionX+scaleItem*i,originY,firstPositionX+scaleItem*i,originY-lineLength,whitePaint);
            //字
            canvas.drawText(xValues.get(i),firstPositionX+scaleItem*i-getTextPaint(xValues.get(i),textPaint)/2,
                    originY+dp2px(15),textPaint);

        }

        //箭头
        canvas.drawLine(mWidth-pdRight,originY,mWidth-pdRight-dp2px(4),originY-dp2px(4),whitePaint);
        canvas.drawLine(mWidth-pdRight,originY,mWidth-pdRight-dp2px(4),originY+dp2px(4),whitePaint);

        //虚线


    }

    private void drawY(Canvas canvas) {



        canvas.drawLine(originX,originY,originX,mHeight-pdBottom-yLabels.length*scaleItem-itemExtra,whitePaint);

        for (int i = 0; i < yLabels.length; i++) {

            canvas.drawLine(originX,mHeight-pdBottom-itemExtra-i*scaleItem,
                    originX+lineLength,mHeight-pdBottom-itemExtra-i*scaleItem,whitePaint);

            canvas.drawText(yLabels[i].toString(),originX-getTextPaint(yLabels[i].toString(),textPaint)-dp2px(6)
                    ,mHeight-pdBottom-itemExtra-i*scaleItem,textPaint);


            Path path = new Path();
            path.moveTo(originX,mHeight-pdBottom-itemExtra-i*scaleItem);
            path.lineTo(mWidth-pdRight,mHeight-pdBottom-itemExtra-i*scaleItem);
            canvas.drawPath(path,dashPaint);

        }

        canvas.drawLine(originX,mHeight-pdBottom-itemExtra-6*scaleItem,originX-dp2px(4),
                mHeight-pdBottom-itemExtra-6*scaleItem+dp2px(4),whitePaint);
        canvas.drawLine(originX,mHeight-pdBottom-itemExtra-6*scaleItem,originX+dp2px(4),
                mHeight-pdBottom-itemExtra-6*scaleItem+dp2px(4),whitePaint);

        // 截取折线超出部分（右边）
        backPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        RectF rectF = new RectF(mWidth - pdRight, 0, mWidth, mHeight);
        canvas.drawRect(rectF, backPaint);

    }

    private class  MyGesture extends GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (e1.getX() > originX && e1.getX() < mWidth - pdRight &&
                    e1.getY() > pdTop && e1.getY() < mHeight - pdBottom) {
                //注意：这里的distanceX是e1.getX()-e2.getX()
                Log.i("tag", "onScroll distanceX : "+distanceX);
                distanceX = -distanceX;
                if (firstPositionX + distanceX > firstMaxX) {
                    firstPositionX = firstMaxX;
                } else if (firstPositionX + distanceX < firstMinX) {
                    firstPositionX = firstMinX;
                } else {
                    firstPositionX = (int) (firstPositionX + distanceX);
                }
                invalidate();
            }

            return false;
        }

    }

    private int getTextPaint(String text,Paint textPaint){

        float v = textPaint.measureText(text);
        return (int) v;

    }

    private int dp2px(int dpValue){

        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (density*dpValue+0.5f);

    }

    private int sp2px(int spValue){

        float scaledDensity = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (scaledDensity*spValue+0.5f);

    }

}
