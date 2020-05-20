package com.wifieye.akechartdemo.lineme;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.wifieye.akechartdemo.line.DiffContainerWaihuiTianyan;
import com.wifieye.akechartdemo.line.DiffDataBean;
import com.wifieye.akechartdemo.line.FunctionHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.wifieye.akechartdemo.line.Dip.dip22;

public class MultContentLayout extends FrameLayout {

    public int mDisplayCount=60;//60; //默认展示个数
    public int mDisplayOffset = 0;//分时显示3小时=180根
    public int mDisplayStart = 0;//分时显示3小时=180根
    public int mRightNumber = FunctionHelper.dp2pxInt(15);
    public int mLeftNumber = FunctionHelper.dp2pxInt(25);
    public int mBottomNumber=FunctionHelper.dp2pxInt(20);

    public int lastTouchX;

    private MoveLineDetailView moveLineView;
    private LinePriceView priceView;
    private LayoutParams flp;
    private LineParentLayout mLayoutBelow;

    public List<DiffDataBean.ContentBean.DBean> mData = new ArrayList<>();
    private DataBean mDataBean = new DataBean();

    public int charType=0;//0 折线 1 柱状

    private Context mContext;
    private Typeface typeface;

    private int mScreenIndex = -1;



    public MultContentLayout(@NonNull Context context) {
        super(context);
        init(context);
    }

    public MultContentLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public void init(Context context){

        mContext=context;

        typeface=Typeface.createFromAsset(mContext.getAssets(),"fonts/DIN-Medium.otf") ;

        setLayout();

    }

    /**
     * 往老分时移动offsetChange为正数，往新分时移动offsetChange为负数
     *
     * @param offsetChange
     */
    public void changeStockOffset(int offsetChange) {
        Log.e("tag", "changeStockOffset: "+offsetChange );
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
                getPriceView().resetData();
            }
        }
        //return isScrollEnd;
    }

    public Typeface getTypeFace(){
        return  typeface;
    }

    private void setLayout() {

        moveLineView=new MoveLineDetailView(mContext);
        flp=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,dip22);
        addView(moveLineView,flp);
        moveLineView.setHolder(this);
        moveLineView.setVisibility(INVISIBLE);

        mLayoutBelow=new LineParentLayout(mContext);
        mLayoutBelow.setOrientation(LinearLayout.VERTICAL);
        mLayoutBelow.setHolder(this);
        flp=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        flp.topMargin=dip22;
        addView(mLayoutBelow,flp);
        setLayoutBelow(mLayoutBelow);

    }

    private void setLayoutBelow(LineParentLayout mLayoutBelow) {



        priceView=new LinePriceView(mContext);
        priceView.setHolder(this);
//        priceView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        mLayoutBelow.addView(priceView,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        priceView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.e(TAG, "onLongClick: " );
                return true;
            }
        });

    }

    private static final String TAG = "MultContentLayout";

    public int getScreenIndex(){
        return mScreenIndex;
    }

    public void setScreenIndex(int index){

        int len = getDataModel().size();
        if (index==-1||index>len){
            index=len-1;
        }

        if (index<mDisplayStart){
            index = mDisplayStart;
        }

        mScreenIndex=index;

    }

    public LinePriceView getPriceView() {
        return priceView;
    }

    public void setData(List<DiffDataBean.ContentBean.DBean> list) {
        mData = list;
        mDataBean.getMinData();
        getPriceView().resetData();
//        initViewData();
    }


    public DataBean getDataModel(){
        return mDataBean;
    }
    public void setMoveViewVisibility(int visible) {
    }

    public class DataBean{

        double[] [] allData=null;

        public int size(){return mData.size();};

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

        public int getmRealLen() {
            return 1;
        }

    }

    private DisplayModel mDisplayModel = DisplayModel.NORMAL;
    public enum DisplayModel {
        NORMAL, CURSOR
    }

    public DisplayModel getDisplayModel(){
        return mDisplayModel;
    }

}
