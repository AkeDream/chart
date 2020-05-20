package com.wifieye.akechartdemo.lineme;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;

import com.wifieye.akechartdemo.line.FunctionHelper;
import com.wifieye.akechartdemo.line.StockChartUtility;

public class LinePriceView extends PriceBaseView{


    private double [] mCurrentPrice;
    private double maxPrice;
    private double minPrice;

    private int leftPadding;
    private int mWidth;
    private int mHeight;

    private int jbColor = 0x252a92eb;//0x662a92eb;

    private Paint mShadowPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path mShadowPath=new Path();
    private Paint linePaint;

    private Paint yScalePaint;
    private Paint xScalePaint;

    private LinearGradient mLinearGradientPortrait;

    private Path mPricePath;

    public LinePriceView(Context context) {
        super(context);
    }

    public LinePriceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private static final String TAG = "LinePriceView";
    @Override
    protected void init() {

        if (paint != null) {
            Log.e(TAG, "init: paint not");
        }else {
            Log.e(TAG, "init: paint null" );
        }

        if (mPaint != null) {
            Log.e(TAG, "init: mPaint not");
        }else {
            Log.e(TAG, "init: mPaint null " );
        }

        setClickable(true);
        mPricePath=new Path();

        yScalePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        yScalePaint.setStyle(Paint.Style.FILL);
        yScalePaint.setStrokeWidth(1);
        yScalePaint.setTextAlign(Paint.Align.LEFT);
        yScalePaint.setTextSize(FunctionHelper.sp2px(9));
        yScalePaint.setColor(Color.RED);


        linePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStrokeWidth(0.5f);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(Color.parseColor("#999999"));

        xScalePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        xScalePaint.setStyle(Paint.Style.FILL);
        xScalePaint.setStrokeWidth(1);
        xScalePaint.setTextAlign(Paint.Align.LEFT);
        xScalePaint.setTextSize(FunctionHelper.sp2px(9));
        xScalePaint.setColor(Color.RED);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


        paint.setColor(Color.parseColor("#3e9ae8"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);

        leftPadding =getPaddingLeft()+mHolder.mLeftNumber;
        mWidth=getWidth()- leftPadding -mHolder.mRightNumber-getPaddingRight();
        mHeight=getHeight()-1-mHolder.mBottomNumber;
        mLinearGradientPortrait = new LinearGradient(leftPadding, mHeight - 1, leftPadding, 1, jbColor & 0x00ffffff,
                jbColor, Shader.TileMode.MIRROR);
        mShadowPaint.setShader(mLinearGradientPortrait);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mHolder==null||mHolder.getDataModel()==null||mHolder.getDataModel().size()<=0){
            return;
        }

        canvas.save();

        mPricePath.reset();
        mShadowPath.reset();

        int len=mHolder.getDataModel().size();
        int total=mHolder.getDataModel().size();
        int start=len>mHolder.mDisplayCount?len-mHolder.mDisplayCount:0;

        if (mHolder.mDisplayOffset > 0) {
            if (start - mHolder.mDisplayOffset >= 0) {
                start = start - mHolder.mDisplayOffset;
            }
        }

        if (len>mHolder.mDisplayCount){
            total=mHolder.mDisplayCount;
        }
        mHolder.mDisplayStart=start;

        mShadowPath.moveTo(leftPadding,mHeight-1);
        for (int i=start;i<len&&(i-start)<total;i++){

            int xTem= (int) (leftPadding +1f*(i-start)/(total-1)*mWidth);
            int yTem= getYTemValue(mCurrentPrice[i]-minPrice,(maxPrice-minPrice));

            if (i==start){

                mPricePath.moveTo(xTem,yTem);
                mShadowPath.lineTo(xTem,yTem);

            }else {

                mPricePath.lineTo(xTem,yTem);
                mShadowPath.lineTo(xTem,yTem);
            }

            if (i == len - 1 || ((i - start) == total - 1)) {
                mShadowPath.lineTo(xTem,  mHeight- 1);
            }

        }

        mShadowPath.close();

        canvas.drawPath(mShadowPath,mShadowPaint);
        canvas.drawPath(mPricePath,paint);
        drawYScale(canvas);
        drawXScale(canvas);
        canvas.restore();



    }

    private void drawXScale(Canvas canvas) {

        String [] mouth={"1月","2月","3月","4月","5月","6月"};
        int count =6;
        for (int i = 0; i < count; i++) {



            int xScale=(mWidth/(count-1))*i+leftPadding;

            canvas.save();
            canvas.drawText(mouth[i],xScale-10,mHeight+xScalePaint.getTextSize()+5,xScalePaint);
//            canvas.translate(-);
            canvas.rotate(-46);
            canvas.restore();

            canvas.drawLine(xScale,2,xScale,mHeight,linePaint);

        }

    }

    public void drawYScale(Canvas canvas){


        int count =6;
        for (int i = 0; i < count; i++) {

            double value=minPrice+(maxPrice-minPrice)*i/(count-1);
            if (value < 0) {
                continue;
            }
            String valStr=(value >= -0.05 && value <= 0.05) ? "0.0" : StockChartUtility.formatPrice(value, mHolder.getDataModel().getmRealLen());
            int yTmp = (int) ((mHeight-yScalePaint.getTextSize()-5)*(count-1-i)/(count-1)+yScalePaint.getTextSize());
            canvas.drawText(valStr,2,yTmp,yScalePaint);

            int yScale=(int) (mHeight/(count-1)*i+2);
            canvas.drawLine(leftPadding,yScale,getWidth()-getPaddingRight()-mHolder.mRightNumber,yScale,linePaint);

        }

    }

    public int getYTemValue(double value, double gap){

        int yTem;
        int height=mHeight-2;
        if (value<0)value=0;
        yTem= (int) (1f*value/gap*height);
        yTem= Math.max(height - yTem, 0);

        return yTem;

    }

    public void resetData(){

        if (mHolder != null) {

            if (mHolder.getDataModel() == null) {
                return;
            }

            double[][] data = mHolder.getDataModel().getMinData();
            if (data != null) {

                int size = mHolder.getDataModel().size();
                mCurrentPrice=new double[size];
                for (int i = 0; i < size; i++) {
                    mCurrentPrice[i]=data[i][1];
                }

                resetMaxAndMinPrice();

            }


            invalidate();

        }

    }

    private void resetMaxAndMinPrice() {

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

        double maxValue=Integer.MIN_VALUE;
        double minValue=Integer.MAX_VALUE;

        for (int i=start;i<len&&((i-start)<total);i++){

            if (data[i][1]>=maxValue){
                maxValue=data[i][1];
            }

            if (data[i][1]<=minValue){
                minValue=data[i][1];
            }

        }

        maxPrice=maxValue;
        minPrice=minValue;

        if (maxPrice == minPrice) {
            minPrice = 0d;
        }


    }

}
