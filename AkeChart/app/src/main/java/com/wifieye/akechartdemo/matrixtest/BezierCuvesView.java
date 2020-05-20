package com.wifieye.akechartdemo.matrixtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Create by ake on 2019/11/23
 * Describe:
 */
public class BezierCuvesView  extends View {


    private Point controlPointOne = new Point(200, 200);
    private Point controlPointTwo = new Point(500, 200);

    private boolean isControlPointTwo ;

    private Paint paintBezier;
    private Paint paintLine;


    private Point point;

    public BezierCuvesView(Context context) {
        super(context);
        init();
    }

    public BezierCuvesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BezierCuvesView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        paintBezier = new Paint();
        paintBezier.setStyle(Paint.Style.STROKE);
        paintBezier.setColor(Color.BLACK);
        paintBezier.setStrokeWidth(10);


        paintLine = new Paint();
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setColor(Color.RED);
        paintLine.setStrokeWidth(3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path = new Path();
        path.moveTo(100, 500);
        path.cubicTo(controlPointOne.x, controlPointOne.y,controlPointTwo.x, controlPointTwo.y, 900, 500);
        //绘制路径
        canvas.drawPath(path, paintBezier);
        //绘制辅助点
        canvas.drawPoint(controlPointOne.x, controlPointOne.y, paintBezier);
        canvas.drawPoint(controlPointTwo.x, controlPointTwo.y, paintBezier);
        //绘制连线
//        canvas.drawLine(100, 500, controlPointOne.x, controlPointOne.y, paintLine);
//        canvas.drawLine(900, 500, controlPointOne.x, controlPointOne.y, paintLine);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_MOVE:
                if (isControlPointTwo){
                    controlPointOne.x = (int) event.getX();
                    controlPointOne.y = (int) event.getY();
                }else {
                    controlPointTwo.x = (int) event.getX();
                    controlPointTwo.y = (int) event.getY();
                }
                invalidate();
                break;

        }

        return true;
    }
}
