package com.wifieye.akechartdemo.matrixtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.wifieye.akechartdemo.R;

/**
 * Create by ake on 2019/11/23
 * Describe:
 */
public class MatrixView extends View {

    private Bitmap bitmap;
    private Paint mPaint;
    private Matrix matrix;

    public MatrixView(Context context) {
        super(context);
        init();
    }



    public MatrixView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MatrixView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        bitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);

        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        matrix = new Matrix();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.drawBitmap(bitmap,matrix,mPaint);
        matrix.setTranslate(300,1000);
//        matrix.setScale(2,1);
//        matrix.setRotate(20);
        matrix.postRotate(10,bitmap.getWidth()/2,bitmap.getHeight()/2);
        canvas.drawBitmap(bitmap,matrix,mPaint);



    }
}
