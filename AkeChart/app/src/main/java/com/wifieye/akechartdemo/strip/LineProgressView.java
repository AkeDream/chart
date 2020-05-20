package com.wifieye.akechartdemo.strip;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

/**
 * Create by ake on 2019/11/18
 * Describe:
 */
public class LineProgressView extends View {

    //线条高度
    private float line_height=20;
    //每个颜色块的宽度
    private float item_width=80;
    //每两个颜色快之间的间距
    private float separation_width=0;
    //平行四边形倾斜的程度
    private float lean_degree=25;
    //第一种颜色块的颜色
    private int first_color=Color.GREEN;
    //第二种颜色块的颜色
    private int second_color=Color.RED;
    //线条底色
    private int canvas_color=Color.WHITE;

    private Paint mPaint;

    private Path path;

    private int mDx=10;

    public LineProgressView(Context context) {
        this(context,null);
    }

    public LineProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LineProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {


        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
        path=new Path();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path path = new Path();
        int lineWidth = getWidth();
        int lineHeight = getHeight();
        int count = (item_width + separation_width == 0) ? 0 : lineWidth / (int) (item_width + separation_width) + 1;

        for(int i=0; i < count; i++){
            canvas.save();
            path.reset();//重置路径
            path.moveTo(lean_degree + (item_width + separation_width) * i, 0);//左上点
            path.lineTo((item_width + separation_width) * i, lineHeight);//左下点
            path.lineTo(item_width * (i + 1) + separation_width * i, lineHeight);//右下点
            path.lineTo(lean_degree + item_width * (i + 1) + separation_width * i, 0);//右上点
//            canvas.clipPath(path);//截取路径所绘制的图形
            if(i % 2 == 0){
                mPaint.setColor(Color.GREEN);
//                canvas.drawColor(first_color);
            }else{
//                canvas.drawColor(second_color);
                mPaint.setColor(Color.RED);
            }
            canvas.drawPath(path,mPaint);
            canvas.restore();
        }

        scrollTo(mDx,0);
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
