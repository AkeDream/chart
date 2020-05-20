package com.wifieye.akechartdemo.lineme;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.wifieye.akechartdemo.line.StockChartBaseView;

public class PriceBaseView extends StockChartBaseView {

    private int currentPosition=-1;
    protected Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
    protected MultContentLayout mHolder;
    private int lastTouchX;


    public void setHolder(MultContentLayout holder){
        mHolder=holder;
        paint.setTypeface(holder.getTypeFace());
    }

    public PriceBaseView(Context context) {
        super(context);
    }

    public PriceBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PriceBaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean b = super.onTouchEvent(event);
        Log.e("TAG", "onTouchEvent: "+b );
        return b;
//        lastTouchX= (int) event.getX();
//
//        if (mHolder.getDisplayModel()==MultContentLayout.DisplayModel.NORMAL){
//            return super.onTouchEvent(event);
//        }else {
//            int action = event.getAction();
//            switch (action & MotionEvent.ACTION_MASK){
//                case MotionEvent.ACTION_DOWN:
//                case MotionEvent.ACTION_MOVE:
//                    getParent().requestDisallowInterceptTouchEvent(true);
//                    this.removeCallbacks(mExitMoveLineModeRunnable);
//                    break;
//                case MotionEvent.ACTION_CANCEL:
//                case MotionEvent.ACTION_UP:
//                    this.postDelayed(mExitMoveLineModeRunnable, 500);
//                    break;
//                default:
//                    break;
//
//            }
//            changePosition(lastTouchX);
//            return true;
//        }

    }

    private void changePosition(int touchX) {
        if (mHolder.getDataModel() != null) {
            if (touchX >= mHolder.mLeftNumber && touchX <= getWidth() - mHolder.mRightNumber) {
                currentPosition = (touchX - mHolder.mLeftNumber) * (mHolder.mDisplayCount - 1) / (getWidth() -
                        mHolder.mRightNumber - mHolder.mLeftNumber) + mHolder.mDisplayStart;
            } else if (touchX < mHolder.mLeftNumber) {
                currentPosition = mHolder.mDisplayStart;
            } else if (touchX > getWidth() - mHolder.mRightNumber) {
                currentPosition = mHolder.mDisplayStart + mHolder.mDisplayCount - 1;
            }
            mHolder.setScreenIndex(currentPosition);
            mHolder.lastTouchX = touchX;
        }
    }


    private Runnable mExitMoveLineModeRunnable = new Runnable() {
        @Override
        public void run() {
            postInvalidate();
        }
    };


    @Override
    protected void paintBackground(Canvas canvas) {

    }

}
