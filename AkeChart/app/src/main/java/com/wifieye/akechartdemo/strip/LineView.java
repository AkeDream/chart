package com.wifieye.akechartdemo.strip;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Create by ake on 2019/12/9
 * Describe:
 */
public class LineView extends View {

    private Paint mPaint;

    private Paint arcPaint;


    public LineView(Context context) {
        super(context);
        init();

    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mPaint=new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GREEN);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(50);

        arcPaint=new Paint();
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setColor(Color.BLUE);
        arcPaint.setAntiAlias(true);
        arcPaint.setStrokeWidth(70);



    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStrokeCap(Paint.Cap.ROUND);
//        canvas.drawLine(100,200,400,200,mPaint);

        RectF rectF = new RectF(100, 100, getWidth()-100, getHeight()-100);
        canvas.drawArc(rectF,0,352,false,mPaint);

    }
}
