package com.wifieye.akechartdemo.line;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import static com.wifieye.akechartdemo.line.Dip.dip20;
import static com.wifieye.akechartdemo.line.Dip.dip5;


public class DiffTreadPriceWaihuiTianyan extends DiffChartBaseViewWaihuiTianyan {

    private double[] mCurrentPrice;
    private double[] mCurrentPrice2;
    private double[] mCurrentPrice3;

    private double mMaxPrice;
    private double mMinPrice;
    private double priceTopPadding;
    private double priceTopPadding2;
    private double priceTopPadding3;
    private double gap;
    private double _row;

    private Path mPricePath = new Path();
    private Path mPricePath2 = new Path();

    private Path mPricePath_op = new Path();
    private Path mPricePath2_op = new Path();
    private float quadToX_last = 0f;
    private float quadToY_last = 0f;
    private float quadToX_2_last = 0f;
    private float quadToY_2_last = 0f;
    private float quadToX_next = 0f;
    private float quadToY_next = 0f;
    private float quadToX_2_next = 0f;
    private float quadToY_2_next = 0f;

    private int mTextSize;

    private float duration = 1000f;
    private long startTime = 0L;
    private Rect mRect = new Rect();
    private boolean mPortrait = true;
    private int width;
    private int height;
    private int leftPadding;
    private float mLineWidth;
    private float mAvgLineWidth;
    private int mAvgPathColor;
    private int mPricePathColor;
    private int mPricePathColor2;
    private int mPricePathColor3;
    private int cpColor;
    private int jbColor = 0x252a92eb;//0x662a92eb;
    private int mode;
    private Paint mPaintShadowLine = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintShadowLine2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path mPathShadowLine = new Path();
    private Path mPathShadowLine2 = new Path();
    private LinearGradient mLinearGradientPortrait;
    private LinearGradient mLinearGradientPortrait2;
    private LinearGradient mLinearGradientLanscape;
    private int mTextSizeReal;
    private Paint mPaintNum = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected int mLeftArrawLineColor = 0xffd3d3d3;
    protected int mLeftArrawDashPathEffectLineColor = 0xffe5e5e5;
    protected int mLeftArrawTextColor = 0xff999999;
    private Paint mSpecialPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mSpecialPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mSpecialTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mSpecialTextPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mSpecialTextPathColor = 0xffe5e5e5;
    private Path mSpecialTextPath = new Path();
    private String mSpecialText1_feinong = "非农";
    private String mSpecialText2_lilv = "利率";
    private int mColorSpecialText1_feinong = 0xff44c8e0;
    private int mColormSpecialText2_lilv = 0xfff16816;
    private int mSpecialTextSizeReal = FunctionHelper.sp2pxInt(12);
    private Paint mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mKlinePaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mKlinePaint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float paddingBottomBesselEffect = FunctionHelper.sp2px(13.0f);
//    private LookFace mLookFace;

