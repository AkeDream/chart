package com.wifieye.akechartdemo.lineme;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.wifieye.akechartdemo.R;
import com.wifieye.akechartdemo.line.FunctionHelper;
import com.wifieye.akechartdemo.line.StockChartUtility;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

public class CloudBarView extends View  {

    private Context mContext;
    private Paint mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);//主线

    private Paint linePaint;
    private Paint yScalePaint;
    private Paint xScalePaint;
    private Paint whitePaint;
    private Paint strokePaint;
    private Paint shadowPaint;

    private Paint bgPaint;

    private Path mPath;
    private Path mShadowPath;

    private String deepBlueColor="#469ee9";
    private String lineColor="#EFEFEF";
    private String textColor="#999999";
    private int jbColor = 0x252a92eb;//0x662a92eb;
    private String effectColor="#6B9EBB";
    private String moveCurrentColor="#F14624";
    private String moveLastColor="#08A094";
    private String moveLastCircleColor="#9008A094";
    private String moveCurrentCircleColor="#90F14624";

    private double maxYValues;
    private double minYValues;

    private PointValues pointValues;

    private String [] xValues;
    private double [] yValues;
    public float[] mEntries = new float[]{};

    private int paddingLeft;

    private int paddingRight;

    public int paddingBottom;

    private int paddingTop;

    private float mDistanceX=0.0f;//总共移动的距离

    private float mTouchX;
    private float mTotalScrollX;//移动距离改变

    private float mMoveScrollX=0;//十字光标当前x
    private float mMoveDistanceX=0.0f;//十字光标移动距离
    private boolean isMove=false;//是否显示十字光标
    private float moveBodyHeight;//光标指示文字高度

    //十字光标
    private PathEffect moveEffect;//虚线
    private Paint effectPaint;
    private Paint movePaint;
    private Paint moveTextPaint;
    private Path  movePath;

    private Rect mContentRect;
    private Paint mCanvasBGPaint;

    private int whiteRadius;
    private int strokeRadius;
    private int bodyWidth=FunctionHelper.dp2pxInt(3f);
    private int dp3=FunctionHelper.dp2pxInt(3);
    private int dp5=FunctionHelper.dp2pxInt(5);

    private int dp2=FunctionHelper.dp2pxInt(2);


    private int mHeight;
    private int mWidth;
    private int contentHeight;
    private int contentWidth;

    private boolean isCanScroll=true;
    private int allVisibleCount=7;
    private boolean isDrawOver=true;



    private int type=0;// 1 净值走势
    private Rect xScaleRect;
    private float moveY;
    private float moveX;
    private int moveValueIndex;

    //柱状
    private  int labelCount=6;
    private boolean isForceLabel=true;
    //y轴上的间隔距离
    private double mYInterval;
    //x轴上的间隔距离
    private double mXInterval;

    private Paint barPaint=new Paint(Paint.ANTI_ALIAS_FLAG);

    public CloudBarView(Context context) {
        super(context);
        init(context);
    }

    public CloudBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CloudBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context){

        mContext=context;

        linePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.parseColor(lineColor));
        linePaint.setStrokeWidth(FunctionHelper.dp2px(0.5f));
        linePaint.setStyle(Paint.Style.STROKE);

        strokePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setColor(Color.parseColor(deepBlueColor));
        strokePaint.setStrokeWidth(FunctionHelper.dp2pxInt(1));
        strokePaint.setStyle(Paint.Style.STROKE);

        mPath=new Path();
        mShadowPath=new Path();

        shadowPaint=new Paint(Paint.ANTI_ALIAS_FLAG);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.parseColor(deepBlueColor));

        barPaint.setStyle(Paint.Style.FILL);
        barPaint.setColor(Color.parseColor(deepBlueColor));

        yScalePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        yScalePaint.setStyle(Paint.Style.FILL);
        yScalePaint.setStrokeWidth(1);
        yScalePaint.setTextAlign(Paint.Align.RIGHT);
        yScalePaint.setTextSize(FunctionHelper.sp2px(9));
        yScalePaint.setColor(Color.parseColor(textColor));


        xScalePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        xScalePaint.setStyle(Paint.Style.FILL);
        xScalePaint.setStrokeWidth(1);
        xScalePaint.setTextAlign(Paint.Align.CENTER);
        xScalePaint.setTextSize(FunctionHelper.sp2px(9));
        xScalePaint.setColor(Color.parseColor(textColor));


        paddingLeft=FunctionHelper.dp2pxInt(60);
        paddingRight=FunctionHelper.dp2pxInt(60);
        paddingBottom= FunctionHelper.dp2pxInt(60);
        paddingTop=FunctionHelper.dp2pxInt(20);
        whiteRadius=FunctionHelper.dp2pxInt(2);

        whitePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        whitePaint.setStyle(Paint.Style.FILL);
        whitePaint.setColor(Color.parseColor("#ffffff"));
        strokeRadius=FunctionHelper.dp2pxInt(2.5f);

        bgPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(Color.parseColor("#ffffff"));

