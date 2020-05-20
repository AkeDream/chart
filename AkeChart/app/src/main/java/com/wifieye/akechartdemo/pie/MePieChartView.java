package com.wifieye.akechartdemo.pie;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.List;

/**
 * Create by ake on 2019/11/8
 * Describe: 自定义圆饼图
 */
public class MePieChartView extends View {


    private Paint mChartPaint; //环形画笔

    private Paint mCirclePaint; //中心圆

    private RectF mRectF;

    private int padding;

    private List<PieModel> mPieModelList;

    private float mAnimaAngle;

    private RectF mSelectedRectF = new RectF();

    //必备初始化 重写三个 第一个 代码中初始化调用 第二 xml中调用 第三个 手动调用 第四个一般用不到
    public MePieChartView(Context context) {
        this(context,null);
    }

    public MePieChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MePieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }



    private void init() {

        mChartPaint = new Paint();//画笔初始化
        mChartPaint.setAntiAlias(true);//抗锯齿 具有一定性能损耗
        mChartPaint.setDither(true); //防抖动 可以使图看起来更柔和些 (我感觉没啥用)
        mChartPaint.setStrokeWidth(100); //圆环宽度
        mChartPaint.setStyle(Paint.Style.FILL);// 画笔样式 Paint.Style.FILL :填充内部
                                               // Paint.Style.FILL_AND_STROKE ：填充内部和描边
                                               // Paint.Style.STROKE ：仅描边

        mCirclePaint=new Paint();
        mCirclePaint.setAntiAlias(true);
        mChartPaint.setDither(true);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(Color.WHITE);//设置画笔颜色 setARGB()


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mPieModelList==null||mPieModelList.isEmpty()){
            return;
        }


        for (int i = 0; i < mPieModelList.size(); i++) {

            PieModel pieModelItem = mPieModelList.get(i);
            if (pieModelItem.percent>0){
//                if (mAnimaAngle>=mPieModelList.get(i).startAngle&&mAnimaAngle<=(mPieModelList.get(i).startAngle+mPieModelList.get(i).sweepAngle)) {
//
//                    drawColor(canvas,mPieModelList.get(i).color,mPieModelList.get(i).startAngle,mAnimaAngle-mPieModelList.get(i).startAngle);
//
//                }else if (mAnimaAngle>=(mPieModelList.get(i).startAngle+mPieModelList.get(i).sweepAngle)){
//
//                    drawColor(canvas,mPieModelList.get(i).color,mPieModelList.get(i).startAngle,mPieModelList.get(i).sweepAngle);
//                }

                if (pieModelItem.selected){

                    //位置计算类
                    float cirX = getWidth() / 2;
                    float cirY = getHeight() / 2 ;
                    float radius = (getWidth()-padding*2) / 2;//150;

                    //当前扇形中间坐标 角度转为弧度
                    //选中状态 沿扇形中心线移动圆心 产生突出效果
                    float arcAngle = (float) (Math.PI * (pieModelItem.startAngle+ pieModelItem.sweepAngle/2) / 180.0);
                    float posX=cirX+(float)(Math.cos(arcAngle)) * 10;
                    float posY = cirY + (float)(Math.sin(arcAngle)) * 10;

                    float arcLeft = posX - radius;
                    float arcTop  = posY - radius ;
                    float arcRight = posX + radius ;
                    float arcBottom = posY + radius ;

                    mSelectedRectF=new RectF(arcLeft,arcTop,arcRight,arcBottom);


                    drawSelectedView(canvas, pieModelItem.color, pieModelItem.startAngle, pieModelItem.sweepAngle);

                    //绘制白色圆环  减少半径 更换白色画笔即可
                    float innerRadius=padding+20;
                    float arcLeftInner = cirX - innerRadius;
                    float arcTopInner  = cirY - innerRadius ;
                    float arcRightInner = cirX + innerRadius ;
                    float arcBottomInner = cirY + innerRadius ;
                    RectF innerRectF=new RectF(arcLeftInner,arcTopInner,arcRightInner,arcBottomInner);
                    canvas.drawArc(innerRectF,pieModelItem.startAngle,pieModelItem.sweepAngle,true,mCirclePaint);

                }
                else {
                    drawColor(canvas, pieModelItem.color, pieModelItem.startAngle, pieModelItem.sweepAngle);
                }
            }



        }

        canvas.drawCircle(getMeasuredWidth()/2,getMeasuredWidth()/2,padding,mCirclePaint);

    }


    private void drawColor(Canvas canvas,int color,float startAngle,float sweepAngle){
        mChartPaint.setColor(color);
        mChartPaint.setAlpha(255);
        canvas.drawArc(mRectF,startAngle,sweepAngle,true,mChartPaint);

    }


    private void  drawSelectedView(Canvas canvas,int color,float startAngle,float sweepAngle){
        mChartPaint.setColor(color);
        mChartPaint.setAlpha(255);
        canvas.drawArc(mSelectedRectF,startAngle,sweepAngle,true,mChartPaint);

    }



    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


    public void startAnima() {
        final ValueAnimator mValueAnimator = ValueAnimator.ofFloat(0f, 360f);
        mValueAnimator.setDuration(3 * 1000);

        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override public void onAnimationUpdate(ValueAnimator animation) {
                mAnimaAngle = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimator.start();
    }

    public void setData(List<PieModel> pieModelList) {
        this.mPieModelList = pieModelList;
        for (int i = 0; i < mPieModelList.size(); i++) {
            PieModel model = mPieModelList.get(i);
            if (i == 0) {
                model.startAngle = 0;
            } else {
                model.startAngle = mPieModelList.get(i - 1).startAngle + mPieModelList.get(i - 1).sweepAngle;
            }
            model.sweepAngle = (model.percent * 360);
        }
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        padding = w / 6;
        mRectF = new RectF(padding, padding, w - padding, w - padding);

//        mSelectedRectF.set(mRectF);
//        mSelectedRectF.inset(-30, -30);
    }



}