    /**
     * @param context
     */
    public DiffTreadPriceWaihuiTianyan(Context context) {
        super(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public DiffTreadPriceWaihuiTianyan(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public DiffTreadPriceWaihuiTianyan(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        super.init();
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//		DisplayMetrics dm = new DisplayMetrics();
        //   ((Application)getContext()).getWindowManager().getDefaultDisplay().getMetrics(dm);
//	    SettingManager.getInstance().setDensity(dm.density);
//	    SettingManager.getInstance().setScreenWidth(dm.widthPixels);
        initColor();
        mTextSize = FunctionHelper.sp2pxInt(12);
        mLineWidth = FunctionHelper.dp2pxInt(1.5f);
        mAvgLineWidth = FunctionHelper.dp2pxInt(1.5f);
        mTextSizeReal = FunctionHelper.sp2pxInt(9);
        DisplayMetrics dm = new DisplayMetrics();
        dm = getResources().getDisplayMetrics();
        if (dm.widthPixels >= 1080) {
            mLineWidth = 3.0f;
            mAvgLineWidth = 2.8f;
        } else if (dm.widthPixels >= 720) {
            mLineWidth = 2.4f;
            mAvgLineWidth = 2.2f;
        } else if (dm.widthPixels != 0) {
            mLineWidth = 1.6f;
            mAvgLineWidth = 1.4f;
        }
        hasPaintBackground = true;
        //makeBackground(getWidth(), getHeight());
        postInvalidate();
    }


    public void initColor() {
        if (mHolder != null && mHolder.isNight()) {
            mLineCol = 0xffd6d6d6;
            cpColor = 0xffffffff;
            mAvgPathColor = 0xfff49331;//0xffff8800;
            mPricePathColor = 0xFF2a92eb;//0xFF538aef;
        } else {
            cpColor = 0xff858c93;
            mAvgPathColor = 0xfff49331;//0xffff8800;
            mPricePathColor = 0xFF2a92eb;//0xFF538aef;
            mLineCol = 0xffd6d6d6;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        paintBackground(canvas);
        if (mHolder == null || mHolder.getDataModel() == null || mHolder.getDataModel().size() <= 0) {
            return;
        }
        paintBackgroundSelf(canvas);
        canvas.save();
        if (mHolder.isTraderEnv) {
            mPricePathColor = mHolder.dataColor1;
            mPricePathColor2 = mHolder.dataColor2;
        } else if (mHolder.isBusinessEffect) {
            mPricePathColor = mHolder.dataColor1;
            mPricePathColor2 = mHolder.dataColor2;
            mPricePathColor3 = mHolder.dataColor3;
        }
        float height = getHeight();
        float maxX = getWidth() - mHolder.mRightNumber;
        width = getWidth() - mHolder.mRightNumber - mHolder.mLeftNumber;
        leftPadding = this.getPaddingLeft() + mHolder.mLeftNumber;
        if (mHolder.isBesselEffect) {
            width = getWidth();
            leftPadding = this.getPaddingLeft();
        }
        mPortrait = true;
        gap = mMaxPrice - mMinPrice;
        if (mHolder.getDataModel() != null && mCurrentPrice != null && mCurrentPrice.length > 0) {
            if (gap > 0) {
                if (mLinearGradientPortrait == null) {
                    if (mHolder.isTraderEnv) {
                        mLinearGradientPortrait = new LinearGradient(leftPadding, height - 1, leftPadding, 1, 0x3f27b1fd & 0x00ffffff,
                                0x3f27b1fd, Shader.TileMode.MIRROR);
                    } else if (mHolder.isBusinessEffect) {
                        mLinearGradientPortrait = new LinearGradient(leftPadding, height - 1, leftPadding, 1, 0x3f27b1fd & 0x00ffffff,
                                0x3f27b1fd, Shader.TileMode.MIRROR);
                    } else {
                        mLinearGradientPortrait = new LinearGradient(leftPadding, height - 1, leftPadding, 1, jbColor & 0x00ffffff,
                                jbColor, Shader.TileMode.MIRROR);
                    }
                }
                mPathShadowLine.reset();
                mPaintShadowLine.setShader(mLinearGradientPortrait);
                if (mHolder.isTraderEnv && mHolder.getDataModel().size2() > 0) {
                    if (mLinearGradientPortrait2 == null) {
                        mLinearGradientPortrait2 = new LinearGradient(leftPadding, height - 1, leftPadding, 1, 0x3f5abc80 & 0x00ffffff,
                                0x3f5abc80, Shader.TileMode.MIRROR);
                    }
                    mPathShadowLine2.reset();
                    mPaintShadowLine2.setShader(mLinearGradientPortrait2);
                } else if (mHolder.isBusinessEffect && mHolder.getDataModel().size2() > 0) {
                    if (mLinearGradientPortrait2 == null) {
                        mLinearGradientPortrait2 = new LinearGradient(leftPadding, height - 1, leftPadding, 1, 0x3f5abc80 & 0x00ffffff,
                                0x3f5abc80, Shader.TileMode.MIRROR);
                    }
                    mPathShadowLine2.reset();
                    mPaintShadowLine2.setShader(mLinearGradientPortrait2);
                }
                mPricePath.reset();
                mPathShadowLine.moveTo(leftPadding, height - 1);
                if (mHolder.isTraderEnv && mHolder.getDataModel().size2() > 0) {
                    mPricePath2.reset();
                    mPathShadowLine2.moveTo(leftPadding, height - 1);
                } else if (mHolder.isBusinessEffect && mHolder.getDataModel().size2() > 0) {
                    mPricePath2.reset();
                    mPathShadowLine2.moveTo(leftPadding, height - 1);
                }
                int len = mHolder.getDataModel().size();
                int total = mHolder.mDisplayCount;
                int start = 0;
                if (total > mHolder.mDisplayCount) {
                    total = mHolder.mDisplayCount;
                }
                start = len >= mHolder.mDisplayCount ? (len - mHolder.mDisplayCount) : 0;
                if (mHolder.mDisplayOffset > 0) {
                    if (start - mHolder.mDisplayOffset >= 0) {
                        start = start - mHolder.mDisplayOffset;
                    }
                }
                mHolder.mDisplayStart = start;
                float klineWidth = (width - 1) * 1f / total;
                float klineWidthHalf = (width - 1) * 1f / total / 2.0f;
                float klineWidthGap = Dip.dip2;
                float xTmp = 0;
                for (int i = start; i < len && ((i - start) < total); i++) {
                    xTmp = leftPadding + (width - 1) * 1f * (i - start) / (total - 1);
                    if (mHolder.isTraderEnv && mHolder.isTraderEnvKline) {
                        if (mHolder.getDataModel().size2() > 0) {
                            klineWidth = Dip.dip4;
                            klineWidthHalf = klineWidth / 2.0f;
                            if (mHolder.getDataModel().size2() <= 8) {
                                xTmp = leftPadding + Dip.dip17 + (width - 1 - Dip.dip17 - Dip.dip17) * 1f * (i - start) / (total - 1);
                            } else {
                                if (mHolder.getDataModel().size2() > 20) {
                                    klineWidth = Dip.dip3;
                                    klineWidthHalf = klineWidth / 2.0f;
                                    klineWidthGap = Dip.dip1;
                                }
                                xTmp = leftPadding + klineWidth + klineWidthGap / 2.0f + (width - 1 - klineWidth - klineWidthGap) * 1f * (i - start) / (total - 1);
                            }
                        }
                    } else if (mHolder.isBusinessEffect && mHolder.isTraderEnvKline) {
                        klineWidth = Dip.dip4;
                        klineWidthHalf = Dip.dip2;
                        xTmp = leftPadding + klineWidth + klineWidthGap / 2.0f + (width - 1 - klineWidth - klineWidthGap - klineWidth - klineWidthGap - klineWidth) * 1f * (i - start) / (total - 1);
                    }
                    priceTopPadding = getTopPaddingByRatio(mCurrentPrice[i] - mMinPrice, gap);
                    if (mHolder.isTraderEnv && mCurrentPrice2.length > 0 && i < mCurrentPrice2.length) {
                        priceTopPadding2 = getTopPaddingByRatio(mCurrentPrice2[i] - mMinPrice, gap);
                    } else if (mHolder.isBusinessEffect && mCurrentPrice2.length > 0 && i < mCurrentPrice2.length) {
                        priceTopPadding2 = getTopPaddingByRatio(mCurrentPrice2[i] - mMinPrice, gap);
                        if (mCurrentPrice3 != null && mCurrentPrice3.length > 0 && i < mCurrentPrice3.length) {
                            priceTopPadding3 = getTopPaddingByRatio(mCurrentPrice3[i] - mMinPrice, gap);
                        }
                    }
                    if (i == start) {
                        quadToX_last = xTmp;
                        quadToY_last = (float) priceTopPadding + 2;
                        mPricePath.moveTo(xTmp, (float) priceTopPadding + 2);
                        mPathShadowLine.lineTo(xTmp, (float) priceTopPadding + 2);
                        if (mHolder.isTraderEnv && mHolder.getDataModel().size2() > 0) {
                            quadToX_2_last = xTmp;
                            quadToY_2_last = (float) priceTopPadding2 + 2;
                            mPricePath2.moveTo(xTmp, (float) priceTopPadding2 + 2);
                            mPathShadowLine2.lineTo(xTmp, (float) priceTopPadding2 + 2);
                        } else if (mHolder.isBusinessEffect && mHolder.getDataModel().size2() > 0) {
                            quadToX_2_last = xTmp;
                            quadToY_2_last = (float) priceTopPadding2 + 2;
                            mPricePath2.moveTo(xTmp, (float) priceTopPadding2 + 2);
                            mPathShadowLine2.lineTo(xTmp, (float) priceTopPadding2 + 2);
                        }
                    } else {
                        if (mHolder.isBesselEffect) {
                            mPricePath.cubicTo(quadToX_last + (xTmp - quadToX_last) * 0.45f, quadToY_last
                                    , quadToX_last + (xTmp - quadToX_last) * 0.55f, (float) (priceTopPadding + 2)
                                    , xTmp, (float) (priceTopPadding + 2));
                        } else {
                            mPricePath.lineTo(xTmp, (float) (priceTopPadding + 2));
                        }
                        quadToX_last = xTmp;
                        quadToY_last = (float) priceTopPadding + 2;
                        mPathShadowLine.lineTo(xTmp, (float) (priceTopPadding + 2));
                        if (mHolder.isTraderEnv && mHolder.getDataModel().size2() > 0) {
                            if (mHolder.isBesselEffect) {
                                mPricePath2.cubicTo(quadToX_2_last + (xTmp - quadToX_2_last) * 0.45f, quadToY_2_last
                                        , quadToX_2_last + (xTmp - quadToX_2_last) * 0.55f, (float) (priceTopPadding2 + 2)
                                        , xTmp, (float) (priceTopPadding2 + 2));
                            } else {
                                mPricePath2.lineTo(xTmp, (float) (priceTopPadding2 + 2));
                            }
                            mPathShadowLine2.lineTo(xTmp, (float) (priceTopPadding2 + 2));

                            quadToX_2_last = xTmp;
                            quadToY_2_last = (float) priceTopPadding2 + 2;
                        } else if (mHolder.isBusinessEffect && mHolder.getDataModel().size2() > 0) {
                            if (mHolder.isBesselEffect) {
                                mPricePath2.cubicTo(quadToX_2_last + (xTmp - quadToX_2_last) * 0.45f, quadToY_2_last
                                        , quadToX_2_last + (xTmp - quadToX_2_last) * 0.55f, (float) (priceTopPadding2 + 2)
                                        , xTmp, (float) (priceTopPadding2 + 2));
                            } else {
                                mPricePath2.lineTo(xTmp, (float) (priceTopPadding2 + 2));
                            }
                            mPathShadowLine2.lineTo(xTmp, (float) (priceTopPadding2 + 2));

                            quadToX_2_last = xTmp;
                            quadToY_2_last = (float) priceTopPadding2 + 2;
                        }
                    }
                    if (i == len - 1 || ((i - start) == total - 1)) {
                        mPathShadowLine.lineTo(xTmp, height - 1);
                        if (mHolder.isTraderEnv && mHolder.getDataModel().size2() > 0) {
                            mPathShadowLine2.lineTo(xTmp, height - 1);
                        } else if (mHolder.isBusinessEffect && mHolder.getDataModel().size2() > 0) {
                            mPathShadowLine2.lineTo(xTmp, height - 1);
                        }
                    }

                    mKlinePaint.setStyle(Style.FILL);
                    mKlinePaint.setStrokeWidth(1);
                    mKlinePaint.setColor(mPricePathColor);
                    if (mHolder.isTraderEnv) {
                        mKlinePaint2.setStyle(Style.FILL);
                        mKlinePaint2.setStrokeWidth(1);
                        mKlinePaint2.setColor(mPricePathColor2);
                    } else if (mHolder.isBusinessEffect) {
                        mKlinePaint2.setStyle(Style.FILL);
                        mKlinePaint2.setStrokeWidth(1);
                        mKlinePaint2.setColor(mPricePathColor2);

                        mKlinePaint3.setStyle(Style.FILL);
                        mKlinePaint3.setStrokeWidth(1);
                        mKlinePaint3.setColor(mPricePathColor3);
                    }
                    if (mHolder.isTraderEnv && mHolder.isTraderEnvKline) {
                        float xTmp1 = xTmp - klineWidthHalf - klineWidthGap / 2.0f;
                        float xTmp2 = xTmp1 + klineWidthHalf + klineWidthGap + klineWidthHalf;
                        if (mHolder.getDataModel().size2() > 0) {
                            //柱形图
                            canvas.drawRect(xTmp2 - klineWidthHalf, (float) priceTopPadding2 + klineWidthHalf, xTmp2 + klineWidthHalf, height - 1, mKlinePaint2);
                            canvas.drawCircle(xTmp2, (float) priceTopPadding2 + klineWidthHalf, klineWidthHalf, mKlinePaint2);
                        }
                        //柱形图
                        canvas.drawRect(xTmp1 - klineWidthHalf, (float) priceTopPadding + klineWidthHalf, xTmp1 + klineWidthHalf, height - 1, mKlinePaint);
                        canvas.drawCircle(xTmp1, (float) priceTopPadding + klineWidthHalf, klineWidthHalf, mKlinePaint);
                    } else if (mHolder.isBusinessEffect && mHolder.isTraderEnvKline) {
                        float xTmp1 = xTmp - klineWidthHalf - klineWidthGap / 2.0f;
                        float xTmp2 = xTmp + klineWidthHalf + klineWidthGap / 2.0f;
                        if (mHolder.getDataModel().size2() > 0) {
                            //柱形图
                            canvas.drawRect(xTmp2 - klineWidthHalf, (float) priceTopPadding2 + klineWidthHalf, xTmp2 + klineWidthHalf, height, mKlinePaint2);
                            canvas.drawCircle(xTmp2, (float) priceTopPadding2 + klineWidthHalf, klineWidthHalf, mKlinePaint2);
                        }
                        //柱形图
                        canvas.drawRect(xTmp1 - klineWidthHalf, (float) priceTopPadding + klineWidthHalf, xTmp1 + klineWidthHalf, height, mKlinePaint);
                        canvas.drawCircle(xTmp1, (float) priceTopPadding + klineWidthHalf, klineWidthHalf, mKlinePaint);

                        if (mCurrentPrice3 != null && mCurrentPrice3.length > 0 && i < mCurrentPrice3.length) {
                            float xTmp3 = xTmp2 + klineWidthHalf + klineWidthGap + klineWidthHalf;
                            //柱形图
                            canvas.drawRect(xTmp3 - klineWidthHalf, (float) priceTopPadding3 + klineWidthHalf, xTmp3 + klineWidthHalf, height, mKlinePaint3);
                            canvas.drawCircle(xTmp3, (float) priceTopPadding3 + klineWidthHalf, klineWidthHalf, mKlinePaint3);
                        }
                    }
                }
                mPathShadowLine.close();
                if (mHolder.isTraderEnv && mHolder.isTraderEnvKline) {

                } else if (mHolder.isBusinessEffect && mHolder.isTraderEnvKline) {

                } else {
                    if (mHolder.isBesselEffect) {

                    } else {
                        canvas.drawPath(mPathShadowLine, mPaintShadowLine);
                        if (mHolder.isTraderEnv && mHolder.getDataModel().size2() > 0) {
                            mPathShadowLine2.close();
                            canvas.drawPath(mPathShadowLine2, mPaintShadowLine2);
                        } else if (mHolder.isBusinessEffect && mHolder.getDataModel().size2() > 0) {
                            mPathShadowLine2.close();
                            canvas.drawPath(mPathShadowLine2, mPaintShadowLine2);
                        }
                    }
                }
                mPaint.setAntiAlias(true);
                mPaint.setStyle(Style.STROKE);
                mPaint.setColor(mPricePathColor);
                if (mHolder.isBesselEffect) {
                    mLineWidth = dip5;
                }
                mPaint.setStrokeWidth(mLineWidth - 1);
                if (mHolder.isTraderEnv && mHolder.isTraderEnvKline) {

                } else if (mHolder.isBusinessEffect && mHolder.isTraderEnvKline) {

                } else {
                    if (mHolder.isBesselEffect) {
                        float now = SystemClock.elapsedRealtime() - startTime;
                        float clipX = 0;
                        if (now >= 0 && now <= duration) {
                            clipX = leftPadding + now / duration * (width - 1);
                            mPricePath_op.reset();
                            mPricePath_op.moveTo(clipX, 0);
                            mPricePath_op.lineTo((width - 1), 0);
                            mPricePath_op.lineTo((width - 1), getHeight());
                            mPricePath_op.lineTo(clipX, getHeight());
                            mPricePath_op.close();
                            //mPricePath.op(mPricePath_op, Path.Op.DIFFERENCE);
                            if (mHolder.getDataModel().size2() > 0) {
                                mPricePath2_op.reset();
                                mPricePath2_op.moveTo(clipX, 0);
                                mPricePath2_op.lineTo((width - 1), 0);
                                mPricePath2_op.lineTo((width - 1), getHeight());
                                mPricePath2_op.lineTo(clipX, getHeight());
                                mPricePath2_op.close();
                                //mPricePath2.op(mPricePath2_op, Path.Op.DIFFERENCE);
                            }
                            postInvalidate();
                        }
                        canvas.save();
                        if (clipX > 0) {
                            canvas.clipRect(0, 0 , clipX, getHeight());
                        }
                        if (mHolder.isTraderEnv && mHolder.getDataModel().size2() > 0) {
                            mPaint2.setAntiAlias(true);
                            mPaint2.setStyle(Style.STROKE);
                            mPaint2.setColor(mPricePathColor2);
                            mPaint2.setStrokeWidth(mLineWidth - 1);
                            canvas.drawPath(mPricePath2, mPaint2);
                        } else if (mHolder.isBusinessEffect && mHolder.getDataModel().size2() > 0) {
                            mPaint2.setAntiAlias(true);
                            mPaint2.setStyle(Style.STROKE);
                            mPaint2.setColor(mPricePathColor2);
                            mPaint2.setStrokeWidth(mLineWidth - 1);
                            canvas.drawPath(mPricePath2, mPaint2);
                        }
                        canvas.drawPath(mPricePath, mPaint);
                        canvas.restore();
                    } else {
                        canvas.drawPath(mPricePath, mPaint);
                        if (mHolder.isTraderEnv && mHolder.getDataModel().size2() > 0) {
                            mPaint2.setAntiAlias(true);
                            mPaint2.setStyle(Style.STROKE);
                            mPaint2.setColor(mPricePathColor2);
                            mPaint2.setStrokeWidth(mLineWidth - 1);
                            canvas.drawPath(mPricePath2, mPaint2);
                        } else if (mHolder.isBusinessEffect && mHolder.getDataModel().size2() > 0) {
                            mPaint2.setAntiAlias(true);
                            mPaint2.setStyle(Style.STROKE);
                            mPaint2.setColor(mPricePathColor2);
                            mPaint2.setStrokeWidth(mLineWidth - 1);
                            canvas.drawPath(mPricePath2, mPaint2);
                        }
                    }
                }
                if (mHolder.isTraderEnv) {
                    float dataGap = 0;
                    float drawWidth = 0;
                    for (int i = start; i < len && ((i - start) < total); i++) {
                        xTmp = leftPadding + (width - 1) * 1f * (i - start) / (total - 1);
                        dataGap = (width - 1) * 1f * (1f) / (total - 1);
                        drawWidth = (width - 1);
                        if (mHolder.isTraderEnvKline) {
                            if (mHolder.getDataModel().size2() > 0) {
                                klineWidth = Dip.dip4;
                                klineWidthHalf = klineWidth / 2.0f;
                                if (mHolder.getDataModel().size2() <= 8) {
                                    xTmp = leftPadding + Dip.dip17 + (width - 1 - Dip.dip17 - Dip.dip17) * 1f * (i - start) / (total - 1);
                                    dataGap = (width - 1 - Dip.dip17 - Dip.dip17) * 1f * (1f) / (total - 1);
                                    drawWidth = (width - 1 - Dip.dip17 - Dip.dip17);
                                } else {
                                    if (mHolder.getDataModel().size2() > 20) {
                                        klineWidth = Dip.dip3;
                                        klineWidthHalf = klineWidth / 2.0f;
                                        klineWidthGap = Dip.dip1;
                                    }
                                    xTmp = leftPadding + klineWidth + klineWidthGap / 2.0f + (width - 1 - klineWidth - klineWidthGap) * 1f * (i - start) / (total - 1);
                                    dataGap = (width - 1 - klineWidth - klineWidthGap) * 1f * (1f) / (total - 1);
                                    drawWidth = (width - 1 - klineWidth - klineWidthGap);
                                }
                            }
                        }
                        priceTopPadding = getTopPaddingByRatio(mCurrentPrice[i] - mMinPrice, gap);
                        if (mCurrentPrice2.length > 0 && i < mCurrentPrice2.length) {
                            priceTopPadding2 = getTopPaddingByRatio(mCurrentPrice2[i] - mMinPrice, gap);
                        }
                        if (!mHolder.isBesselEffect) {
                            DiffDataBean.ContentBean.DBean dBean = null;
                            DiffDataBean.ContentBean.DBean dBeanPre = null;
                            DiffDataBean.ContentBean.DBean dBeanNext = null;
                            try {
                                dBean = mHolder.mData.get(i);
                                if (i - 1 >= 0) {
                                    dBeanPre = mHolder.mData.get(i - 1);
                                }
                                if (i + 1 <= mHolder.mData.size() - 1) {
                                    dBeanNext = mHolder.mData.get(i + 1);
                                }
                            } catch (Exception e) {

                            }
                            if (dBean != null) {
                                if (dBean.getType() == 2 || dBean.getType() == 3) {
                                    if (dBean.getType() == 2) {
                                        mSpecialPaint.setColor(mColorSpecialText1_feinong);
                                    } else {
                                        mSpecialPaint.setColor(mColormSpecialText2_lilv);
                                    }
                                    mSpecialPaint.setPathEffect(EFFECT);
                                    mSpecialPaint.setStrokeWidth(2);
                                    canvas.drawLine(xTmp, height / 4.0f, xTmp, height - 1, mSpecialPaint);
                                    if (dBean.getType() == 2) {
                                        mSpecialPaint2.setColor(mColorSpecialText1_feinong);
                                    } else {
                                        mSpecialPaint2.setColor(mColormSpecialText2_lilv);
                                    }
                                    mSpecialPaint2.setPathEffect(null);
                                    mSpecialPaint2.setStrokeWidth(Dip.dip1_5);
                                    canvas.drawCircle(xTmp, height / 4.0f, Dip.dip3, mSpecialPaint2);
                                    mSpecialPaint2.setColor(Color.WHITE);
                                    canvas.drawCircle(xTmp, height / 4.0f, Dip.dip2, mSpecialPaint2);

                                    mSpecialTextPaint.setStyle(Style.FILL);
                                    mSpecialTextPaint.setStrokeWidth(2.0f);
                                    mSpecialTextPaint.setTextSize(mSpecialTextSizeReal);
                                    if (dBean.getType() == 2) {
                                        mSpecialTextPaint.setColor(mColorSpecialText1_feinong);
                                    } else {
                                        mSpecialTextPaint.setColor(mColormSpecialText2_lilv);
                                    }
                                    mSpecialTextPaint.setTextAlign(Align.CENTER);

                                    float tHeght = mSpecialTextSizeReal;//mSpecialTextPaint.getFontMetrics().descent - mSpecialTextPaint.getFontMetrics().ascent;
                                    float textOutHalfW = Dip.dip17;
                                    float textOutH = Dip.dip17;
                                    float xTmpT = xTmp;
                                    float last_xTmpT = mHolder.mLeftNumber;
                                    if (dBeanPre != null) {
                                        last_xTmpT = dBeanPre.getLast_xTmpT();
                                    }
                                    if (last_xTmpT == mHolder.mLeftNumber) {
                                        if (xTmpT - mHolder.mLeftNumber < textOutHalfW) {
                                            xTmpT = xTmp + textOutHalfW;
                                        } else if (maxX - xTmpT < textOutHalfW) {
                                            xTmpT = xTmp - textOutHalfW;
                                        }
                                    } else {
                                        if (xTmpT - last_xTmpT < textOutHalfW * 2) {
                                            xTmpT = xTmp + textOutHalfW;
                                        } else if (maxX - xTmpT < textOutHalfW) {
                                            xTmpT = xTmp - textOutHalfW;
                                        }
                                    }
                                    dBean.setLast_xTmpT(xTmpT);
                                    float left = xTmpT - textOutHalfW;
                                    float right = xTmpT + textOutHalfW;
                                    float ty = (height / 4.0f - mSpecialTextSizeReal) / 2;
                                    float top = 0;
                                    float bottom = 0;
                                    if (mHolder.isTraderEnvKline) {
                                        float high = (float) priceTopPadding;
                                        if (mHolder.getDataModel().size2() > 0) {
                                            high = (priceTopPadding2 < priceTopPadding ? (float) priceTopPadding2 : (float) priceTopPadding);
                                        }
                                        top = high - dip5 - textOutH;
                                        if (top < Dip.dip1) {
                                            top = Dip.dip1;
                                        }
                                        bottom = top + textOutH;
                                        ty = top + textOutH / 2.0f - tHeght / 2.0f;
                                    } else {
                                        top = Dip.dip1;
                                        bottom = top + textOutH;
                                        ty = top + textOutH / 2.0f - tHeght / 2.0f;
                                    }
                                    if (dBean.getType() == 2 || xTmpT == xTmp || dataGap > textOutHalfW * 2) {
                                        top = height / 4.0f - dip5 - textOutH - Dip.dip3;
                                        if (top < Dip.dip1) {
                                            top = Dip.dip1;
                                        }
                                        bottom = top + textOutH;
                                        ty = top + textOutH / 2.0f - tHeght / 2.0f;
                                    } else {
                                        top = Dip.dip1 + height / 4.0f + dip5 + Dip.dip3;
                                        bottom = top + textOutH;
                                        ty = top + textOutH / 2.0f - tHeght / 2.0f;
                                    }
                                    float arrawW = (right - left) * 0.275f;
                                    float arrawLeft = xTmpT - arrawW / 2.0f;
                                    float arrawRight = xTmpT + arrawW / 2.0f;
                                    if (xTmpT < xTmp) {
                                        arrawRight = right - Dip.dip2;
                                        arrawLeft = arrawRight - arrawW;
                                    } else if (xTmpT > xTmp) {
                                        arrawLeft = left + Dip.dip2;
                                        arrawRight = arrawLeft + arrawW;
                                    }
                                    mSpecialTextPath.reset();
                                    mSpecialTextPath.moveTo(left, top + Dip.dip2);
                                    if (xTmpT > xTmp && top > height / 4.0f) {
                                        mSpecialTextPath.lineTo(left, top - dip5);
                                        mSpecialTextPath.lineTo(left + arrawW * 0.6f, top);
                                    } else {
                                        mSpecialTextPath.arcTo(left, top, left + Dip.dip4, top + Dip.dip4, 180, 90, false);
                                        if (xTmpT == xTmp && top > height / 4.0f) {
                                            mSpecialTextPath.lineTo(arrawLeft, top);
                                            mSpecialTextPath.lineTo(arrawRight - arrawW / 2.0f, top - dip5);
                                            mSpecialTextPath.lineTo(arrawRight, top);
                                        }
                                    }
                                    if (xTmpT < xTmp && top > height / 4.0f) {
                                        mSpecialTextPath.lineTo(right - arrawW * 0.6f, top);
                                        mSpecialTextPath.lineTo(right, top - dip5);
                                    } else {
                                        mSpecialTextPath.lineTo(right - Dip.dip2, top);
                                        mSpecialTextPath.arcTo(right - Dip.dip4, top, right, top + Dip.dip4, 270, 90, false);
                                    }
                                    mSpecialTextPath.lineTo(right, bottom - Dip.dip2);
                                    if (xTmpT < xTmp && top < height / 4.0f) {
                                        mSpecialTextPath.lineTo(right, bottom + dip5);
                                        mSpecialTextPath.lineTo(right - arrawW * 0.6f, bottom);
                                    } else {
                                        mSpecialTextPath.arcTo(right - Dip.dip4, bottom - Dip.dip4, right, bottom, 0, 90, false);
                                        if (xTmpT == xTmp && top < height / 4.0f) {
                                            mSpecialTextPath.lineTo(arrawRight, bottom);
                                            mSpecialTextPath.lineTo(arrawRight - arrawW / 2.0f, bottom + dip5);
                                            mSpecialTextPath.lineTo(arrawLeft, bottom);
                                        }
                                    }
                                    if (xTmpT > xTmp && top < height / 4.0f) {
                                        mSpecialTextPath.lineTo(left + arrawW * 0.6f, bottom);
                                        mSpecialTextPath.lineTo(left, bottom + dip5);
                                    } else {
                                        mSpecialTextPath.lineTo(left + Dip.dip2, bottom);
                                        mSpecialTextPath.arcTo(left, bottom - Dip.dip4, left + Dip.dip4, bottom, 90, 90, false);
                                    }
                                    mSpecialTextPath.close();
                                    mSpecialTextPathPaint.setStyle(Style.FILL_AND_STROKE);
                                    if (dBean.getType() == 2) {
                                        mSpecialTextPathPaint.setColor(mColorSpecialText1_feinong);
                                    } else {
                                        mSpecialTextPathPaint.setColor(mColormSpecialText2_lilv);
                                    }
                                    mSpecialTextPathPaint.setStrokeWidth(2);
                                    canvas.drawPath(mSpecialTextPath, mSpecialTextPathPaint);
                                    mSpecialTextPaint.setColor(Color.WHITE);
                                    if (dBean.getType() == 2) {
                                        canvas.drawText(mSpecialText1_feinong, xTmpT, ty - mSpecialTextPaint.getFontMetrics().ascent, mSpecialTextPaint);
                                    } else {
                                        canvas.drawText(mSpecialText2_lilv, xTmpT, ty - mSpecialTextPaint.getFontMetrics().ascent, mSpecialTextPaint);
                                    }
//                        canvas.drawRoundRect(left, top, right, bottom, Dip.dip2, Dip.dip2, mSpecialTextPathPaint);
//                        canvas.drawLine(xTmp - arraw, bottom, xTmp + arraw, bottom, mSpecialTextPathPaint);
//                        canvas.drawLine(xTmp - arraw, bottom, xTmp, bottom + dip5, mSpecialTextPathPaint);
//                        canvas.drawLine(xTmp + arraw, bottom, xTmp, bottom + dip5, mSpecialTextPathPaint);
//                        mSpecialTextPathPaint.setColor(Color.WHITE);
//                        canvas.drawLine(xTmp - arraw, bottom, xTmp + arraw, bottom, mSpecialTextPathPaint);
                                } else {
                                    if (dBeanPre != null) {
                                        dBean.setLast_xTmpT(dBeanPre.getLast_xTmpT());
                                    } else {
                                        dBean.setLast_xTmpT(mHolder.mLeftNumber);
                                    }
                                }
                            }
                        }
                    }
                } else if (mHolder.isBusinessEffect) {
                    float dataGap = 0;
                    float drawWidth = 0;
                    for (int i = start; i < len && ((i - start) < total); i++) {
                        xTmp = leftPadding + (width - 1) * 1f * (i - start) / (total - 1);
                        dataGap = (width - 1) * 1f * (1f) / (total - 1);
                        drawWidth = (width - 1);
                        if (mHolder.isTraderEnvKline) {
                            if (mHolder.getDataModel().size2() > 0) {
                                klineWidth = Dip.dip4;
                                klineWidthHalf = klineWidth / 2.0f;
                                if (mHolder.getDataModel().size2() <= 8) {
                                    xTmp = leftPadding + Dip.dip17 + (width - 1 - Dip.dip17 - Dip.dip17) * 1f * (i - start) / (total - 1);
                                    dataGap = (width - 1 - Dip.dip17 - Dip.dip17) * 1f * (1f) / (total - 1);
                                    drawWidth = (width - 1 - Dip.dip17 - Dip.dip17);
                                } else {
                                    if (mHolder.getDataModel().size2() > 20) {
                                        klineWidth = Dip.dip3;
                                        klineWidthHalf = klineWidth / 2.0f;
                                        klineWidthGap = Dip.dip1;
                                    }
                                    xTmp = leftPadding + klineWidth + klineWidthGap / 2.0f + (width - 1 - klineWidth - klineWidthGap) * 1f * (i - start) / (total - 1);
                                    dataGap = (width - 1 - klineWidth - klineWidthGap) * 1f * (1f) / (total - 1);
                                    drawWidth = (width - 1 - klineWidth - klineWidthGap);
                                }
                            }
                        }
                        priceTopPadding = getTopPaddingByRatio(mCurrentPrice[i] - mMinPrice, gap);
                        if (mCurrentPrice2.length > 0 && i < mCurrentPrice2.length) {
                            priceTopPadding2 = getTopPaddingByRatio(mCurrentPrice2[i] - mMinPrice, gap);
                        }
                        DiffDataBean.ContentBean.DBean dBean = null;
                        DiffDataBean.ContentBean.DBean dBeanPre = null;
                        DiffDataBean.ContentBean.DBean dBeanNext = null;
                        try {
                            dBean = mHolder.mData.get(i);
                            if (i - 1 >= 0) {
                                dBeanPre = mHolder.mData.get(i - 1);
                            }
                            if (i + 1 <= mHolder.mData.size() - 1) {
                                dBeanNext = mHolder.mData.get(i + 1);
                            }
                        } catch (Exception e) {

                        }
                        if (dBean != null) {
                            if (dBean.getType() == 2 || dBean.getType() == 3) {
                                if (dBean.getType() == 2) {
                                    mSpecialPaint.setColor(mColorSpecialText1_feinong);
                                } else {
                                    mSpecialPaint.setColor(mColormSpecialText2_lilv);
                                }
                                mSpecialPaint.setPathEffect(EFFECT);
                                mSpecialPaint.setStrokeWidth(2);
                                canvas.drawLine(xTmp, height / 4.0f, xTmp, height - 1, mSpecialPaint);
                                if (dBean.getType() == 2) {
                                    mSpecialPaint2.setColor(mColorSpecialText1_feinong);
                                } else {
                                    mSpecialPaint2.setColor(mColormSpecialText2_lilv);
                                }
                                mSpecialPaint2.setPathEffect(null);
                                mSpecialPaint2.setStrokeWidth(Dip.dip1_5);
                                canvas.drawCircle(xTmp, height / 4.0f, Dip.dip3, mSpecialPaint2);
                                mSpecialPaint2.setColor(Color.WHITE);
                                canvas.drawCircle(xTmp, height / 4.0f, Dip.dip2, mSpecialPaint2);

                                mSpecialTextPaint.setStyle(Style.FILL);
                                mSpecialTextPaint.setStrokeWidth(2.0f);
                                mSpecialTextPaint.setTextSize(mSpecialTextSizeReal);
                                if (dBean.getType() == 2) {
                                    mSpecialTextPaint.setColor(mColorSpecialText1_feinong);
                                } else {
                                    mSpecialTextPaint.setColor(mColormSpecialText2_lilv);
                                }
                                mSpecialTextPaint.setTextAlign(Align.CENTER);

                                float tHeght = mSpecialTextSizeReal;//mSpecialTextPaint.getFontMetrics().descent - mSpecialTextPaint.getFontMetrics().ascent;
                                float textOutHalfW = Dip.dip17;
                                float textOutH = Dip.dip17;
                                float xTmpT = xTmp;
                                float last_xTmpT = mHolder.mLeftNumber;
                                if (dBeanPre != null) {
                                    last_xTmpT = dBeanPre.getLast_xTmpT();
                                }
                                if (last_xTmpT == mHolder.mLeftNumber) {
                                    if (xTmpT - mHolder.mLeftNumber < textOutHalfW) {
                                        xTmpT = xTmp + textOutHalfW;
                                    } else if (maxX - xTmpT < textOutHalfW) {
                                        xTmpT = xTmp - textOutHalfW;
                                    }
                                } else {
                                    if (xTmpT - last_xTmpT < textOutHalfW * 2) {
                                        xTmpT = xTmp + textOutHalfW;
                                    } else if (maxX - xTmpT < textOutHalfW) {
                                        xTmpT = xTmp - textOutHalfW;
                                    }
                                }
                                dBean.setLast_xTmpT(xTmpT);
                                float left = xTmpT - textOutHalfW;
                                float right = xTmpT + textOutHalfW;
                                float ty = (height / 4.0f - mSpecialTextSizeReal) / 2;
                                float top = 0;
                                float bottom = 0;
                                if (mHolder.isTraderEnvKline) {
                                    float high = (float) priceTopPadding;
                                    if (mHolder.getDataModel().size2() > 0) {
                                        high = (priceTopPadding2 < priceTopPadding ? (float) priceTopPadding2 : (float) priceTopPadding);
                                    }
                                    top = high - dip5 - textOutH;
                                    if (top < Dip.dip1) {
                                        top = Dip.dip1;
                                    }
                                    bottom = top + textOutH;
                                    ty = top + textOutH / 2.0f - tHeght / 2.0f;
                                } else {
                                    top = Dip.dip1;
                                    bottom = top + textOutH;
                                    ty = top + textOutH / 2.0f - tHeght / 2.0f;
                                }
                                if (dBean.getType() == 2 || xTmpT == xTmp || dataGap > textOutHalfW * 2) {
                                    top = height / 4.0f - dip5 - textOutH - Dip.dip3;
                                    if (top < Dip.dip1) {
                                        top = Dip.dip1;
                                    }
                                    bottom = top + textOutH;
                                    ty = top + textOutH / 2.0f - tHeght / 2.0f;
                                } else {
                                    top = Dip.dip1 + height / 4.0f + dip5 + Dip.dip3;
                                    bottom = top + textOutH;
                                    ty = top + textOutH / 2.0f - tHeght / 2.0f;
                                }
                                float arrawW = (right - left) * 0.275f;
                                float arrawLeft = xTmpT - arrawW / 2.0f;
                                float arrawRight = xTmpT + arrawW / 2.0f;
                                if (xTmpT < xTmp) {
                                    arrawRight = right - Dip.dip2;
                                    arrawLeft = arrawRight - arrawW;
                                } else if (xTmpT > xTmp) {
                                    arrawLeft = left + Dip.dip2;
                                    arrawRight = arrawLeft + arrawW;
                                }
                                mSpecialTextPath.reset();
                                mSpecialTextPath.moveTo(left, top + Dip.dip2);
                                if (xTmpT > xTmp && top > height / 4.0f) {
                                    mSpecialTextPath.lineTo(left, top - dip5);
                                    mSpecialTextPath.lineTo(left + arrawW * 0.6f, top);
                                } else {
                                    mSpecialTextPath.arcTo(left, top, left + Dip.dip4, top + Dip.dip4, 180, 90, false);
                                    if (xTmpT == xTmp && top > height / 4.0f) {
                                        mSpecialTextPath.lineTo(arrawLeft, top);
                                        mSpecialTextPath.lineTo(arrawRight - arrawW / 2.0f, top - dip5);
                                        mSpecialTextPath.lineTo(arrawRight, top);
                                    }
                                }
                                if (xTmpT < xTmp && top > height / 4.0f) {
                                    mSpecialTextPath.lineTo(right - arrawW * 0.6f, top);
                                    mSpecialTextPath.lineTo(right, top - dip5);
                                } else {
                                    mSpecialTextPath.lineTo(right - Dip.dip2, top);
                                    mSpecialTextPath.arcTo(right - Dip.dip4, top, right, top + Dip.dip4, 270, 90, false);
                                }
                                mSpecialTextPath.lineTo(right, bottom - Dip.dip2);
                                if (xTmpT < xTmp && top < height / 4.0f) {
                                    mSpecialTextPath.lineTo(right, bottom + dip5);
                                    mSpecialTextPath.lineTo(right - arrawW * 0.6f, bottom);
                                } else {
                                    mSpecialTextPath.arcTo(right - Dip.dip4, bottom - Dip.dip4, right, bottom, 0, 90, false);
                                    if (xTmpT == xTmp && top < height / 4.0f) {
                                        mSpecialTextPath.lineTo(arrawRight, bottom);
                                        mSpecialTextPath.lineTo(arrawRight - arrawW / 2.0f, bottom + dip5);
                                        mSpecialTextPath.lineTo(arrawLeft, bottom);
                                    }
                                }
                                if (xTmpT > xTmp && top < height / 4.0f) {
                                    mSpecialTextPath.lineTo(left + arrawW * 0.6f, bottom);
                                    mSpecialTextPath.lineTo(left, bottom + dip5);
                                } else {
                                    mSpecialTextPath.lineTo(left + Dip.dip2, bottom);
                                    mSpecialTextPath.arcTo(left, bottom - Dip.dip4, left + Dip.dip4, bottom, 90, 90, false);
                                }
                                mSpecialTextPath.close();
                                mSpecialTextPathPaint.setStyle(Style.FILL_AND_STROKE);
                                if (dBean.getType() == 2) {
                                    mSpecialTextPathPaint.setColor(mColorSpecialText1_feinong);
                                } else {
                                    mSpecialTextPathPaint.setColor(mColormSpecialText2_lilv);
                                }
                                mSpecialTextPathPaint.setStrokeWidth(2);
                                canvas.drawPath(mSpecialTextPath, mSpecialTextPathPaint);
                                mSpecialTextPaint.setColor(Color.WHITE);
                                if (dBean.getType() == 2) {
                                    canvas.drawText(mSpecialText1_feinong, xTmpT, ty - mSpecialTextPaint.getFontMetrics().ascent, mSpecialTextPaint);
                                } else {
                                    canvas.drawText(mSpecialText2_lilv, xTmpT, ty - mSpecialTextPaint.getFontMetrics().ascent, mSpecialTextPaint);
                                }
//                        canvas.drawRoundRect(left, top, right, bottom, Dip.dip2, Dip.dip2, mSpecialTextPathPaint);
//                        canvas.drawLine(xTmp - arraw, bottom, xTmp + arraw, bottom, mSpecialTextPathPaint);
//                        canvas.drawLine(xTmp - arraw, bottom, xTmp, bottom + dip5, mSpecialTextPathPaint);
//                        canvas.drawLine(xTmp + arraw, bottom, xTmp, bottom + dip5, mSpecialTextPathPaint);
//                        mSpecialTextPathPaint.setColor(Color.WHITE);
//                        canvas.drawLine(xTmp - arraw, bottom, xTmp + arraw, bottom, mSpecialTextPathPaint);
                            } else {
                                if (dBeanPre != null) {
                                    dBean.setLast_xTmpT(dBeanPre.getLast_xTmpT());
                                } else {
                                    dBean.setLast_xTmpT(mHolder.mLeftNumber);
                                }
                            }
                        }
                    }
                }
            }
            paintRightPart(canvas);
            mHolder.updateTime();
        }
        canvas.restore();
    }

    private int getHeightIndex(int v) {
        int height = getHeight();
        return 1 + ((height - 1) >> 1) - v * ((height >> 2) - 2) / 120;
    }

    private double getTopPaddingByRatio(double value, double marker) {
        int height = getHeight() - 2;
        if (mHolder.isBusinessEffect && mHolder.isTraderEnvKline) {
            height = getHeight();
        }
        if (value < 0) {
            value = 0;
        }
        double topPadding = value * ((double) height) / marker;
        topPadding = height - topPadding > 0 ? height - topPadding : 0;
        if (mHolder.isBesselEffect) {
            topPadding = topPadding - paddingBottomBesselEffect / 2.0f;
        }
        return topPadding;
    }

    private float getTopPaddingByRatio(long value, long marker) {
        int height = getHeight();
        if (value < 0) {
            value = 0;
        }
        float topPadding = value * (height - 2) * 1f / marker;
        topPadding = height - topPadding - 2 > 0 ? height - topPadding - 2 : 0;
        return topPadding;
    }

    @Override
    protected void makeBackground(int width, int height) {
//        super.makeBackground(width, height);
        hasPaintBackground = true;
    }

    @Override
    protected void paintBackground(Canvas canvas) {
        int width = getWidth();
    }

    protected void paintBackgroundSelf(Canvas canvas) {
        float width = getWidth();
        float height = getHeight();
        float paddingLeft = getPaddingLeft();
        float paddingTop = getPaddingTop();
        float paddingRight = getPaddingRight();
        float paddingBottom = getPaddingBottom();
        width = getWidth();
        paddingLeft = this.getPaddingLeft();
        mPortrait = true;
        if (paddingRight == 0) {
            paddingRight = 1;
        }
        if (paddingTop == 0) {
            paddingTop = 1;
        }
        if (paddingBottom == 0) {
            paddingBottom = 1;
        }
        if (mHolder.isBesselEffect) {
            paddingBottom = paddingBottomBesselEffect;
        }
        canvas.save();
        mPaint.setColor(mLineCol);
        mPaint.setStyle(Style.STROKE);
        float strokeW = mPaint.getStrokeWidth();
        mPaint.setStrokeWidth(2);
        int countH = 9;
        int countV = 6;
        if (mHolder.isTraderEnv) {
            countH = 5;
            countV = 8;
            if (mHolder.isBesselEffect) {
                countH = 3;
                countV = 8;
            }
            mPaint.setColor(mLeftArrawLineColor);
        } else if (mHolder.isBusinessEffect) {
            countH = 5;
            countV = 7;
            mPaint.setColor(0xfff6f8fa);
            mPaint.setStrokeWidth(1);
        }
        float hDistance = (width - (mHolder != null ? (mHolder.mRightNumber + mHolder.mLeftNumber) : 0) - paddingLeft - paddingRight) / (countV - 1f);
        float vDistance = (height - paddingTop - paddingBottom) / (countH - 1);
        if (mHolder.isTraderEnv) {

        } else if (mHolder.isBusinessEffect) {
            canvas.drawLine(0.5f + mHolder.mLeftNumber, 0.5f, width - 0.5f - (mHolder != null ? mHolder.mRightNumber : 0), 0.5f, mPaint);
        } else {
            canvas.drawLine(0.5f + mHolder.mLeftNumber, 0.5f, width - 0.5f - (mHolder != null ? mHolder.mRightNumber : 0), 0.5f, mPaint);
        }
        if (mHolder.isBesselEffect) {

        } else {
            if (mHolder.isBusinessEffect) {
                mPaint.setColor(0xffd3d3d3);
                mPaint.setStrokeWidth(1);
            }
            canvas.drawLine(0.5f + mHolder.mLeftNumber, height - 0.5f, width - 0.5f - (mHolder != null ? mHolder.mRightNumber : 0), height - 0.5f, mPaint);
        }
//        canvas.drawLine(width - 0.5f, 0.5f, width - 0.5f, height - 0.5f, mPaint);
//        canvas.drawLine(0.5f, 0.5f, 0.5f, height - 0.5f, mPaint);
        mPaint.setStrokeWidth(1);
        for (int i = 1; i < (countH - 1); i++) {
            //横向虚线
            if (mHolder.isBesselEffect) {

            } else {
                float top = paddingTop + i * vDistance;
                mPaint.setStrokeWidth(1);
                mPaint.setPathEffect(EFFECT);
                if (mHolder.isTraderEnv) {
                    mPaint.setColor(mLeftArrawDashPathEffectLineColor);
                } else if (mHolder.isBusinessEffect) {
                    mPaint.setPathEffect(null);
                    mPaint.setColor(0xfff6f8fa);
                    mPaint.setStrokeWidth(1);
                }
                canvas.drawLine(paddingLeft + mHolder.mLeftNumber, top, width - paddingRight - mHolder.mRightNumber, top, mPaint);
            }
        }
        for (int i = 0; i < (countV); i++) {
            //竖向虚线
            float left = paddingLeft + mHolder.mLeftNumber + i * hDistance;
            if (mHolder.isTraderEnv) {
                if (mHolder.isBesselEffect) {

                } else {
                    if (i == 0) {
                        mPaint.setPathEffect(null);
                        mPaint.setStrokeWidth(2);
                        mPaint.setColor(mLeftArrawDashPathEffectLineColor);
                        canvas.drawLine(left, paddingTop, left, height - paddingBottom, mPaint);
                        canvas.drawLine(left, paddingTop, left - Dip.dip4, paddingTop + Dip.dip4, mPaint);
                        canvas.drawLine(left, paddingTop, left + Dip.dip4, paddingTop + Dip.dip4, mPaint);
                    } else {
                        if (!mHolder.isTraderEnvKline) {
                            mPaint.setPathEffect(EFFECT);
                            mPaint.setStrokeWidth(1);
                            mPaint.setColor(mLeftArrawLineColor);
                            canvas.drawLine(left, paddingTop + vDistance, left, height - paddingBottom, mPaint);
                        }
                    }
                }
            } else if (mHolder.isBusinessEffect) {
                mPaint.setPathEffect(EFFECT);
                mPaint.setStrokeWidth(1);
                mPaint.setColor(0xfff6f8fa);
                canvas.drawLine(left, paddingTop, left, height - paddingBottom, mPaint);
            } else {
                mPaint.setPathEffect(null);
                mPaint.setStrokeWidth(1);
                canvas.drawLine(left, height - paddingBottom - dip5, left, height - paddingBottom, mPaint);
            }
        }
        mPaint.setPathEffect(null);
        mPaint.setStrokeWidth(2);
//        canvas.drawLine(width - (mHolder != null ? (mHolder.mRightNumber + mHolder.mLeftNumber) : 0), paddingTop, width - (mHolder != null ? (mHolder.mRightNumber + mHolder.mLeftNumber) : 0), height - paddingBottom, mPaint);
        mPaint.setStrokeWidth(strokeW);
        mPaint.setPathEffect(null);
        canvas.restore();
    }

    protected void paintRightPart(Canvas canvas) {
        float width = getWidth();
        int height = getHeight();
        float paddingLeft = getPaddingLeft();
        float paddingTop = getPaddingTop();
        float paddingRight = getPaddingRight();
        float paddingBottom = getPaddingBottom();
        width = getWidth();
        paddingLeft = this.getPaddingLeft();
        mPortrait = true;
        if (paddingRight == 0) {
            paddingRight = 1;
        }
        if (paddingTop == 0) {
            paddingTop = 1;
        }
        if (paddingBottom == 0) {
            paddingBottom = 1;
        }
        if (mHolder.isBesselEffect) {
            paddingBottom = paddingBottomBesselEffect;
        }
        canvas.save();
        double gap = mMaxPrice - mMinPrice;
        mPaint.setStyle(Style.FILL);
        mPaint.setStrokeWidth(2.0f);
        double closePrice = mHolder.getDataModel().getmPreClosePrice();
        if (closePrice == 0) {
            closePrice = (mMaxPrice + mMinPrice) / 2;
        }
        String valueStr;
        double inc;
        String increase;
        mPaint.setTextSize(mTextSizeReal);

        double value;
        int count = 9;
        if (mHolder.isTraderEnv) {
            count = 5;
            if (mHolder.isBesselEffect) {
                count = 3;
            }
        } else if (mHolder.isBusinessEffect) {
            count = 5;
        }
        for (int i = 0; i < count; i++) {
            value = mMaxPrice - gap * i / (count - 1);
            if (i == 0 && mHolder.isTraderEnv) {
                if (mHolder.isBesselEffect) {

                } else {
                    continue;
                }
            } else if (i == 0 && mHolder.isBusinessEffect) {
//                continue;
            }
            if (mHolder.isTraderEnv) {

            } else if (mHolder.isBusinessEffect) {

            } else {
                if (value < 0) {
                    continue;
                }
            }
            valueStr = (value >= -0.05 && value <= 0.05) ? "0.0" : StockChartUtility.formatPrice(value, mHolder.getDataModel().getmRealLen());
            if (mHolder.isTraderEnv) {

            } else if (mHolder.isBusinessEffect) {
                valueStr = (value >= -0.05 && value <= 0.05) ? "0" : StockChartUtility.formatPrice(value, 0);
            } else {
                inc = 100 * (double) Math.abs(value - closePrice) / closePrice;
                increase = String.format("%.2f", inc) + "%";
                if (i > (count / 2)) {
                    increase = "-" + increase;
                }
            }
            mPaint.setColor(mHolder.getUpColor());
            if (i == (count / 2)) {
                mPaint.setColor(mHolder.getUpColor());
            } else {
                //mPaint.setColor(i < (count / 2) ? mHolder.getUpColor() : mHolder.getDownColor());
            }
            if (mHolder.isTraderEnv) {
                if (mHolder.isBesselEffect) {
                    mPaint.setColor(0xff686f89);
                    mPaint.setTextSize(FunctionHelper.sp2px(13.0f));
                } else {
                    mPaint.setColor(mLeftArrawTextColor);
                    mPaint.getTextBounds(valueStr, 0, valueStr.length(), mRect);
                    while (mRect.width() > mHolder.mLeftNumber - dip5) {
                        mPaint.setTextSize(mPaint.getTextSize() - 1);
                        mPaint.getTextBounds(valueStr, 0, valueStr.length(), mRect);
                    }
                }
            } else if (mHolder.isBusinessEffect) {
                mPaint.setColor(0xff999999);
                mPaint.setTextSize(FunctionHelper.sp2px(11.0f));
                mPaint.getTextBounds(valueStr, 0, valueStr.length(), mRect);
                while (mRect.width() > mHolder.mLeftNumber - dip5) {
                    mPaint.setTextSize(mPaint.getTextSize() - 1);
                    mPaint.getTextBounds(valueStr, 0, valueStr.length(), mRect);
                }
            }
            int yTmp = (int) (2 + (height - mPaint.getTextSize() - 5) * i / (count - 1));
            if (mPortrait) {
                if (mHolder.isTraderEnv) {
                    if (mHolder.isBesselEffect) {
                        yTmp = (int) (0 + (height - paddingBottom) * i / (count - 1));
                        mPaint.setTextAlign(Align.LEFT);
                        canvas.drawText(valueStr, dip20, yTmp - mPaint.getFontMetrics().ascent, mPaint);
                    } else {
                        mPaint.setTextAlign(Align.RIGHT);
                        canvas.drawText(valueStr, mHolder.mLeftNumber - dip5, yTmp - mPaint.getFontMetrics().ascent, mPaint);
                    }
                } else if (mHolder.isBusinessEffect) {
                    mPaint.setTextAlign(Align.RIGHT);
                    canvas.drawText(valueStr, mHolder.mLeftNumber - dip5, yTmp - mPaint.getFontMetrics().ascent, mPaint);
                } else {
                    mPaint.setTextAlign(Align.LEFT);
                    canvas.drawText(valueStr, 2, yTmp - mPaint.getFontMetrics().ascent, mPaint);
                }
            } else {
                mPaint.setTextAlign(Align.RIGHT);
                canvas.drawText(valueStr, paddingLeft - 2, yTmp - mPaint.getFontMetrics().ascent, mPaint);
//                mPaint.setTextAlign(Align.LEFT);
//                canvas.drawText(valueStr, width - 2, yTmp - mPaint.getFontMetrics().ascent, mPaint);
            }
        }
        canvas.restore();
    }

    public void resetDataModel() {
        if (mHolder != null) {
            if (mHolder.getDataModel() == null) {
                return;
            }
            boolean firstLineOK = false;
            double[][] data = mHolder.getDataModel().getMinData();
            if (data != null) {
                if (mHolder.isTraderEnv) {
                    mHolder.mDisplayCount = data.length;
                } else if (mHolder.isBusinessEffect) {
                    mHolder.mDisplayCount = data.length;
                }
                if (mCurrentPrice == null || mCurrentPrice.length != data.length) {
                    mCurrentPrice = new double[data.length];
                }
                int dataLength = mHolder.getDataModel().size();
                for (int i = 0; i < dataLength; i++) {
                    mCurrentPrice[i] = data[i][1];
                }
                resetMaxPriceAndMinPrice();
                firstLineOK = true;
            }
            boolean secondLineOK = false;
            double[][] data2 = mHolder.getDataModel().getMinData2();
            if (data2 != null && data2.length > 0) {
                if (mCurrentPrice2 == null || mCurrentPrice2.length != data2.length) {
                    mCurrentPrice2 = new double[data2.length];
                }
                int dataLength = mHolder.getDataModel().size2();
                for (int i = 0; i < dataLength; i++) {
                    mCurrentPrice2[i] = data2[i][1];
                }
                resetMaxPriceAndMinPrice2(firstLineOK);
                secondLineOK = true;
            }
            boolean thirdLineOK = false;
            double[][] data3 = mHolder.getDataModel().getMinData3();
            if (data3 != null && data3.length > 0) {
                if (mCurrentPrice3 == null || mCurrentPrice3.length != data3.length) {
                    mCurrentPrice3 = new double[data3.length];
                }
                int dataLength = mHolder.getDataModel().size3();
                for (int i = 0; i < dataLength; i++) {
                    mCurrentPrice3[i] = data3[i][1];
                }
                resetMaxPriceAndMinPrice3(firstLineOK, secondLineOK);
                thirdLineOK = true;
            }

            if (mHolder.isTraderEnv) {
                if (mHolder.isBesselEffect) {
                    _row = 2;
                    if (mMinPrice > 0) {
                        mMinPrice = 0;
                    }
                    double cha = mMaxPrice - mMinPrice;
                    double space_value = cha * 1.0f / _row;
                    double maxPrice_Temp = mMaxPrice + space_value;
                    double minPrice_Temp = mMinPrice;
                    if (mMinPrice < 0) {
                        minPrice_Temp = mMinPrice - space_value / 2.0f;
                    }
                    space_value = (maxPrice_Temp - minPrice_Temp) * 1.0f / _row;

                    if (mMinPrice == mMaxPrice && mMinPrice == 0) {
                        space_value = 0;
                    }
                    if (mMinPrice == mMaxPrice && mMinPrice < 0) {
                        space_value = 1;
                    }

                    if (mMinPrice < 0) {
                        mMinPrice = mMinPrice - space_value / 2.0f;
                    } else {
                    }
                    mMaxPrice = mMinPrice + space_value * _row;
                } else {
                    if (mMaxPrice > 0 && mMinPrice > 0) {
                        mMinPrice = 0;
                    } else {
                        double gap = mMaxPrice - mMinPrice;
                        if (mMinPrice >= 0) {
                            mMinPrice = mMinPrice * 2f / 3f;
                        } else {
                            mMinPrice = mMinPrice - Math.abs(mMinPrice) * 1f / 3f;
                        }
                    }
                    gap = mMaxPrice - mMinPrice;
                    mMaxPrice = mMaxPrice + gap / 3L;
                    gap = mMaxPrice - mMinPrice;
                    mMaxPrice = mMaxPrice + gap / 3L;
                }
            } else if (mHolder.isBusinessEffect) {
                if (mMinPrice > 0) {
                    mMinPrice = 0;
                }
                gap = mMaxPrice - mMinPrice;
                int diff = (int) gap % 4;
                if (diff != 0) {
                    mMaxPrice = mMaxPrice + (4 - diff);
                }
                if (mMinPrice == 0 && mMaxPrice == 0) {
                    mMaxPrice = 4;
                }
            }
            if (mHolder.isBesselEffect) {
                startTime = SystemClock.elapsedRealtime();
                postInvalidate();
            }
        }
        invalidate();
    }

    public double[][] getDetailByIndex(int index) {
        double[][] details = null;
        if (mHolder.getDataModel() != null) {
            double[][] data = mHolder.getDataModel().getMinData();
            if (data == null || index < 0 || data.length <= 0 || index >= data.length) {
                return details;
            }
            details = new double[5][2];
            details[0][0] = data[index][0];
            details[0][1] = 0xFF222222;
            details[1][0] = mCurrentPrice[index];
            details[1][1] = 0xFF222222;
        }
        return details;
    }

    public String getDetailTimeByIndex(int index) {
        String details = "";
        if (mHolder.getDataModel() != null) {
            double[][] data = mHolder.getDataModel().getMinData();
            if (data == null || index < 0 || data.length <= 0 || index >= data.length) {
                return details;
            }
            details = mHolder.mData.get(index).getTimeString();
        }
        return details;
    }

    public String getDetailTimeByIndex2(int index) {
        String details = "";
        if (mHolder.getDataModel() != null) {
            double[][] data2 = mHolder.getDataModel().getMinData2();
            if (data2 == null || index < 0 || data2.length <= 0 || index >= data2.length) {
                return details;
            }
            details = mHolder.mData2.get(index).getTimeString();
        }
        return details;
    }

    private void resetMaxPriceAndMinPrice() {
        if (mHolder == null || mHolder.getDataModel() == null) {
            return;
        }
        double[][] data = mHolder.getDataModel().getMinData();
        if (data == null) {
            return;
        }
        int len = mHolder.getDataModel().size();
        int total = mHolder.getDataModel().size();
        int start = 0;
        if (total > mHolder.mDisplayCount) {
            total = mHolder.mDisplayCount;
        }
        start = len >= mHolder.mDisplayCount ? (len - mHolder.mDisplayCount) : 0;
        if (mHolder.mDisplayOffset > 0) {
            if (start - mHolder.mDisplayOffset >= 0) {
                start = start - mHolder.mDisplayOffset;
            }
        }
        mHolder.mDisplayStart = start;
        double maxPointPrice = Integer.MIN_VALUE;
        double minPointPrice = Integer.MAX_VALUE;
//        for (int i = 0; i < len; i++) {
        for (int i = start; i < len && ((i - start) < total); i++) {
            if (data[i][1] > maxPointPrice) {
                maxPointPrice = data[i][1];
            }
            if (data[i][1] < minPointPrice) {
                minPointPrice = data[i][1];
            }
        }
        mMaxPrice = maxPointPrice;// * 1.1f;
        mMinPrice = minPointPrice;// * 0.9f;
        if (mHolder.isTraderEnv) {

        } else if (mHolder.isBusinessEffect) {

        } else {
            if (mMinPrice == mMaxPrice) {
                mMinPrice = 0d;
            }
        }
        if (mMinPrice > mMaxPrice) {
            mMinPrice = 0d;
            mMaxPrice = 0d;
        }
//        if (mMinPrice > 0) {
//            mMinPrice = 0;
//        }
//        if (mMaxPrice < 7) {
//            mMaxPrice = 7;
//        }
    }

    private void resetMaxPriceAndMinPrice2(boolean firstLineOK) {
        if (mHolder == null || mHolder.getDataModel() == null) {
            return;
        }
        double[][] data2 = mHolder.getDataModel().getMinData2();
        if (data2 == null && data2.length > 0) {
            return;
        }
        int len = mHolder.getDataModel().size2();
        int total = mHolder.getDataModel().size2();
        int start = 0;
        if (total > mHolder.mDisplayCount) {
            total = mHolder.mDisplayCount;
        }
        start = len >= mHolder.mDisplayCount ? (len - mHolder.mDisplayCount) : 0;
        if (mHolder.mDisplayOffset > 0) {
            if (start - mHolder.mDisplayOffset >= 0) {
                start = start - mHolder.mDisplayOffset;
            }
        }
        mHolder.mDisplayStart = start;
        double maxPointPrice = Integer.MIN_VALUE;
        double minPointPrice = Integer.MAX_VALUE;
        if (firstLineOK) {
            maxPointPrice = mMaxPrice;
            minPointPrice = mMinPrice;
        }
//        for (int i = 0; i < len; i++) {
        for (int i = start; i < len && ((i - start) < total); i++) {
            if (data2[i][1] > maxPointPrice) {
                maxPointPrice = data2[i][1];
            }
            if (data2[i][1] < minPointPrice) {
                minPointPrice = data2[i][1];
            }
        }
        mMaxPrice = maxPointPrice;// * 1.1f;
        mMinPrice = minPointPrice;// * 0.9f;
        if (mHolder.isTraderEnv) {

        } else if (mHolder.isBusinessEffect) {

        } else {
            if (mMinPrice == mMaxPrice) {
                mMinPrice = 0d;
            }
        }
        if (mMinPrice > mMaxPrice) {
            mMinPrice = 0d;
            mMaxPrice = 0d;
        }
//        if (mMinPrice > 0) {
//            mMinPrice = 0;
//        }
//        if (mMaxPrice < 7) {
//            mMaxPrice = 7;
//        }
    }

    private void resetMaxPriceAndMinPrice3(boolean firstLineOK, boolean secondLineOK) {
        if (mHolder == null || mHolder.getDataModel() == null) {
            return;
        }
        double[][] data3 = mHolder.getDataModel().getMinData3();
        if (data3 == null && data3.length > 0) {
            return;
        }
        int len = mHolder.getDataModel().size3();
        int total = mHolder.getDataModel().size3();
        int start = 0;
        if (total > mHolder.mDisplayCount) {
            total = mHolder.mDisplayCount;
        }
        start = len >= mHolder.mDisplayCount ? (len - mHolder.mDisplayCount) : 0;
        if (mHolder.mDisplayOffset > 0) {
            if (start - mHolder.mDisplayOffset >= 0) {
                start = start - mHolder.mDisplayOffset;
            }
        }
        mHolder.mDisplayStart = start;
        double maxPointPrice = Integer.MIN_VALUE;
        double minPointPrice = Integer.MAX_VALUE;
        if (firstLineOK || secondLineOK) {
            maxPointPrice = mMaxPrice;
            minPointPrice = mMinPrice;
        }
//        for (int i = 0; i < len; i++) {
        for (int i = start; i < len && ((i - start) < total); i++) {
            if (data3[i][1] > maxPointPrice) {
                maxPointPrice = data3[i][1];
            }
            if (data3[i][1] < minPointPrice) {
                minPointPrice = data3[i][1];
            }
        }
        mMaxPrice = maxPointPrice;// * 1.1f;
        mMinPrice = minPointPrice;// * 0.9f;
        if (mHolder.isTraderEnv) {

        } else if (mHolder.isBusinessEffect) {

        } else {
            if (mMinPrice == mMaxPrice) {
                mMinPrice = 0d;
            }
        }
        if (mMinPrice > mMaxPrice) {
            mMinPrice = 0d;
            mMaxPrice = 0d;
        }
//        if (mMinPrice > 0) {
//            mMinPrice = 0;
//        }
//        if (mMaxPrice < 7) {
//            mMaxPrice = 7;
//        }
    }

    public double getMaxPrice() {
        return mMaxPrice;
    }

    public double getMinPrice() {
        return mMinPrice;
    }

    public double[] getCurrentPrices() {
        return mCurrentPrice;
    }

    public void setPortrait(boolean value) {
        mPortrait = value;
    }

}
