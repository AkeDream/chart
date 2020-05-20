package com.wifieye.akechartdemo.line;


import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;


import static com.wifieye.akechartdemo.line.Dip.dip35;
import static com.wifieye.akechartdemo.line.Dip.dip4;
import static com.wifieye.akechartdemo.line.Dip.dip5;


public class DiffMoveLineViewWaihuiTianyan extends View {

    protected final PathEffect EFFECT = new DashPathEffect(
            new float[]{2, 2}, 0);
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintNum = new Paint(Paint.ANTI_ALIAS_FLAG);
    private DiffContainerWaihuiTianyan mHolder;
    private int mLineColor;
    private float mLineWidth;
    private int mWidth;
    private int mHeight;
    private int mTextSize;
    private int mTextSizeReal;
    private Rect mRect = new Rect();

    /**
     * @param context
     */
    public DiffMoveLineViewWaihuiTianyan(Context context) {
        super(context);
        init();
    }

    /**
     * @param context
     * @param attrs
     */
    public DiffMoveLineViewWaihuiTianyan(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public DiffMoveLineViewWaihuiTianyan(Context context, AttributeSet attrs,
                                         int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void initColor() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mLineColor = 0xffb7b7b7;
        mLineWidth = 2.0f;
        mTextSize = FunctionHelper.sp2pxInt(12);
        mTextSizeReal = FunctionHelper.sp2pxInt(9);
        postInvalidate();
    }

    private void init() {
        initColor();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int mLeftMargin = 0;
        int mTopHeight = 0;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mLeftMargin = dip35;
            mTopHeight = dip5;
        }
        canvas.save();
        if (mHolder.getDataModel() != null) {
            int len = mHolder.getDataModel().size();
            int screenIndex = mHolder.getScreenIndex();
            if (screenIndex >= 0 && screenIndex < len) {
                int width = mHolder.getTreadPriceViewWidth();
                int height = getHeight();
                int leftPadding = this.getPaddingLeft();
                if (leftPadding == 0) {
                    leftPadding = 2;
                }
                mPaint.setStyle(Style.STROKE);
                double maxValue = mHolder.getDiffTreadPriceMaxValue();
                double minValue = mHolder.getDiffTreadPriceMinValue();
                double[] currentPrice = mHolder.getDiffTreadCurrentPrice();
                double gap = maxValue - minValue;
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
                double y = getTopPaddingByRatio(currentPrice[screenIndex]
                        - minValue, gap);
                mPaint.setColor(mLineColor);
                mPaint.setStrokeWidth(2.0f);
                mPaint.setPathEffect(EFFECT);
                int offsetH = 0;
                if (mHolder.isTraderEnv) {
                    canvas.drawLine(x, offsetH + + mHeight * 0.25f, x, offsetH + mHeight, mPaint);
                } else if (mHolder.isBusinessEffect) {
                    canvas.drawLine(x, offsetH + + mHeight * 0.25f, x, offsetH + mHeight, mPaint);
                } else {
                    canvas.drawLine(x, offsetH, x, offsetH + mHeight, mPaint);
                }
                canvas.drawLine(mLeftMargin, (float) y, width - mLeftMargin - mHolder.mRightNumber, (float) y, mPaint);

                if (mHolder.getScreenIndex() >= 0) {
                    double[][] details = mHolder.getTreadPriceView()
                            .getDetailByIndex(mHolder.getScreenIndex());
                    if (details != null) {
                        String value = StockChartUtility.formatPrice(
                                details[1][0], mHolder.getmRealLen());
                        mPaintNum.setStyle(Style.FILL);
                        mPaintNum.setTextSize(mTextSizeReal);
                        mPaintNum.setTextAlign(Align.LEFT);
                        mPaintNum.getTextBounds(value, 0, value.length(), mRect);
                        int fontH = mRect.height();
                        int fontW = mRect.width() + dip4;
                        if (y - fontH / 2 - 2 <= 0) {
                            y = fontH / 2 + 2;
                        } else if (y + fontH / 2 + 2 > height) {
                            y = y - fontH / 2 - 2;
                        }

                        mPaintNum.setColor(mHolder.getEqualColor());
                        mPaintNum.setStyle(Style.FILL);
                        canvas.drawRect(0, (float) y - fontH,
                                fontW, (float) y + fontH, mPaintNum);

                        mPaintNum.setStyle(Style.FILL);
                        mPaintNum.setTextSize(mTextSizeReal);
                        mPaintNum.setTextAlign(Align.CENTER);
                        mPaintNum.setColor(Color.WHITE);
                        canvas.drawText(value, fontW / 2, (float) y + fontH / 2,
                                mPaintNum);
                    }
                }
            }
            mHolder.displayIndexDetailByScreenIndex();
        }
        canvas.restore();
    }

    private double getTopPaddingByRatio(double value, double marker) {
        int height = mHolder.getTreadPriceView().getHeight();
        if (value < 0) {
            value = 0;
        }
        double topPadding = value * (height - 2) * 1f / marker;
        topPadding = height - topPadding - 2 > 0 ? height - topPadding - 2 : 0;
        return topPadding;
    }

    public void setHolder(DiffContainerWaihuiTianyan holder) {
        mHolder = holder;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

}