//        bodyWidth=FunctionHelper.dp2pxInt(3f);

        mCanvasBGPaint = new Paint();
        mCanvasBGPaint.setColor(Color.parseColor("#00000000"));

        moveEffect=new DashPathEffect(new float[]{20f, 5f, 10f},0);
        effectPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        effectPaint.setPathEffect(moveEffect);
        effectPaint.setColor(Color.parseColor(effectColor));
        effectPaint.setStrokeWidth(FunctionHelper.dp2px(0.5f));
        effectPaint.setStyle(Paint.Style.STROKE);

        movePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        movePaint.setColor(Color.parseColor(moveCurrentColor));
        movePaint.setStyle(Paint.Style.FILL);
        movePath=new Path();

        moveTextPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        moveTextPaint.setStyle(Paint.Style.FILL);
        moveTextPaint.setColor(Color.parseColor("#ffffff"));
        moveTextPaint.setTextSize(FunctionHelper.sp2px(12));
        moveTextPaint.setTextAlign(Paint.Align.CENTER);
        moveBodyHeight=FunctionHelper.dp2px(18);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mHeight=getHeight();
        mWidth=getWidth();

        contentHeight=mHeight-paddingTop-paddingBottom;
        contentWidth=mWidth-paddingLeft-paddingRight;

        LinearGradient  mLinearGradientPortrait =
                new LinearGradient(0, paddingTop+contentHeight, 0, paddingTop,
                        jbColor & 0x00ffffff, jbColor, Shader.TileMode.MIRROR);
        shadowPaint.setShader(mLinearGradientPortrait);

        mContentRect=new Rect(paddingLeft-bodyWidth,paddingTop-bodyWidth,mWidth-paddingRight+bodyWidth,mHeight-paddingBottom+bodyWidth);
        xScaleRect = new Rect(paddingLeft-FunctionHelper.dp2pxInt(10),mHeight-paddingBottom,mWidth-paddingRight+FunctionHelper.dp2pxInt(10),mHeight);

        setAnimal();
    }


    public void setType(int type){
        this.type=type;
        bodyWidth=FunctionHelper.dp2pxInt(3f);
    }

    public void setPointValues(PointValues pointValues) {

        this.pointValues = pointValues;
        xValues=pointValues.getxValues();
        yValues=pointValues.getyValues();
        moveY = (float) yValues[0];
        setMaxAndMin();

        invalidate();
    }

    public void setMaxAndMin(){

        double[] doublesY = pointValues.getyValues();
        if (doublesY ==null|| doublesY.length==0) {
            return;
        }

        double maxCurrent=Integer.MIN_VALUE;
        double minCurrent=Integer.MAX_VALUE;

        for (int i = 0; i < doublesY.length; i++) {

            if (maxCurrent<doublesY[i]){
                maxCurrent=doublesY[i];
            }

            if (minCurrent>doublesY[i]){
                minCurrent=doublesY[i];
            }

        }

        if (maxCurrent<0){
            maxCurrent=0;
        }

        if (minCurrent>0){
            minCurrent=0;
        }

        maxYValues=maxCurrent;
        minYValues=minCurrent;


        if (doublesY.length==1&&doublesY[0]>0){
            if (doublesY[0]>0){
                maxYValues=doublesY[0];
                minYValues=0;
            }else {
                maxYValues=0;
                minYValues=doublesY[0];
            }

        }

        computeAxisValues((float) minYValues,(float) maxYValues);

    }

    /**
     * Sets up the axis values. Computes the desired number of labels between the two given extremes.
     *
     * @return
     */
    protected void computeAxisValues(float min, float max) {

        float yMin = min;
        float yMax = max;

        double range = Math.abs(yMax - yMin);

        if (labelCount == 0 || range <= 0 || Double.isInfinite(range)) {
            return;
        }

        // Find out how much spacing (in y value space) between axis values
        double rawInterval = range / labelCount;
        double interval = NumberUtil.roundToNextSignificant(rawInterval);

        double intervalMagnitude = NumberUtil.roundToNextSignificant(Math.pow(10, (int) Math.log10(interval)));
        int intervalSigDigit = (int) (interval / intervalMagnitude);
        if (intervalSigDigit > 5) {
            // Use one order of magnitude higher, to avoid intervals like 0.9 or
            // 90
            interval = Math.floor(10 * intervalMagnitude);
        }

        int n = 1;

        double first = interval == 0.0 ? 0.0 : Math.ceil(yMin / interval) * interval;

        double last = interval == 0.0 ? 0.0 : NumberUtil.nextUp(Math.floor(yMax / interval) * interval);

        double f;
        int i;

        if (isForceLabel){
            n=labelCount;
        }else {


            if (interval != 0.0) {
                for (f = first; f <= last; f += interval) {
                    ++n;
                }
            }
        }

        if (mEntries.length < n) {
            // Ensure stops contains at least numStops elements.
           mEntries = new float[n];
        }

        for (f = first, i = 0; i < n; f += interval, ++i) {

            if (f == 0.0) // Fix for negative zero case (Where value == -0.0, and 0.0 == -0.0)
                f = 0.0;

            mEntries[i] = (float) f;
        }

        for (int j = 0; j < mEntries.length; j++) {

            if (maxYValues<mEntries[j]){
                maxYValues=mEntries[j];
            }

            if (minYValues>mEntries[j]){
                minYValues=mEntries[j];
            }

        }

        Log.e(TAG, "computeAxisValues: "+ Arrays.toString(mEntries));

    }


    public void setCanScroll(boolean isCanScroll){
        this.isCanScroll=isCanScroll;
    }

    private boolean isScrollVisible(int total){
        return isCanScroll && allVisibleCount < total;
    }

    private static final String TAG = "CloudBarView";
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (xValues==null||xValues.length==0) {
            return;
        }

        if (yValues==null||yValues.length==0){
            return;
        }


        drawLeftAxis(canvas);

        drawBarContent(canvas);

