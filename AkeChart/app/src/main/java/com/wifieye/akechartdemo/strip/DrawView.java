package com.wifieye.akechartdemo.strip;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class DrawView extends View {

    public DrawView(Context context) {
        super(context);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*
         * 方法 说明 drawRect 绘制矩形 drawCircle 绘制圆形 drawOval 绘制椭圆 drawPath 绘制任意多边形
         * drawLine 绘制直线 drawPoin 绘制点
         */


        //设置画笔
        Paint paint=new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);



        //画文本
        canvas.drawText("画圆",20,30,paint);
        //画圆
        canvas.drawCircle(100,30,30,paint);

        //画线
        paint.setColor(Color.BLACK);
        canvas.drawText("画线及圆弧",10,80,paint);
        canvas.drawLine(10,90,310,90,paint);//直线
        canvas.drawLine(330,90,430,150,paint);//斜线


        //画圆弧及扇形
        RectF oval1=new RectF(10,160,110,260);
        paint.setColor(Color.GREEN);

        canvas.drawArc(oval1,180,180,false,paint);
        //第一个参数：范围  第二个参数：开始角度  第三个参数：圆弧度数
        //第四个参数：false---圆弧   true---扇形
        //第五个参数：画笔

        oval1.set( 120,160,220,260);
        canvas.drawArc(oval1,20,60,true,paint);

        oval1.set(230,160,330,260);
        canvas.drawArc(oval1,200,240,true,paint);


        //画矩形
        paint.setColor(Color.BLUE);
        canvas.drawRect(10,280,210,380,paint);

        //绘制圆角矩形
        RectF re1=new RectF(250,280,450,380);
        canvas.drawRoundRect(re1,15,15,paint);


        //画椭圆
        RectF re2=new RectF(10,400,210,500);
        canvas.drawOval(re2, paint);

        //画三角形
        paint.setColor(Color.DKGRAY);
        Path path=new Path();
        path.moveTo(70,520);
        path.lineTo(10,580);
        path.lineTo(130,580);
        path.close();
        canvas.drawPath(path,paint);



        //画点
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPoint(10,600,paint);//一个点
        canvas.drawPoints(new float[]{20,600,30,600,40,600},paint);//多个点


        //画贝塞尔曲线
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        Path path1=new Path();
        path1.moveTo(100,620);
        path1.quadTo(150,550,170,700);
        canvas.drawPath(path1,paint);



//        //画图片
//        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.pic6);
////        canvas.drawBitmap(bitmap,100,610,paint);//不限定图片大小  只指定左上角坐标
//        RectF rectF=new RectF(100,710,400,1010);
//        canvas.drawBitmap(bitmap,null,rectF,paint);//限定图片显示范围



        //-----------------设置渐变后绘制------------------

        //Paint设置渐变器
        Shader mShader=new LinearGradient(0,0,40,60,
                new int[]{Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW},
                null, Shader.TileMode.REPEAT);
        paint.setShader(mShader);
        paint.setStyle(Paint.Style.FILL);
        //设置阴影
        paint.setShadowLayer(45,10,10,Color.BLACK);

        canvas.drawRect(440,710,640,910,paint);

    }
}
