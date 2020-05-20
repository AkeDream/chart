package com.wifieye.akechartdemo.line;


import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;

import static com.wifieye.akechartdemo.line.Dip.dip11;
import static com.wifieye.akechartdemo.line.Dip.dip12;
import static com.wifieye.akechartdemo.line.Dip.dip22;
import static com.wifieye.akechartdemo.line.Dip.dip5;


public class DiffContainerWaihuiTianyan extends FrameLayout implements OnClickListener,
        OnLongClickListener {
    private static final String CLASS = DiffContainerWaihuiTianyan.class.getSimpleName() + " ";
    public int mDisplayCount = 60;//288;//分时显示3小时=180根
    public int mDisplayOffset = 0;//分时显示3小时=180根
    public int mDisplayStart = 0;//分时显示3小时=180根
    public int mRightNumber = FunctionHelper.dp2pxInt(5);
    public int mLeftNumber = FunctionHelper.dp2pxInt(25);
    public int lastTouchX = 0;
    public boolean isTraderEnv = false;
    public boolean isTraderEnvKline = false;
    public boolean isBusinessEffect = false;
    public boolean isBesselEffect = false;
    public String dataName1 = "";
    public String dataName2 = "";
    public int dataColor1 = 0xff3e9ae8;
    public int dataColor2 = 0xff5abc80;
    public int dataColor3 = 0xffe06155;
    private ViewPager viewPager;
    RelativeLayout.LayoutParams rlp;
    LinearLayout.LayoutParams llp;
    LayoutParams flp;
    private Context mContext;
    private LinearLayout mLayoutBelow;
    private LinearLayout mLayoutLeft;
    private DiffWaihuiTianyanContentLayout mDiffWaihuiTianyanContentLayout;
    private DiffTreadPriceWaihuiTianyan mDiffTreadPrice; // 均价现价图
    private DiffMoveLineDetailViewWaihuiTianyan mMinDetailView;
    private DiffBottomTimeLineViewWaihuiTianyan mDiffBottomTimeLineViewWaihuiTianyan;
    //    private ActivityHolder mHolder;
    public List<DiffDataBean.ContentBean.DBean> mData = new ArrayList<>();
    public List<DiffDataBean.ContentBean.DBean> mData2 = new ArrayList<>();
    public List<DiffDataBean.ContentBean.DBean> mData3 = new ArrayList<>();
    private DataBean mDataBean = new DataBean();
    private boolean isNight = false;
    private int mScreenIndex = -1;
    private TextView mBeginTimeView;
    private TextView mEndTimeView;
    private TextView mMidTimeView1;
    private TextView mMidTimeView2;
    private TextView mMidTimeView3;
    private TextView mMidTimeView4;
    private TextView mMidTimeView5;
    private TextView mMidTimeView6;
    private TextView[] mDateStringView;
    private DiffMoveLineWaihuiTianyanDetailTimeView mKChartMoveLineDetailTimeView;
    private LinearLayout timeLayoutWuri;
    private LinearLayout timeLayout;
    private int mTextColor = 0xff222222;
    private int mUpColor = 0xffeb1a28;
    private int mDownColor = 0xff4ca92a;
    private int mEqualColor = 0xff858c93;
    private int mWidth;
    private int height;
    private StockType mStockType;
    private int mDetailsViewHeight;
    private TextView mChangeLandView;
    private Typeface fontFace;

    public DiffContainerWaihuiTianyan(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DiffContainerWaihuiTianyan(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        try {
//            if (mContext != null && mContext instanceof TraderExperienceActivity) {
//                isTraderEnv = true;
//            } else if (mContext != null && mContext instanceof BusinessEffectActivity) {
//                isBusinessEffect = true;
//                mRightNumber = FunctionHelper.dp2pxInt(20);
//                mLeftNumber = FunctionHelper.dp2pxInt(35);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fontFace = Typeface.createFromAsset(context.getAssets(), "fonts/DIN-Medium.otf");
        setContent();
        // mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public boolean isTraderEnv() {
        return isTraderEnv;
    }

    public void setTraderEnv(boolean traderEnv) {
        isTraderEnv = traderEnv;
    }

    public boolean isBesselEffect() {
        return isBesselEffect;
    }

    public void setBesselEffect(boolean besselEffect) {
        isBesselEffect = besselEffect;
        if (isBesselEffect) {
            if (mDiffBottomTimeLineViewWaihuiTianyan != null) {
                mDiffBottomTimeLineViewWaihuiTianyan.setBackgroundColor(0xff202339);
                mDiffBottomTimeLineViewWaihuiTianyan.setVisibility(INVISIBLE);
            }
        }
    }

    public boolean isBusinessEffect() {
        return isBusinessEffect;
    }

    public void setBusinessEffect(boolean businessEffect) {
        isBusinessEffect = businessEffect;
    }

    private void setContent() {
        mDetailsViewHeight = FunctionHelper.dp2pxInt(65);
        setLayout();
//        setChangeLandView();
//        changeLookFace();
    }

    private void setLayout() {
        mMinDetailView = new DiffMoveLineDetailViewWaihuiTianyan(mContext);
        flp = new LayoutParams(LayoutParams.MATCH_PARENT, dip22);
        flp.leftMargin = 0;
        flp.rightMargin = 0;
        addView(mMinDetailView, flp);
        mMinDetailView.setHolder(this);
        mMinDetailView.setVisibility(INVISIBLE);

        mLayoutBelow = new LinearLayout(mContext);
        mLayoutBelow.setOrientation(LinearLayout.HORIZONTAL);
        flp = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        flp.leftMargin = 0;
        flp.rightMargin = 0;
        flp.topMargin = dip22;
        if (isTraderEnv) {
            flp.topMargin = 0;
        } else if (isBusinessEffect) {
            flp.topMargin = 0;
        }
        addView(mLayoutBelow, flp);
        setLayouBelow(mLayoutBelow);

        flp = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        flp.leftMargin = 0;
        flp.rightMargin = 0;
        flp.bottomMargin = dip11;
        flp.topMargin = dip22;
        if (isTraderEnv) {
            flp.topMargin = 0;
        } else if (isBusinessEffect) {
            flp.topMargin = 0;
        }
    }

    private void setLayouBelow(LinearLayout mLayoutBelow) {
        mLayoutLeft = new LinearLayout(mContext);
        mLayoutLeft.setOrientation(LinearLayout.VERTICAL);
        llp = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 2);
        mLayoutBelow.addView(mLayoutLeft, llp);
        setLayouLeft(mLayoutLeft);
    }

    private void setLayouLeft(LinearLayout mLayoutLeft) {
        FrameLayout mDiffTreadPriceLayer = new FrameLayout(mContext);
        llp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 2);
//        llp.bottomMargin = FunctionHelper.dp2pxInt(3);
        mLayoutLeft.addView(mDiffTreadPriceLayer, llp);
//        setDiffTreadPrice(mDiffTreadPriceLayer);
        setKChartContentLayout(mDiffTreadPriceLayer);

        RelativeLayout timeLayout = new RelativeLayout(mContext);
        if (isBusinessEffect) {
            mLayoutLeft.addView(timeLayout, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, dip11 + dip12));
        } else {
            mLayoutLeft.addView(timeLayout, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, dip11));
        }
        setTimeLayout(timeLayout);
    }

    private void setDiffTreadPrice(FrameLayout mDiffTreadPriceLayer) {
        mDiffTreadPrice = new DiffTreadPriceWaihuiTianyan(mContext);
        mDiffTreadPrice.setHolder(this);
        mDiffTreadPrice.setOnClickListener(this);
        flp = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        mDiffTreadPriceLayer.addView(mDiffTreadPrice, flp);
        if (isTraderEnv) {

        } else if (isBusinessEffect) {

        } else {
            mDiffTreadPrice.setOnLongClickListener(this);
        }
    }

    private void setKChartContentLayout(FrameLayout mDiffTreadPriceLayer) {
        mDiffWaihuiTianyanContentLayout = new DiffWaihuiTianyanContentLayout(mContext);
        mDiffTreadPriceLayer.addView(mDiffWaihuiTianyanContentLayout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mDiffWaihuiTianyanContentLayout.setHolder(this);
        setKChartContentLayout(mDiffWaihuiTianyanContentLayout);
    }

    private void setKChartContentLayout(DiffWaihuiTianyanContentLayout mDiffContentLayout) {
        mDiffTreadPrice = new DiffTreadPriceWaihuiTianyan(mContext);
        mDiffTreadPrice.setHolder(this);
        mDiffTreadPrice.setOnClickListener(this);
        llp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        mDiffContentLayout.addView(mDiffTreadPrice, llp);
        if (isTraderEnv) {

        } else if (isBusinessEffect) {

        } else {
            mDiffTreadPrice.setOnLongClickListener(this);
        }
    }

    private void setTimeLayout(RelativeLayout timeLayoutParent) {
        timeLayout = new LinearLayout(mContext);
        timeLayout.setOrientation(LinearLayout.HORIZONTAL);
        timeLayout.setPadding(mLeftNumber, 0, mRightNumber, 0);
        timeLayoutParent.addView(timeLayout, new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        timeLayoutWuri = new LinearLayout(mContext);
        timeLayoutParent.addView(timeLayoutWuri, new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        mBeginTimeView = new TextView(mContext);
        mBeginTimeView.setTextColor(mTextColor);
        mBeginTimeView.setTextSize(8);
        mBeginTimeView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        timeLayout.addView(mBeginTimeView, new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));

        mMidTimeView1 = new TextView(mContext);
        mMidTimeView1.setTextColor(mTextColor);
        mMidTimeView1.setTextSize(8);
        mMidTimeView1.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        timeLayout.addView(mMidTimeView1, new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 2));
        mMidTimeView2 = new TextView(mContext);
        mMidTimeView2.setTextColor(mTextColor);
        mMidTimeView2.setTextSize(8);
        mMidTimeView2.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        timeLayout.addView(mMidTimeView2, new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 2));
        mMidTimeView3 = new TextView(mContext);
        mMidTimeView3.setTextColor(mTextColor);
        mMidTimeView3.setTextSize(8);
        mMidTimeView3.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        timeLayout.addView(mMidTimeView3, new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 2));
        mMidTimeView4 = new TextView(mContext);
        mMidTimeView4.setTextColor(mTextColor);
        mMidTimeView4.setTextSize(8);
        mMidTimeView4.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        timeLayout.addView(mMidTimeView4, new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 2));
        if (isTraderEnv) {
            mMidTimeView5 = new TextView(mContext);
            mMidTimeView5.setTextColor(mTextColor);
            mMidTimeView5.setTextSize(8);
            mMidTimeView5.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            timeLayout.addView(mMidTimeView5, new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 2));
            mMidTimeView6 = new TextView(mContext);
            mMidTimeView6.setTextColor(mTextColor);
            mMidTimeView6.setTextSize(8);
            mMidTimeView6.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            timeLayout.addView(mMidTimeView6, new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 2));
        } else if (isBusinessEffect) {
            mMidTimeView5 = new TextView(mContext);
            mMidTimeView5.setTextColor(mTextColor);
            mMidTimeView5.setTextSize(8);
            mMidTimeView5.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            timeLayout.addView(mMidTimeView5, new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 2));
            mMidTimeView6 = new TextView(mContext);
            mMidTimeView6.setTextColor(mTextColor);
            mMidTimeView6.setTextSize(8);
            mMidTimeView6.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            timeLayout.addView(mMidTimeView6, new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 2));
        }

        mEndTimeView = new TextView(mContext);
        mEndTimeView.setTextColor(mTextColor);
        mEndTimeView.setTextSize(8);
        mEndTimeView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        timeLayout.addView(mEndTimeView, new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        timeLayout.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        mDateStringView = new TextView[5];
        for (int i = 0; i < 5; i++) {
            mDateStringView[i] = new TextView(mContext);
            mDateStringView[i].setTextColor(mTextColor);
            mDateStringView[i].setTextSize(8);
            mDateStringView[i].setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            timeLayoutWuri.addView(mDateStringView[i], new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        }

        mKChartMoveLineDetailTimeView = new DiffMoveLineWaihuiTianyanDetailTimeView(mContext);
        rlp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, dip11);
        mKChartMoveLineDetailTimeView.setVisibility(INVISIBLE);
        mKChartMoveLineDetailTimeView.setHolder(this);
        timeLayoutParent.addView(mKChartMoveLineDetailTimeView, rlp);

        if (isTraderEnv) {
            mDiffBottomTimeLineViewWaihuiTianyan = new DiffBottomTimeLineViewWaihuiTianyan(mContext);
            mDiffBottomTimeLineViewWaihuiTianyan.setHolder(this);
            mDiffBottomTimeLineViewWaihuiTianyan.setVisibility(View.VISIBLE);
            mDiffBottomTimeLineViewWaihuiTianyan.setBackgroundColor(Color.WHITE);
            timeLayoutParent.addView(mDiffBottomTimeLineViewWaihuiTianyan, new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        } else if (isBusinessEffect) {
            mDiffBottomTimeLineViewWaihuiTianyan = new DiffBottomTimeLineViewWaihuiTianyan(mContext);
            mDiffBottomTimeLineViewWaihuiTianyan.setHolder(this);
            mDiffBottomTimeLineViewWaihuiTianyan.setVisibility(View.VISIBLE);
            mDiffBottomTimeLineViewWaihuiTianyan.setBackgroundColor(Color.WHITE);
            timeLayoutParent.addView(mDiffBottomTimeLineViewWaihuiTianyan, new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
    }

    private void setChangeLandView() {
        mChangeLandView = new TextView(mContext);
        mChangeLandView.setId(mChangeLandView.hashCode());
        mChangeLandView.setBackgroundColor(0xff2F3136);
        mChangeLandView.setPadding(dip5, dip5, dip5, dip5);
        mChangeLandView.setText("横屏");
        mChangeLandView.setTextSize(10);
        mChangeLandView.setOnClickListener(this);
        rlp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        rlp.addRule(RelativeLayout.CENTER_VERTICAL);
        // mDiffTradeVolumnViewLayer.addView(mChangeLandView, rlp);
    }

    /**
     * @param isChanged 横竖屏切换
     */
    private void layoutAsNeeded(boolean isChanged) {
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if ((h > 0 && h != oldh) || (w > 0 && w != oldw)) {
            this.mWidth = w;
            this.height = h;
            layoutAsNeeded(true);
        }
    }

//    public void setHolder(ActivityHolder holder) {
//        mHolder = holder;
//    }

    public DiffContainerWaihuiTianyan getHolder() {
        return this;
    }

    @Override
    public void onClick(View v) {
        if (v == mChangeLandView) {
            ((Activity) getContext())
                    .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
    }

    public DiffTreadPriceWaihuiTianyan getTreadPriceView() {
        return mDiffTreadPrice;
    }

    public void resetView() {
        mDiffTreadPrice.invalidate();
    }

    public double getDiffTreadPriceMaxValue() {
        return mDiffTreadPrice.getMaxPrice();
    }

    public double getDiffTreadPriceMinValue() {
        return mDiffTreadPrice.getMinPrice();
    }

    public double[] getDiffTreadCurrentPrice() {
        return mDiffTreadPrice.getCurrentPrices();
    }

    public int getTreadPriceViewWidth() {
        return mDiffTreadPrice.getWidth();
    }

    public DataBean getDataModel() {
        return mDataBean;
    }

    public void displayIndexDetailByScreenIndex() {
        if (mKChartMoveLineDetailTimeView != null) {
            mKChartMoveLineDetailTimeView.setDetailData();
            if (mKChartMoveLineDetailTimeView.getVisibility() == View.VISIBLE) {
                mKChartMoveLineDetailTimeView.postInvalidate();
            }
        }
    }

    public void setMoveViewVisibility(int visible) {
        if (visible == View.VISIBLE) {
            mKChartMoveLineDetailTimeView.setVisibility(VISIBLE);
            mMinDetailView.setVisibility(VISIBLE);
        } else {
            mKChartMoveLineDetailTimeView.setVisibility(INVISIBLE);
            mMinDetailView.setVisibility(INVISIBLE);
            setScreenIndex(-1);
        }
    }

    public int getScreenIndex() {
        return mScreenIndex;
    }

    public void setScreenIndex(int index) {
        int len = getDataModel().size();
        if (index == -1) {
            index = len - 1;
        }
        if (index >= len) {
            index = len - 1;
        }
        if (index < mDisplayStart) {
            index = mDisplayStart;
        }
        mScreenIndex = index;
    }

    private void setStockType(StockType type) {
        layoutAsNeeded(false);
    }

    @Override
    public boolean onLongClick(View v) {
        if ((v == mDiffTreadPrice) && mDiffWaihuiTianyanContentLayout.getTouchModel() == DiffWaihuiTianyanContentLayout.SINGLE_TOUCH_STATUS
                && getDataModel() != null) {
            if (getDataModel() != null) {
                setMoveViewVisibility(View.VISIBLE);
                ((DiffChartBaseViewWaihuiTianyan) v).changePosition();
            }
        }
        return true;
    }
    private MinDisplayModel mDisplayModel = MinDisplayModel.NORMAL;
    public enum MinDisplayModel {
        NORMAL, CURSOR
    }

    public MinDisplayModel getDisplayModel() {
        return mDisplayModel;
    }

    public void initViewData() {
        if (getDataModel() == null) {
            return;
        }
        if (getDataModel() != null) {
            if (mBeginTimeView.getVisibility() != VISIBLE) {
                mBeginTimeView.setVisibility(VISIBLE);
            }
            if (mEndTimeView.getVisibility() != VISIBLE) {
                mEndTimeView.setVisibility(VISIBLE);
            }
            if (timeLayoutWuri.getVisibility() != INVISIBLE) {
                timeLayoutWuri.setVisibility(INVISIBLE);
            }
            updateTime();
            if (mMinDetailView != null) {
                mMinDetailView.invalidate();
            }
        } else {
            if (mDateStringView != null) {
                if (mBeginTimeView.getVisibility() != INVISIBLE) {
                    mBeginTimeView.setVisibility(INVISIBLE);
                }
                if (mEndTimeView.getVisibility() != INVISIBLE) {
                    mEndTimeView.setVisibility(INVISIBLE);
                }
                if (timeLayoutWuri.getVisibility() != VISIBLE) {
                    timeLayoutWuri.setVisibility(VISIBLE);
                }
                String[] date = null;//getDataModel().getMinWuriDateString();
                if (date != null && date.length > 0) {
                    for (int i = 0; i < date.length && i < mDateStringView.length; i++) {
                        mDateStringView[i].setText(date[i]);
                    }
                }
            }
        }
    }

    public void updateTime() {
//        mBeginTimeView.setText("00:00");
//        mMidTimeView1.setText("04:00");
//        mMidTimeView2.setText("08:00");
//        mMidTimeView3.setText("12:00");
//        mMidTimeView4.setText("16:00");
//        mMidTimeView5.setText("20:00");
//        mEndTimeView.setText("24:00");
        if (getDataModel() != null) {
            int len = getDataModel().size();
            if (mDisplayStart >= 0 && mDisplayStart < len) {
                double[][] details = getTreadPriceView().getDetailByIndex(mDisplayStart);
                if (details == null) {
                    return;
                }
                if (details != null && details.length > 0) {
                    if (isTraderEnv) {
                        int i = mDisplayStart;
                        if (mDiffBottomTimeLineViewWaihuiTianyan != null) {
                            mDiffBottomTimeLineViewWaihuiTianyan.postInvalidate();
                        }
//                        mBeginTimeView.setText(getTreadPriceView().getDetailTimeByIndex(i++));
//                        mMidTimeView1.setText(getTreadPriceView().getDetailTimeByIndex(i++));
//                        mMidTimeView2.setText(getTreadPriceView().getDetailTimeByIndex(i++));
//                        mMidTimeView3.setText(getTreadPriceView().getDetailTimeByIndex(i++));
//                        mMidTimeView4.setText(getTreadPriceView().getDetailTimeByIndex(i++));
//                        mMidTimeView5.setText(getTreadPriceView().getDetailTimeByIndex(i++));
//                        if (mMidTimeView6 != null) {
//                            mMidTimeView6.setText(getTreadPriceView().getDetailTimeByIndex(i++));
//                        }
//                        mEndTimeView.setText(getTreadPriceView().getDetailTimeByIndex(i++));
//                        if (isTraderEnvKline) {
//                            mBeginTimeView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
//                            mEndTimeView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
//                            LinearLayout.LayoutParams llp = (LinearLayout.LayoutParams) mBeginTimeView.getLayoutParams();
//                            llp.weight = 2;
//                            mBeginTimeView.requestLayout();
//                            mBeginTimeView.postInvalidate();
//
//                            llp = (LinearLayout.LayoutParams) mEndTimeView.getLayoutParams();
//                            llp.weight = 2;
//                            mEndTimeView.requestLayout();
//                            mEndTimeView.postInvalidate();
//                        } else {
//                            mBeginTimeView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
//                            mEndTimeView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
//                            LinearLayout.LayoutParams llp = (LinearLayout.LayoutParams) mBeginTimeView.getLayoutParams();
//                            llp.weight = 1;
//                            mBeginTimeView.requestLayout();
//                            mBeginTimeView.postInvalidate();
//
//                            llp = (LinearLayout.LayoutParams) mEndTimeView.getLayoutParams();
//                            llp.weight = 1;
//                            mEndTimeView.requestLayout();
//                            mEndTimeView.postInvalidate();
//                        }
                    } else if (isBusinessEffect) {
                        int i = mDisplayStart;
                        if (mDiffBottomTimeLineViewWaihuiTianyan != null) {
                            mDiffBottomTimeLineViewWaihuiTianyan.postInvalidate();
                        }
//                        mBeginTimeView.setText(getTreadPriceView().getDetailTimeByIndex(i++));
//                        mMidTimeView1.setText(getTreadPriceView().getDetailTimeByIndex(i++));
//                        mMidTimeView2.setText(getTreadPriceView().getDetailTimeByIndex(i++));
//                        mMidTimeView3.setText(getTreadPriceView().getDetailTimeByIndex(i++));
//                        mMidTimeView4.setText(getTreadPriceView().getDetailTimeByIndex(i++));
//                        mMidTimeView5.setText(getTreadPriceView().getDetailTimeByIndex(i++));
//                        if (mMidTimeView6 != null) {
//                            mMidTimeView6.setText(getTreadPriceView().getDetailTimeByIndex(i++));
//                        }
//                        mEndTimeView.setText(getTreadPriceView().getDetailTimeByIndex(i++));
//                        if (isTraderEnvKline) {
//                            mBeginTimeView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
//                            mEndTimeView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
//                            LinearLayout.LayoutParams llp = (LinearLayout.LayoutParams) mBeginTimeView.getLayoutParams();
//                            llp.weight = 2;
//                            mBeginTimeView.requestLayout();
//                            mBeginTimeView.postInvalidate();
//
//                            llp = (LinearLayout.LayoutParams) mEndTimeView.getLayoutParams();
//                            llp.weight = 2;
//                            mEndTimeView.requestLayout();
//                            mEndTimeView.postInvalidate();
//                        } else {
//                            mBeginTimeView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
//                            mEndTimeView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
//                            LinearLayout.LayoutParams llp = (LinearLayout.LayoutParams) mBeginTimeView.getLayoutParams();
//                            llp.weight = 1;
//                            mBeginTimeView.requestLayout();
//                            mBeginTimeView.postInvalidate();
//
//                            llp = (LinearLayout.LayoutParams) mEndTimeView.getLayoutParams();
//                            llp.weight = 1;
//                            mEndTimeView.requestLayout();
//                            mEndTimeView.postInvalidate();
//                        }
                    } else {
                        String mBegin = "--";
                        if (mDisplayStart >= 0 && mDisplayStart < len) {
                            mBegin = getTreadPriceView().getDetailTimeByIndex(mDisplayStart);
                        }
                        String mEnd = "--";
                        if (mDisplayStart + mDisplayCount - 1 >= 0 && mDisplayStart + mDisplayCount - 1 < len) {
                            mEnd = getTreadPriceView().getDetailTimeByIndex(mDisplayStart + mDisplayCount - 1);
                        } else if (len > 0) {
                            mEnd = getTreadPriceView().getDetailTimeByIndex(len - 1);
                        }
                        int time = (int) details[0][0];
                        int minute = time % 100;
//                        int diff = mDisplayCount * 5 / 6;
                        mBeginTimeView.setText(mBegin);
                        minute = time % 100;
                        time = time / 100 * 100;
                        time += (minute + 55) / 60 * 100;
                        time += (minute + 55) % 60;
                        mMidTimeView1.setText(FunctionHelper.getHHmm(time));
                        minute = time % 100;
                        time = time / 100 * 100;
                        time += (minute + 60) / 60 * 100;
                        time += (minute + 60) % 60;
                        mMidTimeView2.setText(FunctionHelper.getHHmm(time));
                        minute = time % 100;
                        time = time / 100 * 100;
                        time += (minute + 60) / 60 * 100;
                        time += (minute + 60) % 60;
                        mMidTimeView3.setText(FunctionHelper.getHHmm(time));
                        minute = time % 100;
                        time = time / 100 * 100;
                        time += (minute + 60) / 60 * 100;
                        time += (minute + 60) % 60;
                        mMidTimeView4.setText(FunctionHelper.getHHmm(time));
                        minute = time % 100;
                        time = time / 100 * 100;
                        time += (minute + 60) / 60 * 100;
                        time += (minute + 60) % 60;
                        mEndTimeView.setText(mEnd);
                    }
                }
            }
        }
        if (getDataModel() != null && false) {
            int len = getDataModel().size();
            if (mDisplayStart >= 0 && mDisplayStart < len) {
                double[][] details = getTreadPriceView().getDetailByIndex(mDisplayStart);
                if (details == null) {
                    return;
                }
                if (details != null && details.length > 0) {
                    mBeginTimeView.setText(FunctionHelper.getDay((int) details[0][0]));
                }
            }
            if (mDisplayStart + mDisplayCount - 1 >= 0 && mDisplayStart + mDisplayCount - 1 < len) {
                double[][] details = getTreadPriceView().getDetailByIndex(mDisplayStart + mDisplayCount - 1);
                if (details == null) {
                    return;
                }
                if (details != null && details.length > 0) {
                    mEndTimeView.setText(FunctionHelper.getDay((int) details[0][0]));
                }
            } else if (len > 0) {
                double[][] details = getTreadPriceView().getDetailByIndex(len - 1);
                if (details == null) {
                    return;
                }
                if (details != null && details.length > 0) {
                    mEndTimeView.setText(FunctionHelper.getDay((int) details[0][0]));
                }
            }
        }
    }

    public int getUpColor() {
        return mUpColor;
    }

    public int getDownColor() {
        return mDownColor;
    }

    public int getEqualColor() {
        return mEqualColor;
    }

    public Typeface getTypeFace() {
        return fontFace;
    }

    public void changeLookFace() {
        if (isNight()) {
            mChangeLandView.setBackgroundColor(0x8f666666);
            mChangeLandView.setTextColor(0xff11141A);
            mTextColor = 0xffffffff;
            mUpColor = 0xffef5a3c;
            mDownColor = 0xff39a44d;
            mEqualColor = 0xffffffff;
        } else {
            mChangeLandView.setBackgroundColor(0x8fc9c9c9);
            mChangeLandView.setTextColor(0xff90b7e8);
            mTextColor = 0xff999999;
            mUpColor = 0xffef5a3c;
            mDownColor = 0xff39a44d;
            mEqualColor = 0xff858c93;
        }
        mBeginTimeView.setTextColor(mTextColor);
        mEndTimeView.setTextColor(mTextColor);
        mMidTimeView1.setTextColor(mTextColor);
        mMidTimeView2.setTextColor(mTextColor);
        mMidTimeView3.setTextColor(mTextColor);
        mMidTimeView4.setTextColor(mTextColor);
        if (mMidTimeView5 != null) {
            mMidTimeView5.setTextColor(mTextColor);
        }
        if (mMidTimeView6 != null) {
            mMidTimeView6.setTextColor(mTextColor);
        }
        for (int i = 0; i < 5; i++) {
            mDateStringView[i].setTextColor(mTextColor);
        }
        mDiffTreadPrice.initColor();
        if (mKChartMoveLineDetailTimeView != null) {
            mKChartMoveLineDetailTimeView.changeLookFace();
        }
    }

    public boolean isNight() {
        return isNight;
    }

    public void enlargeKLineWidth() {
        if (getDataModel() == null) {
            return;
        }
        if (mDisplayCount > 0) {
//            mDisplayCount--;
        }
        getTreadPriceView().resetDataModel();
        initViewData();
    }

    public void reduceKLineWidth() {
        if (getDataModel() == null) {
            return;
        }
        if (mDisplayCount < getDataModel().size()) {
//            mDisplayCount++;
        }
        getTreadPriceView().resetDataModel();
        initViewData();
    }

    /**
     * 往老分时移动offsetChange为正数，往新分时移动offsetChange为负数
     *
     * @param offsetChange
     */
    public void changeStockOffset(int offsetChange) {
        Log.e("tag", "changeStockOffset1: "+offsetChange );
        boolean isScrollEnd = true;
        if (getDataModel() != null) {
            if (getDataModel() != null) {
                //Logx.d(CLASS + "min chart changeStockOffset offsetChange=" + offsetChange + " mDisplayOffset=" + mDisplayOffset);
                if (offsetChange > 0) {
                    //往老分时移动offsetChange为正数
                    int len = getDataModel().size();
                    int maxOffset = len >= mDisplayCount ? (len - mDisplayCount) : 0;
                    if ((mDisplayOffset + offsetChange) >= 0 && (mDisplayOffset + offsetChange) <= maxOffset) {
                        mDisplayOffset = mDisplayOffset + offsetChange;
                        isScrollEnd = false;
                    } else {
                        mDisplayOffset = maxOffset;
                        isScrollEnd = true;
                    }
                } else if (offsetChange < 0) {
                    //往新分时移动offsetChange为负数
                    if ((mDisplayOffset + offsetChange) >= 0) {
                        mDisplayOffset = mDisplayOffset + offsetChange;
                        isScrollEnd = false;
                    } else {
                        mDisplayOffset = 0;
                        isScrollEnd = true;
                    }
                }
                getTreadPriceView().resetDataModel();
                initViewData();
            }
        }
        //return isScrollEnd;
    }

    public boolean changeStockOffsetCheck(MotionEvent ev) {
        return mDiffWaihuiTianyanContentLayout.onInterceptTouchEvent(ev);
    }

    public boolean changeStockOffsetCheck(int offsetChange) {
        boolean isScrollEnd = true;
        if (getDataModel() != null) {
            if (getDataModel() != null) {
                if (offsetChange > 0) {
                    //往老分时移动offsetChange为正数
                    int len = getDataModel().size();
                    int maxOffset = len >= mDisplayCount ? (len - mDisplayCount) : 0;
                    if ((mDisplayOffset + offsetChange) >= 0 && (mDisplayOffset + offsetChange) <= maxOffset) {
                        isScrollEnd = false;
                    } else {
                        isScrollEnd = true;
                    }
                } else if (offsetChange < 0) {
                    //往新分时移动offsetChange为负数
                    if ((mDisplayOffset + offsetChange) >= 0) {
                        isScrollEnd = false;
                    } else {
                        isScrollEnd = true;
                    }
                }
            }
        }
        //Logx.d(CLASS + "changeStockOffsetCheck offsetChange=" + offsetChange + " mDisplayOffset=" + mDisplayOffset + " isScrollEnd=" + isScrollEnd);
        return isScrollEnd;
    }

    public int getmRealLen() {
        return 1;
    }

    public void setData(List<DiffDataBean.ContentBean.DBean> list) {
        mData = list;
        mDataBean.getMinData();
        getTreadPriceView().resetDataModel();
        initViewData();
    }

    public void setData2(List<DiffDataBean.ContentBean.DBean> list, List<DiffDataBean.ContentBean.DBean> list2) {
        mData = list;
        mData2 = list2;
        mDataBean.getMinData();
        mDataBean.getMinData2();
        getTreadPriceView().resetDataModel();
        initViewData();
    }

    public void setData3(List<DiffDataBean.ContentBean.DBean> list, List<DiffDataBean.ContentBean.DBean> list2, List<DiffDataBean.ContentBean.DBean> list3) {
        mData = list;
        mData2 = list2;
        mData3 = list3;
        mDataBean.getMinData();
        mDataBean.getMinData2();
        mDataBean.getMinData3();
        getTreadPriceView().resetDataModel();
        initViewData();
    }

    public class DataBean {
        double[][] allData = null;
        double[][] allData2 = null;
        double[][] allData3 = null;

        public int size() {
            return mData.size();
        }

        public int size2() {
            return mData2.size();
        }

        public int size3() {
            return mData3.size();
        }

        public int getmRealLen() {
            return 1;
        }

        public double getmPreClosePrice() {
            return 0d;
        }

        public double[][] getMinData() {
            if (allData == null || allData.length != mData.size()) {
                allData = new double[mData.size()][3];
            }
            int len = mData.size();
            for (int i = 0; i < len; i++) {
                allData[i][0] = mData.get(i).getTime();
                allData[i][1] = mData.get(i).getValue();
            }
            return allData;
        }

        public double[][] getMinData2() {
            if (allData2 == null || allData2.length != mData2.size()) {
                allData2 = new double[mData2.size()][3];
            }
            int len = mData2.size();
            for (int i = 0; i < len; i++) {
                allData2[i][0] = mData2.get(i).getTime();
                allData2[i][1] = mData2.get(i).getValue();
            }
            return allData2;
        }

        public double[][] getMinData3() {
            if (allData3 == null || allData3.length != mData3.size()) {
                allData3 = new double[mData3.size()][3];
            }
            int len = mData3.size();
            for (int i = 0; i < len; i++) {
                allData3[i][0] = mData3.get(i).getTime();
                allData3[i][1] = mData3.get(i).getValue();
            }
            return allData3;
        }
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }
}

