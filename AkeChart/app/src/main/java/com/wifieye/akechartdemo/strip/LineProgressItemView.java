package com.wifieye.akechartdemo.strip;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.wifieye.akechartdemo.R;

/**
 * Create by ake on 2019/11/18
 * Describe:
 */
public class LineProgressItemView extends View {

    private Paint mPaint;

    private Paint mProPaint;

    private int backgroundColor; //背景色

    private int itemColorDeep; //深色背景

    private int itemColorTint; //浅色背景

    private int mWidth; // 总宽度

    private int mHight; // 总高度

    private int progress=60; //进度 最大100

    public LineProgressItemView(Context context) {
        this(context,null);
    }

    public LineProgressItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LineProgressItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LineProgressItemView);

        backgroundColor = typedArray.getColor(R.styleable.LineProgressItemView_item_background, Color.parseColor("#4f3452"));
        itemColorDeep=typedArray.getColor(R.styleable.LineProgressItemView_item_color_deep,Color.parseColor("#e14b78"));
        itemColorTint=typedArray.getColor(R.styleable.LineProgressItemView_item_color_tint,Color.parseColor("#f75e8c"));

        typedArray.recycle();


        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        mProPaint=new Paint();
        mProPaint.setAntiAlias(true);
        mProPaint.setStyle(Paint.Style.FILL);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth=getMeasuredWidth();
        mHight=getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBackgroud(canvas);
        drawItem(canvas);

    }

    private int mDx;
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

//        setAnimator();

    }

    //绘制条纹
    private void drawItem(Canvas canvas) {

        Shader gradient=new LinearGradient(0,0,20,20,new int[]{itemColorDeep,itemColorTint},
                new float[]{0.3f,0.7f}, Shader.TileMode.REPEAT);
        Matrix matrix = new Matrix();
        matrix.setTranslate(mDx,0);
        gradient.setLocalMatrix(matrix);
        mProPaint.setShader(gradient);

        Path path = new Path();
        path.moveTo(mHight/2,mHight);
        path.addArc(0,0,mHight,mHight,90,180);
        if (mWidth*progress/100>mHight){
            path.lineTo(mWidth*progress/100-mHight/2,0);
            path.addArc(mWidth*progress/100-mHight,0,mWidth*progress/100,
                    mHight,270,180);
            path.lineTo(mHight/2,mHight);
        }else {
            path.addArc(0,0,mHight,mHight,270,180);
        }
        path.close();
        canvas.drawPath(path,mProPaint);

    }

    //绘制背景
    private void drawBackgroud(Canvas canvas) {

        mPaint.setColor(backgroundColor);

        RectF rectF=new RectF(0,0,mWidth,mHight);
        Path path = new Path();
        path.reset(); // remember to reset path
        path.addRoundRect(rectF, mHight/2, mHight/2, Path.Direction.CW);
        path.close();
        canvas.drawPath(path,mPaint);

    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void setAnimator(){
        ValueAnimator animator = ValueAnimator.ofInt(0,20000*getMeasuredWidth());
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDx = (Integer) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(3000*20000);
        animator.start();
    }

}

