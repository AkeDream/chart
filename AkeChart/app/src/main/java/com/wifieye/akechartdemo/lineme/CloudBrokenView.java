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

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

public class CloudBrokenView extends View  {

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

    public CloudBrokenView(Context context) {
        super(context);
        init(context);
    }

    public CloudBrokenView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CloudBrokenView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        paddingTop=FunctionHelper.dp2pxInt(5);
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

    }

    public void setCanScroll(boolean isCanScroll){
        this.isCanScroll=isCanScroll;
    }

    private boolean isScrollVisible(int total){
       return isCanScroll && allVisibleCount < total;
    }

    private static final String TAG = "CloudBrokenView";
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (xValues==null||xValues.length==0) {
            return;
        }

        if (yValues==null||yValues.length==0){
            return;
        }

        int total=yValues.length;
        int visibleCount=total;
        if (isScrollVisible(total)){
            visibleCount=allVisibleCount;
        }

        //保证光标点不超过边界

        if ( mMoveScrollX < paddingLeft) {
            mMoveScrollX=paddingLeft;
        }
        if (mMoveScrollX> paddingLeft+contentWidth){
            mMoveScrollX=paddingLeft+contentWidth;
        }

        moveX=mMoveScrollX;
        float xItem;
        if (visibleCount==1) {
            xItem=contentWidth;
        }else {
            xItem = (float) (contentWidth / (visibleCount - 1f));
        }
        float maxLeftDistance = (total - allVisibleCount) * xItem;

        if (isScrollVisible(total)){

            mDistanceX=mDistanceX+mTotalScrollX;
            if (mDistanceX>maxLeftDistance){
                mDistanceX=maxLeftDistance;
            }

            if (mDistanceX<0){
                mDistanceX=0;
            }

        }else {
            maxLeftDistance=0;
            mDistanceX=0;
        }

        setLine(canvas);

        int saveCount0 = canvas.save();

        canvas.clipRect(xScaleRect);
        for (int i = 0; i < total; i++) {

            float xTem= (float) (i*xItem+paddingLeft+mDistanceX-maxLeftDistance);
            canvas.drawText(xValues[i],xTem,mHeight-paddingBottom+xScalePaint.getTextSize()+FunctionHelper.dp2pxInt(5),xScalePaint);
            if (i>0){
                canvas.drawLine(i*xItem+paddingLeft,paddingTop,i*xItem,mHeight-paddingBottom,linePaint);
            }

        }


        canvas.restoreToCount(saveCount0);
        canvas.drawRect(xScaleRect,mCanvasBGPaint);

        mPath.reset();
        mShadowPath.reset();
        int saveCount = canvas.save();
        canvas.clipRect(mContentRect);


        mShadowPath.moveTo(paddingLeft,paddingTop+contentHeight);
        for (int i = 0; i < total; i++) {

            float xTem= (float) (i*xItem+paddingLeft+mDistanceX-maxLeftDistance);

            if (i>0){
                canvas.drawLine(xTem,paddingTop,xTem,mHeight-paddingBottom,linePaint);
            }

            float yTem= getYTemValue(yValues[i]-minYValues,(maxYValues-minYValues));

            mShadowPath.lineTo(xTem,yTem);
            if (i==0){
                moveX=xTem;

                mPath.moveTo(xTem,yTem);
            }else {
                mPath.lineTo(xTem,yTem);
            }

            if (i==total-1){
                mShadowPath.lineTo(xTem,paddingTop+contentHeight);
            }

            if (mMoveScrollX>=xTem){
                moveY = yTem;
                moveX=xTem;
                moveValueIndex=i;
            }


        }
        mShadowPath.close();
        canvas.drawPath(mShadowPath,shadowPaint);
        canvas.drawPath(mPath,mPaint);

        canvas.drawRect(new Rect(paddingLeft-bodyWidth,paddingTop-bodyWidth,
                paddingLeft,paddingTop+contentHeight+bodyWidth),bgPaint);

        for (int i = 0; i < total; i++) {

            float xTem= (float) (i*xItem+paddingLeft+mDistanceX-maxLeftDistance);
            float yTem= getYTemValue(yValues[i]-minYValues,(maxYValues-minYValues));

            if (type==1){
                canvas.drawCircle(xTem,yTem,whiteRadius,whitePaint);
                canvas.drawCircle(xTem,yTem,strokeRadius,strokePaint);
            }else {


                //绘制最后一个值 指示框
                if (i==(total-1)){
                    setDialogValue(canvas, yValues[i], xTem, yTem,moveLastColor,moveLastCircleColor);
                }

            }


        }


        if (isMove){
            Log.e(TAG, "onDraw: moveY"+ moveY);
            movePath.reset();
            movePath.moveTo(paddingLeft,moveY);
            movePath.lineTo(paddingLeft+contentWidth,moveY);
            canvas.drawPath(movePath,effectPaint);

            movePath.reset();
            movePath.moveTo(moveX,paddingTop);
            movePath.lineTo(moveX,paddingTop+contentHeight);
            canvas.drawPath(movePath,effectPaint);


            setDialogValue(canvas, yValues[moveValueIndex], moveX, moveY,moveCurrentColor,moveCurrentCircleColor);

        }


        canvas.restoreToCount(saveCount);
//        canvas.drawRect(mContentRect,mCanvasBGPaint);


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
                            ViewCompat.postInvalidateOnAnimation(CloudBrokenView.this);
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
                        ViewCompat.postInvalidateOnAnimation(CloudBrokenView.this);
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
            ViewCompat.postInvalidateOnAnimation(CloudBrokenView.this);
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {

            isMove=true;
            ViewCompat.postInvalidateOnAnimation(CloudBrokenView.this);
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
        valueAnimator.setDuration(1500);
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

        //竖线
//        int yCount=6;
//        for (int i = 0; i < yCount; i++) {
//
//            int x=contentWidth/(yCount-1)*i+paddingLeft;
//            canvas.drawLine(x,paddingTop,x,contentHeight+paddingTop,linePaint);
//        }

    }

}
