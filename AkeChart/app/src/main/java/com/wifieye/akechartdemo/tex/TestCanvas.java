package com.wifieye.akechartdemo.tex;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.wifieye.akechartdemo.R;

/**
 * Create by ake on 2019/11/13
 * Describe:
 */
public class TestCanvas extends View {

    private Paint mPaint;

    public TestCanvas(Context context) {
        super(context);
        init();
    }

    public TestCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint=new Paint();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setTextSize(40);
        mPaint.setAntiAlias(true);
//        canvas.drawRect(50,50,250,250,mPaint);
//// 保存画布
//        canvas.save();
//        canvas.saveLayer(200, 200, 260, 260, null, Canvas.ALL_SAVE_FLAG);
//        // 旋转画布
//
//        mPaint.setColor(Color.BLUE);
//        canvas.drawRect(200, 200, 240, 240, mPaint);
//        canvas.rotate(30);
//        // 还原画布
//        canvas.restore();
//
//        mPaint.setColor(Color.DKGRAY);
//        canvas.drawCircle(150,150,40,mPaint);
//
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
////        canvas.scale(0.1f,5f,bitmap.getHeight(),bitmap.getHeight());
////        canvas.rotate(50,90,90);
////        canvas.translate(13,79);
//        canvas.skew(0.5f,0);
//        canvas.drawBitmap(bitmap,0,0,null);
//
//        Path path=new Path();
//        path.moveTo(10,10);
//        path.lineTo(100,100);
//        path.rLineTo(50,250);
//        path.lineTo(10,10);
//        path.close();
//        canvas.drawPath(path,mPaint);


        //设置Paint
//        Paint paint = new Paint();
//        paint.setColor(Color.RED);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(10f);
////设置Path
//        Path path = new Path();
////屏幕左上角（0,0）到（200,400）画一条直线
//        path.lineTo(200, 400);
////(200, 400)到（400,600）画一条直线
//        path.lineTo(400, 600);
////以（400,600）为起始点（0,0）偏移量为（400,600）画一条直线。
////其终点坐标实际在屏幕的位置为（800,1200）
//        path.rLineTo(400, 600);
//        canvas.drawPath(path, paint);

        //初始化Paint
//        Paint paint = new Paint();
//        paint.setColor(Color.RED);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(10f);
////初始化Path
//        Path path = new Path();
////将坐标系原点从（0,0）移动到（100,100）
//        path.moveTo(100, 100);
////画从（100,100）到（400,400）之间的直线
//        path.lineTo(300,300);
//
//        path.lineTo(400, 400);
////path.rMoveTo(0, 100); //临时凝视
//
//        path.setLastPoint(50, 800); //死亡凝视
//        path.lineTo(400, 800);
//        canvas.drawPath(path, mPaint);

//        Path path=new Path();
//        path.addCircle(500,500,300, Path.Direction.CCW);
//
//        canvas.drawTextOnPath("他大爷的 想死吗",path,0,0,mPaint);
//        canvas.drawPath(path,mPaint);

//        Path path = new Path();
//        //以(400,200)为圆心 100位半径绘制圆
//        path.addCircle(400,200,100, Path.Direction.CW);
//
//        //绘制椭圆
//        RectF rectF = new RectF(100, 350, 500, 600);
//        //第一种方法绘制椭圆
//        path.addOval(rectF, Path.Direction.CW);
//        //第二种方法绘制椭圆
//        path.addOval(600,350,10000,600, Path.Direction.CW);
//
//        //绘制矩形
//        RectF rect = new RectF(100, 650, 500, 900);
//        path.addRect(rect, Path.Direction.CW);
//        path.addRect(600,650,10000,900, Path.Direction.CCW);
//
//        //绘制圆角矩形
//        RectF roundRect = new RectF(100,950,300,1100);
//        path.addRoundRect(roundRect,20,20, Path.Direction.CW);
//        path.addRoundRect(350,950,550,1100,10,50, Path.Direction.CW);
//        //float[] radii 中有8个值，依次为左上角，右上角，右下角，左下角的 rx ,ry
//        RectF roundRectT = new RectF(600, 950, 800, 1100);
//        path.addRoundRect(roundRectT,new float[]{50,50,50,50,50,50,0,0}, Path.Direction.CW);
//        path.addRoundRect(850, 950, 1050, 1100,new float[]{0, 0, 0, 0,50, 50, 50, 50}, Path.Direction.CCW);
//        canvas.drawPath(path,mPaint);




    }
}
