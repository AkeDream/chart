package com.wifieye.akechartdemo.line;


import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

import static com.wifieye.akechartdemo.line.Dip.dip35;


public abstract class StockChartBaseView extends View {

    protected final PathEffect EFFECT = new DashPathEffect(
            new float[]{3, 3, 3, 3}, 1);
    public int mOrientation = Configuration.ORIENTATION_PORTRAIT;
    public int mMarginLen = dip35;
    protected Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected int mLineCol = 0xffd6d6d6;
    protected boolean hasPaintBackground = false;

    /**
     * @param context
     */
    public StockChartBaseView(Context context) {
        super(context);
        init();
    }

    /**
     * @param context
     * @param attrs
     */
    public StockChartBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public StockChartBaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
    }

    protected void makeBackground(int width, int height) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            if (!hasPaintBackground) {
                paintBackground(canvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mOrientation = Configuration.ORIENTATION_PORTRAIT;
        } else {
            mOrientation = Configuration.ORIENTATION_LANDSCAPE;
        }
        makeBackground(w, h);
    }

    protected abstract void paintBackground(Canvas canvas);
}

