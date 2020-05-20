package com.wifieye.akechartdemo.scale;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Create by ake on 2019/11/13
 * Describe:
 */
public class PointerScaleView extends View {

    private Paint mPaint;

    //view 实际宽高
    private int viewWidth;
    private int viewHeight;

    private int contentWidth;//绘制内容宽度

    private int outCircleWidth;//外环宽度

    private int outCircleRadius;//外环半径

    private int startAngle;//开始角度

    private int sweepAngle;//扫过角度

    private int [] centerPoint=new int[2];//中心坐标

    private int centerRadius;//中心蓝色小圆半径

    private int centerRingWidth;//中心圆环 宽度

    private String  color_center_blue="#469ee9";//蓝色 中心圆 动态圆环 小圆点

    private String color_center_white="#ffffff";//白色 中心园环

    private String color_outcircle = "#e5e5e5";//外环颜色

    private int indicateNum=7;//指示小圆点个数

    private int indicateRadius;//指示器半径

    private String color_tv_percent="#29292d";//文字百分比颜色

    private String color_tv_name="#bcbcbc";//文字名称颜色

    private String color_pointer_bg="#ff9900";//指针背景色

    /**
     * 当前进度
     */
    private int progress = 50;

    public PointerScaleView(Context context) {
        this(context,null);
    }