//        int total=yValues.length;
//        int visibleCount=total;
//        if (isScrollVisible(total)){
//            visibleCount=allVisibleCount;
//        }
//
//        //保证光标点不超过边界
//
//        if ( mMoveScrollX < paddingLeft) {
//            mMoveScrollX=paddingLeft;
//        }
//        if (mMoveScrollX> paddingLeft+contentWidth){
//            mMoveScrollX=paddingLeft+contentWidth;
//        }
//
//        moveX=mMoveScrollX;
//        float xItem;
//        if (visibleCount==1) {
//            xItem=contentWidth;
//        }else {
//            xItem = (float) (contentWidth / (visibleCount - 1f));
//        }
//        float maxLeftDistance = (total - allVisibleCount) * xItem;
//
//        if (isScrollVisible(total)){
//
//            mDistanceX=mDistanceX+mTotalScrollX;
//            if (mDistanceX>maxLeftDistance){
//                mDistanceX=maxLeftDistance;
//            }
//
//            if (mDistanceX<0){
//                mDistanceX=0;
//            }
//
//        }else {
//            maxLeftDistance=0;
//            mDistanceX=0;
//        }
//
//        setLine(canvas);
//
//        int saveCount0 = canvas.save();
//
//        canvas.clipRect(xScaleRect);
//        for (int i = 0; i < total; i++) {
//
//            float xTem= (float) (i*xItem+paddingLeft+mDistanceX-maxLeftDistance);
//            canvas.drawText(xValues[i],xTem,mHeight-paddingBottom+xScalePaint.getTextSize()+FunctionHelper.dp2pxInt(5),xScalePaint);
//            if (i>0){
//                canvas.drawLine(i*xItem+paddingLeft,paddingTop,i*xItem,mHeight-paddingBottom,linePaint);
//            }
//
//        }
//
//
//        canvas.restoreToCount(saveCount0);
//        canvas.drawRect(xScaleRect,mCanvasBGPaint);
//
//        mPath.reset();
//        mShadowPath.reset();
//        int saveCount = canvas.save();
//        canvas.clipRect(mContentRect);
//
//
//        mShadowPath.moveTo(paddingLeft,paddingTop+contentHeight);
//        for (int i = 0; i < total; i++) {
//
//            float xTem= (float) (i*xItem+paddingLeft+mDistanceX-maxLeftDistance);
//
//            if (i>0){
//                canvas.drawLine(xTem,paddingTop,xTem,mHeight-paddingBottom,linePaint);
//            }
//
//            float yTem= getYTemValue(yValues[i]-minYValues,(maxYValues-minYValues));
//
//            mShadowPath.lineTo(xTem,yTem);
//            if (i==0){
//                moveX=xTem;
//
//                mPath.moveTo(xTem,yTem);
//            }else {
//                mPath.lineTo(xTem,yTem);
//            }
//
//            if (i==total-1){
//                mShadowPath.lineTo(xTem,paddingTop+contentHeight);
//            }
//
//            if (mMoveScrollX>=xTem){
//                moveY = yTem;
//                moveX=xTem;
//                moveValueIndex=i;
//            }
//
//
//        }
//        mShadowPath.close();
//        canvas.drawPath(mShadowPath,shadowPaint);
//        canvas.drawPath(mPath,mPaint);
//
//        canvas.drawRect(new Rect(paddingLeft-bodyWidth,paddingTop-bodyWidth,
//                paddingLeft,paddingTop+contentHeight+bodyWidth),bgPaint);
//
//        for (int i = 0; i < total; i++) {
//
//            float xTem= (float) (i*xItem+paddingLeft+mDistanceX-maxLeftDistance);
//            float yTem= getYTemValue(yValues[i]-minYValues,(maxYValues-minYValues));
//
//            if (type==1){
//                canvas.drawCircle(xTem,yTem,whiteRadius,whitePaint);
//                canvas.drawCircle(xTem,yTem,strokeRadius,strokePaint);
//            }else {
//
//
//                //绘制最后一个值 指示框
//                if (i==(total-1)){
//                    setDialogValue(canvas, yValues[i], xTem, yTem,moveLastColor,moveLastCircleColor);
//                }
//
//            }
//
//
//        }
//
//
//        if (isMove){
//            Log.e(TAG, "onDraw: moveY"+ moveY);
//            movePath.reset();
//            movePath.moveTo(paddingLeft,moveY);
//            movePath.lineTo(paddingLeft+contentWidth,moveY);
//            canvas.drawPath(movePath,effectPaint);
//
//            movePath.reset();
//            movePath.moveTo(moveX,paddingTop);
//            movePath.lineTo(moveX,paddingTop+contentHeight);
//            canvas.drawPath(movePath,effectPaint);
//
//
//            setDialogValue(canvas, yValues[moveValueIndex], moveX, moveY,moveCurrentColor,moveCurrentCircleColor);
//
//        }
//
//
//        canvas.restoreToCount(saveCount);

    }

    //绘制柱状图
    private void drawBarContent(Canvas canvas) {

        int length = xValues.length;
        mXInterval=1f*contentWidth/length;

        for (int i = 0; i < length; i++) {

            float xTem=(float) (paddingLeft+1.0/4*mXInterval+i*mXInterval);
            float yTem=getYTemValue((yValues[i]*dy)-minYValues,maxYValues-minYValues);
            float yTem0 = getYTemValue(0-minYValues, maxYValues - minYValues);
            RectF rectF;
            Log.e(TAG, "drawBarContent: "+xTem+"yT"+yTem+"yt0"+yTem0 );
            if (yTem>yTem0){
                rectF=new RectF(xTem,yTem0,(float) (xTem+1.0/2*mXInterval),yTem);
                canvas.drawRect(rectF,barPaint);
            }else if (yTem<yTem0){
                rectF=new RectF(xTem,yTem,(float) (xTem+1.0/2*mXInterval),yTem0);
                canvas.drawRect(rectF,barPaint);
            }

        }

    }

    //绘制y轴刻度
    private void drawLeftAxis(Canvas canvas) {

        canvas.drawLine(paddingLeft,paddingTop,paddingLeft,paddingTop+contentHeight,linePaint);
        for (int i = 0; i < mEntries.length; i++) {

            float yTemValue = getYTemValue(mEntries[i]-minYValues, (maxYValues - minYValues));
            canvas.drawText(mEntries[i]+"",paddingLeft-FunctionHelper.dp2pxInt(6),yTemValue-((yScalePaint.descent() + yScalePaint.ascent()) / 2),yScalePaint);
            canvas.drawLine(paddingLeft,yTemValue,mWidth-paddingRight,yTemValue,linePaint);
        }

    }

    //指示框
    private void setDialogValue(Canvas canvas, double yValue, float xTem, float yTem,String color,String circleColor) {

        movePaint.setColor(Color.parseColor(color));
        canvas.drawCircle(xTem,yTem, FunctionHelper.dp2pxInt(2),movePaint);
        movePaint.setColor(Color.parseColor(circleColor));
        canvas.drawCircle(xTem,yTem,FunctionHelper.dp2pxInt(3),movePaint);

        movePath.reset();

        float moveTextWidth = moveTextPaint.measureText(yValue + "")+FunctionHelper.dp2pxInt(10);
        int addHeight=-1;
        int addWidth=-1;

        if ((yTem-dp5-moveBodyHeight-FunctionHelper.dp2pxInt(3))<paddingTop){
            addHeight=1;
        }
        if ((xTem-moveTextWidth)<paddingLeft){
            addWidth=1;
        }

        movePaint.setColor(Color.parseColor(color));

        movePath.moveTo(xTem,yTem+addHeight*dp5);
        movePath.lineTo(xTem,yTem+addHeight*dp5+addHeight*(dp5*2));
        movePath.lineTo(xTem+addWidth*(dp5*2),yTem+addHeight*dp5+addHeight*(dp5*2));
        movePath.close();
        canvas.drawPath(movePath,movePaint);

        canvas.drawRoundRect(new RectF(xTem+addWidth*(moveTextWidth),
                yTem+addHeight*(dp5+dp5+moveBodyHeight),xTem,yTem+addHeight*(dp5+dp5)),dp3,dp3,movePaint);
        canvas.drawText(yValue + "",xTem+addWidth*(moveTextWidth/2),
                yTem+addHeight*(dp5+dp5+moveBodyHeight/2)-((moveTextPaint.descent() + moveTextPaint.ascent()) / 2),moveTextPaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();

        if (isDrawOver){

            switch (action&MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    mTouchX=event.getX();
                    mMoveScrollX=event.getX();
                    getParent().requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_MOVE:
                    float currentDistance = mTouchX - event.getX();
                    if (Math.abs(currentDistance)> ViewConfiguration.get(mContext).getScaledTouchSlop()){

                        if (isMove){
                            mMoveScrollX=event.getX();
                            mMoveDistanceX=currentDistance;
                            ViewCompat.postInvalidateOnAnimation(CloudBarView.this);
                        }

                        getParent().requestDisallowInterceptTouchEvent(true);
                    }else {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }

                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    if (isMove) {
                        isMove = false;
                        ViewCompat.postInvalidateOnAnimation(CloudBarView.this);
                    }
                    getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
            return  gestureDetector.onTouchEvent(event);
        }
        getParent().requestDisallowInterceptTouchEvent(false);
        return super.onTouchEvent(event);
    }

    private GestureDetector gestureDetector=new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            mTotalScrollX=-distanceX;
            ViewCompat.postInvalidateOnAnimation(CloudBarView.this);
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {

            isMove=true;
            ViewCompat.postInvalidateOnAnimation(CloudBarView.this);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    });

    private float dy;
    public void setAnimal(){

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dy= (float) animation.getAnimatedValue();
                mContentRect=new Rect(paddingLeft-bodyWidth,paddingTop-bodyWidth,
                        (int) (paddingLeft+contentWidth*dy)+bodyWidth,mHeight-paddingBottom+bodyWidth);
                postInvalidate();
            }
        });
        valueAnimator.setDuration(1000);
        valueAnimator.start();

    }

    public float getYTemValue(double value, double gap){

        float yTem;
        yTem= (float) ((1-1f*value/gap)*contentHeight)+paddingTop;

        return yTem;

    }

    //绘制横竖线
    private void setLine(Canvas canvas) {

        //横线
        int xCount=6;

        for (int i = 0; i < xCount; i++) {


            double value=minYValues+(maxYValues-minYValues)*i/(xCount-1);
//            if (value < 0) {
//                continue;
//            }
            String valStr=(value >= -0.05 && value <= 0.05) ? "0" : StockChartUtility.formatPrice(value, 1);
            int yTmp = (int) ((contentHeight-linePaint.getTextSize()-5)*(xCount-1-i)/(xCount-1)+yScalePaint.getTextSize())+paddingTop-FunctionHelper.dp2pxInt(2);
            canvas.drawText(valStr,paddingLeft-FunctionHelper.dp2pxInt(6),yTmp,yScalePaint);

            int y=contentHeight/(xCount-1)*i+paddingTop;
            canvas.drawLine(paddingLeft,y,mWidth-paddingRight,y,linePaint);

        }

        canvas.drawLine(paddingLeft,paddingTop,paddingLeft,contentHeight+paddingTop,linePaint);

    }

}
