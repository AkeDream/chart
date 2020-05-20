package com.wifieye.akechartdemo.lineme;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.wifieye.akechartdemo.line.FunctionHelper;

import androidx.annotation.Nullable;

public class MoveLineDetailView  extends View {

    private MultContentLayout mHolder;
    private Paint mPaint;
    private int mTextSize;

    private String[] mStrs = {"时间:", "点差:",};
    private Rect rect;

    public MoveLineDetailView(Context context) {
        super(context);
        init();
    }
    public MoveLineDetailView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextSize= FunctionHelper.sp2pxInt(10);
        mPaint.setTextSize(mTextSize);

        rect=new Rect();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);



    }

    public void setHolder(MultContentLayout contentLayout){
        mHolder=contentLayout;
    }

}
