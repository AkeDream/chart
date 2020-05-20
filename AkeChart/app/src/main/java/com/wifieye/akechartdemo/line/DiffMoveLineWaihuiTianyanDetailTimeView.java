package com.wifieye.akechartdemo.line;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import static com.wifieye.akechartdemo.line.Dip.dip3;


public class DiffMoveLineWaihuiTianyanDetailTimeView extends View {

    private Paint mPaint;
    private int mTextSize;
    private Rect mRect = new Rect();

    private String[] mLable = {"时",};
    private String[] mTexts;
    private int[] mColors;

    private DiffContainerWaihuiTianyan mHolder;
    private int mTextColor = Color.WHITE;
    private Paint mPaintBg;

    /**
     * @param context
     */
    public DiffMoveLineWaihuiTianyanDetailTimeView(Context context) {
        super(context);
        init();
    }

    /**
     * @param context
     * @param attrs
     */
    public DiffMoveLineWaihuiTianyanDetailTimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public DiffMoveLineWaihuiTianyanDetailTimeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        readLookFaceColor();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mTextSize = FunctionHelper.sp2pxInt(8);
        mPaint.setTextSize(mTextSize);
        mPaintBg = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBg.setTextAlign(Paint.Align.CENTER);
    }

    public void setDetailData() {
        if (mHolder != null && mHolder.getDataModel() != null) {
            if (mHolder.getScreenIndex() < 0) {
                return;
            }
            if (mHolder.getDataModel() == null) {
                return;
            }
            double[][] details = mHolder.getTreadPriceView().getDetailByIndex(
                    mHolder.getScreenIndex());
            if (details == null) {
                return;
            }
            if (details != null && details.length > 0) {
                mTexts = new String[mLable.length];
                mColors = new int[mLable.length];
                mTexts[0] = FunctionHelper.getDay((int) details[0][0]);
                ;
                mColors[0] = mTextColor;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mHolder == null || mHolder.getDataModel() == null) {
            return;
        }
        int screenIndex = mHolder.getScreenIndex();
        if (mTexts == null || mColors == null || mLable == null || screenIndex == -1) {
            return;
        }
        if (mTexts.length < 1 || mLable.length < 1 || mColors.length < 1) {
            return;
        }
        canvas.save();
        int mLeftMargin = 0;
        if (mHolder.getDataModel() != null) {
            int len = mHolder.getDataModel().size();
            if (screenIndex >= 0 && screenIndex < len) {
                int width = mHolder.getTreadPriceViewWidth();
                int height = getHeight();
                int leftPadding = this.getPaddingLeft();
                if (leftPadding == 0) {
                    leftPadding = 2;
                }
                mPaint.setStyle(Style.STROKE);
                int total = mHolder.getDataModel().size();
                float x = mLeftMargin + leftPadding
                        + (width - 1 - 2 * mLeftMargin) * 1f * screenIndex
                        / total;
                x = mHolder.lastTouchX;
                if (x > width - mHolder.mRightNumber) {
                    x = width - mHolder.mRightNumber;
                }
                if (x < mHolder.mLeftNumber) {
                    x = mHolder.mLeftNumber;
                }
                int offY = (height - mTextSize) / 2;
                float xx = x;
//		            int y = 0;
//		            canvas.drawLine(xx, y, xx, mHolder.getKLineViewHeight(), mPaint);
                if (mTexts[0] == null) {
                    mTexts[0] = "";
                }
                mPaint.setColor(mTextColor);
                mPaint.getTextBounds(mTexts[0], 0, mTexts[0].length(), mRect);
                if (mRect.width() / 2 > xx) {
                    xx = dip3;
                    mPaint.setTextAlign(Paint.Align.LEFT);
                    int fontH = mRect.height();
                    int fontW = mRect.width();
                    mPaintBg.setStyle(Style.FILL);
                    mPaintBg.setColor(0xff585858);
                    canvas.drawRect(xx - dip3, 0, xx + fontW + dip3, height, mPaintBg);
                } else if ((width - xx) < mRect.width() / 2) {
                    xx = width - dip3;
                    mPaint.setTextAlign(Paint.Align.RIGHT);
                    int fontH = mRect.height();
                    int fontW = mRect.width();
                    mPaintBg.setStyle(Style.FILL);
                    mPaintBg.setColor(0xff585858);
                    canvas.drawRect(xx - fontW - dip3, 0, xx + dip3, height, mPaintBg);
                } else {
                    mPaint.setTextAlign(Paint.Align.CENTER);
                    int fontH = mRect.height();
                    int fontW = mRect.width();
                    mPaintBg.setStyle(Style.FILL);
                    mPaintBg.setColor(0xff585858);
                    canvas.drawRect(xx - fontW / 2 - dip3, 0, xx + fontW / 2 + dip3, height, mPaintBg);
                }
                canvas.drawText(mTexts[0], xx, offY - mPaint.getFontMetrics().ascent, mPaint);
            }
        }
        canvas.restore();
    }

    public void setHolder(DiffContainerWaihuiTianyan holder) {
        mHolder = holder;
    }

    private void readLookFaceColor() {
        if (mHolder == null || !mHolder.isNight()) {
            mTextColor = 0xffffffff;
            setBackgroundColor(Color.TRANSPARENT);
        } else {
            mTextColor = 0xff666666;
            setBackgroundColor(Color.TRANSPARENT);
        }
    }

    public void changeLookFace() {
        readLookFaceColor();
    }
}
