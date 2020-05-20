package com.wifieye.akechartdemo.line;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;


public class DiffBottomTimeLineViewWaihuiTianyan extends View {

    protected final PathEffect EFFECT = new DashPathEffect(
            new float[]{2, 2}, 0);
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
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
    public DiffBottomTimeLineViewWaihuiTianyan(Context context) {
        super(context);
        init();
    }

    /**
     * @param context
     * @param attrs
     */
    public DiffBottomTimeLineViewWaihuiTianyan(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public DiffBottomTimeLineViewWaihuiTianyan(Context context, AttributeSet attrs,
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
        mPaint.setTextAlign(Paint.Align.CENTER);
        mTextSize = FunctionHelper.sp2pxInt(8);
        mPaint.setTextSize(mTextSize);
        postInvalidate();
    }

    private void init() {
        initColor();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        if (mHolder.getDataModel() != null) {
            int mLeftMargin = 0;
            int mTopHeight = 0;
            int len = mHolder.getDataModel().size();
            int i = mHolder.mDisplayStart;
            float klineWidth = 0;
            float klineWidthHalf = 0;
            float klineWidthGap = Dip.dip2;
            for (; i < len && ((i - mHolder.mDisplayStart) < mHolder.mDisplayCount); i++) {
                int screenIndex = i;
                if (screenIndex >= 0 && screenIndex < len) {
                    String value = mHolder.getTreadPriceView().getDetailTimeByIndex(screenIndex);
                    int width = mHolder.getTreadPriceViewWidth() - mHolder.mRightNumber - mHolder.mLeftNumber;
                    int height = getHeight();
                    int leftPadding = this.getPaddingLeft();
                    if (leftPadding == 0) {
                        leftPadding = 2;
                    }
                    leftPadding = leftPadding + mHolder.mLeftNumber;
                    if (mHolder.isBesselEffect) {
                        leftPadding = getPaddingLeft();
                        width = mHolder.getTreadPriceViewWidth();
                    }
                    float xTmp = leftPadding + (width - 1) * 1f * (i - mHolder.mDisplayStart) / (mHolder.mDisplayCount - 1);
                    if (mHolder.isTraderEnv && mHolder.isTraderEnvKline) {
                        if (mHolder.getDataModel().size2() > 0) {
                            klineWidth = Dip.dip4;
                            klineWidthHalf = klineWidth / 2.0f;
                            if (mHolder.getDataModel().size2() <= 8) {
                                xTmp = leftPadding + Dip.dip17 + (width - 1 - Dip.dip17 - Dip.dip17) * 1f * (i - mHolder.mDisplayStart) / (mHolder.mDisplayCount - 1);
                            } else {
                                if (mHolder.getDataModel().size2() > 20) {
                                    klineWidth = Dip.dip3;
                                    klineWidthHalf = klineWidth / 2.0f;
                                    klineWidthGap = Dip.dip1;
                                }
                                xTmp = leftPadding + klineWidth + klineWidthGap / 2.0f + (width - 1 - klineWidth - klineWidthGap) * 1f * (i - mHolder.mDisplayStart) / (mHolder.mDisplayCount - 1);
                            }
                        }
                    } else if (mHolder.isBusinessEffect && mHolder.isTraderEnvKline) {
                        klineWidth = Dip.dip4;
                        klineWidthHalf = klineWidth / 2.0f;
                        xTmp = leftPadding + klineWidth + klineWidthHalf + (width - 1 - klineWidth - klineWidthGap - klineWidth - klineWidthGap - klineWidth) * 1f * (i - mHolder.mDisplayStart) / (mHolder.mDisplayCount - 1);
                    }
                    float x = xTmp;
                    if (x > width - mHolder.mRightNumber) {
                        //x = width - mHolder.mRightNumber;
                    }
                    if (x < mHolder.mLeftNumber) {
                        //x = mHolder.mLeftNumber;
                    }
                    int offY = (height - mTextSize) / 2;
                    float xx = x;
                    if (value == null) {
                        value = "";
                    }
                    mPaint.setStyle(Style.FILL);
                    mPaint.setStrokeWidth(2.0f);
                    mPaint.setColor(0xff999999);
//                    mPaint.getTextBounds(value, 0, value.length(), mRect);
//                    if (mRect.width() / 2 > xx) {
//                        xx = dip3;
//                        mPaint.setTextAlign(Paint.Align.LEFT);
//                    } else if ((width - xx) < mRect.width() / 2) {
//                        xx = width - dip3;
//                        mPaint.setTextAlign(Paint.Align.RIGHT);
//                    } else {
                    if (mHolder.isBusinessEffect && mHolder.isTraderEnvKline) {
                        mPaint.setColor(0xff999999);
                        mPaint.setTextSize(FunctionHelper.sp2px(11));
                        mPaint.setTextAlign(Paint.Align.CENTER);
                    } else {
                        mPaint.setTextAlign(Paint.Align.CENTER);
                    }
//                    }
                    canvas.drawText(value, xx, offY - mPaint.getFontMetrics().ascent, mPaint);
                }
            }
        }
        canvas.restore();
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

