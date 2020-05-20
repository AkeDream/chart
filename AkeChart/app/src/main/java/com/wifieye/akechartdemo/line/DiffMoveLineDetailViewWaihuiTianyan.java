package com.wifieye.akechartdemo.line;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import static com.wifieye.akechartdemo.line.Dip.dip3;


public class DiffMoveLineDetailViewWaihuiTianyan extends View {

    private DiffContainerWaihuiTianyan mHolder;
    private Paint mPaint;
    private int mTextSize;

    private Rect mRect = new Rect();
    private String[] mStrs =
            {"时间:",
                    "点差:",};
    private int mTextColor = Color.WHITE;
    private int mConstTextColor = 0xFF222222;

    /**
     * @param context
     */
    public DiffMoveLineDetailViewWaihuiTianyan(Context context) {
        super(context);
        init();
    }

    /**
     * @param context
     * @param attrs
     */
    public DiffMoveLineDetailViewWaihuiTianyan(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public DiffMoveLineDetailViewWaihuiTianyan(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mTextSize = FunctionHelper.sp2pxInt(10);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(mTextSize);
        changeLookFace();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mHolder == null || mHolder.getDataModel() == null || mHolder.getDataModel().size() <= 0) {
            return;
        }
        int ii = mHolder.getScreenIndex();
        if (ii < 0) {
            ii = mHolder.getDataModel().size() - 1;
        }
        int width = getWidth();
        int height = getHeight();
        double[][] details = mHolder.getTreadPriceView()
                .getDetailByIndex(ii);
        if (details == null || details.length < mStrs.length) {
            return;
        }
        mPaint.setTextSize(mTextSize);
        mPaint.setTextAlign(Align.LEFT);
        String value = "";
        int tmpx = dip3;
        int tmpy = (height - mTextSize) / 2;
        int ascent = (int) mPaint.getFontMetrics().ascent;

        mPaint.setColor(mConstTextColor);
        //canvas.drawText("单位:", tmpx, tmpy - ascent, mPaint);
        mPaint.getTextBounds("单位:", 0, "单位:".length(), mRect);
        tmpx += dip3 + mRect.width();

        mPaint.setColor(0xff359ee0);
        //canvas.drawText("℃", tmpx, tmpy - ascent, mPaint);
        mPaint.getTextBounds("℃", 0, "℃".length(), mRect);
        tmpx += dip3 + mRect.width() + dip3;

        mPaint.setTextAlign(Align.RIGHT);
        tmpx = width - dip3;

        mPaint.setColor(0xff359ee0);
        value = StockChartUtility.formatPrice(details[1][0], mHolder.getDataModel().getmRealLen());
        canvas.drawText(value, tmpx, tmpy - ascent, mPaint);
        mPaint.getTextBounds(value, 0, value.length(), mRect);
        tmpx -= mRect.width() + dip3;

        mPaint.setColor(mConstTextColor);
        if (mHolder.isTraderEnv) {
            mStrs[1] = mHolder.dataName1 + ":";
            canvas.drawText(mStrs[1], tmpx, tmpy - ascent, mPaint);
        } else if (mHolder.isBusinessEffect) {
            mStrs[1] = mHolder.dataName1 + ":";
            canvas.drawText(mStrs[1], tmpx, tmpy - ascent, mPaint);
        } else {
            mStrs[1] = "点差:";
            canvas.drawText(mStrs[1], tmpx, tmpy - ascent, mPaint);
        }
        mPaint.getTextBounds(mStrs[1], 0, mStrs[1].length(), mRect);
        tmpx -= mRect.width() + dip3;

        mPaint.setColor(mConstTextColor);
        value = FunctionHelper.getDay((int) details[0][0]);
        if (mHolder.isTraderEnv) {
            value = mHolder.getTreadPriceView().getDetailTimeByIndex(ii);
        } else if (mHolder.isBusinessEffect) {
            value = mHolder.getTreadPriceView().getDetailTimeByIndex(ii);
        }
        canvas.drawText(value, tmpx, tmpy - ascent, mPaint);
        mPaint.getTextBounds(value, 0, value.length(), mRect);
        tmpx -= dip3 + mRect.width() + dip3;
    }

    public void setHolder(DiffContainerWaihuiTianyan holder) {
        mHolder = holder;
    }

    public void changeLookFace() {
        if (mHolder == null || !mHolder.isNight()) {
            mTextColor = 0xff858c93;
            setBackgroundColor(Color.TRANSPARENT);
            mConstTextColor = 0xFF222222;
        } else {
            mTextColor = Color.WHITE;
            setBackgroundColor(Color.TRANSPARENT);
            mConstTextColor = 0xfff3f3f7;
        }
        postInvalidate();
    }

}