    public PointerScaleView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public PointerScaleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mPaint=new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        startAngle=138;
        sweepAngle=264;

    }

    /**
     * 设置当前进度值
     * @param progress
     */
    public void setProgress(int progress){
        this.progress=progress;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initValues();

    }

    private void initValues() {

        viewWidth=getMeasuredWidth();
        viewHeight=getMeasuredHeight();

        contentWidth=viewWidth>viewHeight?viewWidth:viewHeight;

        centerPoint[0]=viewWidth/2;
        centerPoint[1]=viewHeight/2;

        outCircleWidth=30;
        outCircleRadius = contentWidth / 2 - outCircleWidth;

        centerRadius=40;
        centerRingWidth=26;

        indicateRadius=12;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawOutCircleBg(startAngle,sweepAngle,outCircleWidth,outCircleRadius,color_outcircle,canvas);


        drawIndicateCircle(startAngle,sweepAngle,outCircleRadius-outCircleWidth/2-30,indicateRadius,color_center_blue,canvas);

        drawProgress(progress, canvas);
        drawCurrentTv(progress,canvas);

        drawPointer(canvas);
        drawCenterCircle(centerRadius,0,color_center_blue, Paint.Style.FILL,canvas);
        drawCenterCircle(centerRadius+centerRingWidth/2,centerRingWidth,color_center_white, Paint.Style.STROKE,canvas);

    }

    /**
     * 绘制当前进度 圆环
     * @param progress
     * @param canvas
     */
    private void drawProgress(int progress, Canvas canvas) {

        int currentAngle=sweepAngle*progress/100;
        mPaint.setStrokeWidth(outCircleWidth);
        mPaint.setColor(Color.parseColor(color_center_blue));
        mPaint.setStyle(Paint.Style.STROKE);
        RectF rectF = new RectF(centerPoint[0] - outCircleRadius, centerPoint[1] - outCircleRadius,
                centerPoint[0] + outCircleRadius, centerPoint[1] + outCircleRadius);
        canvas.drawArc(rectF,startAngle,currentAngle,false,mPaint);

        drawArcRounce(outCircleRadius, startAngle, outCircleWidth, canvas);
        drawArcRounce(outCircleRadius,startAngle+currentAngle,outCircleWidth,canvas);

    }

    /**
     * 画指针以及他的背景
     *
     * @param canvas
     */
    private void drawIndicator( Canvas canvas) {
        drawPointer(canvas);
    }

    /**
     * 指针的最远处的半径和刻度线的一样
     */
    private void drawPointer(Canvas canvas) {
        RectF rectF = new RectF(centerPoint[0] - (int) (outCircleRadius / 3f / 2 / 2),
                centerPoint[1] - (int) (outCircleRadius / 3f / 2 / 2), centerPoint[0] + (int) (outCircleRadius / 3f / 2 / 2), centerPoint[1] + (int) (outCircleRadius / 3f / 2 / 2));
        int angle = (int) ((sweepAngle) / (100 * 1f) * progress) + startAngle;
        //指针的定点坐标
        float[] peakPoint = getPointFromRadiusAndAngle(outCircleRadius-outCircleWidth/2-60,angle);
        //顶点朝上，左侧的底部点的坐标
        float[] bottomLeft = getPointFromRadiusAndAngle( (int) (outCircleRadius / 3f / 2 / 2),angle - 90);
        //顶点朝上，右侧的底部点的坐标
        float[] bottomRight = getPointFromRadiusAndAngle( (int) (outCircleRadius / 3f / 2 / 2),angle + 90);
        Path path = new Path();
//        mPaint.setColor(Color.parseColor(color_pointer_bg));
//        path.moveTo(centerPoint[0], centerPoint[1]);
//        path.lineTo(peakPoint[0], peakPoint[1]);
//        path.lineTo(bottomLeft[0], bottomLeft[1]);
//        path.close();
//        canvas.drawPath(path, mPaint);
////        canvas.drawArc(rectF, angle - 180, 100, true, mPaint);
//        Log.e("InstrumentView", "drawPointer" + angle);
//
//
//        mPaint.setColor(Color.parseColor(color_pointer_bg));
//        path.reset();
//        path.moveTo(centerPoint[0], centerPoint[1]);
//        path.lineTo(peakPoint[0], peakPoint[1]);
//        path.lineTo(bottomRight[0], bottomRight[1]);
//        path.close();
//        canvas.drawPath(path, mPaint);

//        canvas.drawArc(rectF, angle + 80, 100, true, mPaint);


        mPaint.setColor(Color.parseColor(color_pointer_bg));
        path.moveTo(centerPoint[0], centerPoint[1]);
        path.lineTo(bottomLeft[0], bottomLeft[1]);
        path.lineTo(peakPoint[0], peakPoint[1]);
        path.lineTo(bottomRight[0], bottomRight[1]);
        path.close();
        canvas.drawPath(path, mPaint);


    }


    /**
     * 绘制 圆环背景
     * @param startAngle
     * @param allAngle
     * @param width
     * @param radius
     * @param color
     * @param canvas
     */
    private void drawOutCircleBg(int startAngle, int allAngle, int width, int radius, String color, Canvas canvas) {

        mPaint.setStrokeWidth(width);//并不是往圆内增加圆环宽度，而是内侧增加一半 外侧增加一半
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor(color));
        RectF rectF = new RectF(centerPoint[0] - radius, centerPoint[1] - radius, centerPoint[0] + radius, centerPoint[1] + radius);
        canvas.drawArc(rectF, startAngle, allAngle, false, mPaint);
        drawArcRounce(radius, startAngle, width, canvas);
        drawArcRounce(radius,startAngle+allAngle,width,canvas);
    }

    /**
     * 绘制中心圆 和圆环
     * @param radius
     * @param canvas
     */
    private void drawCenterCircle(int radius,int width,String color,Paint.Style style,Canvas canvas){

        mPaint.setStrokeWidth(width);
        mPaint.setStyle(style);
        mPaint.setColor(Color.parseColor(color));
        canvas.drawCircle(centerPoint[0],centerPoint[1],radius,mPaint);

    }


    /**
     *  绘制 内侧7个 指示小蓝点
     * @param startAngle
     * @param allAngle
     * @param radius
     * @param width
     * @param color
     * @param canvas
     */
    private void drawIndicateCircle(int startAngle, int allAngle,  int radius,int width, String color, Canvas canvas){

        mPaint.setColor(Color.parseColor(color));
        mPaint.setStrokeWidth(0);
        mPaint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < 7; i++) {

            int angle=startAngle+allAngle/6*i;
            if (angle<=(startAngle+progress*allAngle/100)){
                float[] pointS = getPointFromRadiusAndAngle(radius, angle);
                canvas.drawCircle(pointS[0],pointS[1],width,mPaint);
            }

        }

    }

    /**
     *  绘制当前tv 值
     * @param progress
     * @param canvas
     */
    private void drawCurrentTv(int progress,Canvas canvas){

        mPaint.setTextSize(38);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor(color_tv_percent));
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float baseLine1 = centerPoint[1] + (outCircleRadius / 20f * 11 - fontMetrics.top - fontMetrics.bottom);
        canvas.drawText(progress+"%",centerPoint[0],baseLine1,mPaint);

        mPaint.setTextSize(30);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(Color.parseColor(color_tv_name));
        float baseLine2 =centerPoint[1]  + outCircleRadius  + (fontMetrics.bottom + fontMetrics.top) ;
        canvas.drawText( "交易速度", centerPoint[0], baseLine2, mPaint);

    }


    /**
     *  绘制圆环两端圆角
     * @param radius
     * @param angle
     * @param width
     * @param canvas
     */
    private void drawArcRounce(int radius, int angle, int width, Canvas canvas) {

        float[] centerPoint = getPointFromRadiusAndAngle(radius, angle);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(0);
        canvas.drawCircle(centerPoint[0],centerPoint[1],width/2,mPaint);

    }

    /**
     *  根据半径和角度获取坐标
     */
    public float [] getPointFromRadiusAndAngle(int radius,int angle){

        float x= (float) (Math.cos(angle*Math.PI/180)*radius+centerPoint[0]);
        float y= (float) (Math.sin(angle*Math.PI/180)*radius+centerPoint[1]);

        return new float[]{x,y};

    }


}
