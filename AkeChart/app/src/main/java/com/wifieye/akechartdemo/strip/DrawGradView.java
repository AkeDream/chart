package com.wifieye.akechartdemo.strip;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class DrawGradView extends View {
    private Paint paint;
    public DrawGradView(Context context) {
        super(context);
        initData();
    }

    public DrawGradView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public DrawGradView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    public DrawGradView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initData();
    }
    public void initData(){
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置画笔
        Paint paint=new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);






        //-----------------设置渐变后绘制------------------

        //Paint设置渐变器
        Shader mShader=new LinearGradient(0,0,40,60,
                new int[]{Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW},
                null, Shader.TileMode.REPEAT);
        paint.setShader(mShader);
        paint.setStyle(Paint.Style.FILL);
        //设置阴影
//        paint.setShadowLayer(45,10,10,Color.BLACK);

        canvas.drawRect(440,710,640,910,paint);
    }
}
