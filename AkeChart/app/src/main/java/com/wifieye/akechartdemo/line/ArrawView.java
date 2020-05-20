package com.wifieye.akechartdemo.line;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import static com.wifieye.akechartdemo.line.Dip.dip1;


public class ArrawView extends View {

    protected Paint mPaint;
    private int mTextSize;
    private int color = Color.WHITE;
    private Rect mRect = new Rect();
    private Path mPricePath = new Path();

    private ArrawType type = ArrawType.ARRAW_DOWN;

    public ArrawView(Context context) {
        super(context);
        init();
    }

    public ArrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ArrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextSize = FunctionHelper.sp2pxInt(12);
        mPaint.setTextSize(mTextSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        int width = getWidth();
        int height = getHeight();
        if (type == ArrawType.ARRAW_DOWN) {
            mPricePath.reset();
            mPricePath.moveTo(0 + dip1 / 2, 0);
            mPricePath.lineTo(width / 2, height - dip1 / 2);
            mPricePath.lineTo(width - dip1 / 2, 0);
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Style.STROKE);
            mPaint.setStrokeWidth(dip1);
            mPaint.setColor(color);
            canvas.drawPath(mPricePath, mPaint);
        } else if (type == ArrawType.ARRAW_LEFT_SOLID) {
            mPricePath.reset();
            mPricePath.moveTo(0 + dip1 / 2, height / 2);
            mPricePath.lineTo(width - dip1 / 2, height - dip1 / 2);
            mPricePath.lineTo(width - dip1 / 2, 0 + dip1 / 2);
            mPricePath.close();
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Style.FILL_AND_STROKE);
            mPaint.setStrokeWidth(dip1);
            mPaint.setColor(color);
            canvas.drawPath(mPricePath, mPaint);
        } else if (type == ArrawType.ARRAW_RIGHT_SOLID) {
            mPricePath.reset();
            mPricePath.moveTo(0 + dip1 / 2, 0 + dip1 / 2);
            mPricePath.lineTo(0 + dip1 / 2, height - dip1 / 2);
            mPricePath.lineTo(width - dip1 / 2, height / 2);
            mPricePath.close();
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Style.FILL_AND_STROKE);
            mPaint.setStrokeWidth(dip1);
            mPaint.setColor(color);
            canvas.drawPath(mPricePath, mPaint);
        } else if (type == ArrawType.ARRAW_UP_SOLID) {
            mPricePath.reset();
            mPricePath.moveTo(width / 2, 0 + dip1 / 2);
            mPricePath.lineTo(0 + dip1 / 2, height - dip1 / 2);
            mPricePath.lineTo(width - dip1 / 2, height - dip1 / 2);
            mPricePath.close();
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Style.FILL_AND_STROKE);
            mPaint.setStrokeWidth(dip1);
            mPaint.setColor(color);
            canvas.drawPath(mPricePath, mPaint);
        } else if (type == ArrawType.ARRAW_DOWN_SOLID) {
            mPricePath.reset();
            mPricePath.moveTo(0 + dip1 / 2, 0);
            mPricePath.lineTo(width / 2, height - dip1 / 2);
            mPricePath.lineTo(width - dip1 / 2, 0);
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Style.FILL_AND_STROKE);
            mPaint.setStrokeWidth(dip1);
            mPaint.setColor(color);
            canvas.drawPath(mPricePath, mPaint);
        }
        canvas.restore();
    }

    public void setType(ArrawType type) {
        this.type = type;
        postInvalidate();
    }

    public void setColor(int color) {
        this.color = color;
        postInvalidate();
    }

    public enum ArrawType {
        ARRAW_DOWN, ARRAW_UP, ARRAW_LEFT, ARRAW_RIGHT, ARRAW_DOWN_SOLID, ARRAW_UP_SOLID, ARRAW_LEFT_SOLID, ARRAW_RIGHT_SOLID
    }
}

