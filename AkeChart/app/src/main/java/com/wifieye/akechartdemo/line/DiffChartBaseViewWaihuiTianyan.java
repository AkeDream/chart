package com.wifieye.akechartdemo.line;


import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public abstract class DiffChartBaseViewWaihuiTianyan extends StockChartBaseView {
    public int currentPositon = -1;
    protected Paint mKlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected DiffContainerWaihuiTianyan mHolder;
    int lastTouchX;
    private Runnable mExitMoveLineModeRunnable = new Runnable() {
        @Override
        public void run() {
            mHolder.setMoveViewVisibility(View.GONE);
            currentPositon = -1;
            postInvalidate();
        }
    };

    /**
     * @param context
     */
    public DiffChartBaseViewWaihuiTianyan(Context context) {
        super(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public DiffChartBaseViewWaihuiTianyan(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public DiffChartBaseViewWaihuiTianyan(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int) event.getX();
        lastTouchX = touchX;
        if (mHolder.getDisplayModel() == DiffContainerWaihuiTianyan.MinDisplayModel.NORMAL) {
            if (mHolder.isTraderEnv) {
                return false;
            } else if (mHolder.isBusinessEffect) {
                return false;
            }
            return super.onTouchEvent(event);
        } else {
            int touchAction = event.getAction();

            switch (touchAction & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    getParent().requestDisallowInterceptTouchEvent(true);
                    this.removeCallbacks(mExitMoveLineModeRunnable);
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    this.postDelayed(mExitMoveLineModeRunnable, 500);
                    break;
                default:
                    break;
            }
            changePosition(touchX);
            return true;
        }
    }

    private void changePosition(int touchX) {
        if (mHolder.getDataModel() != null) {
            if (touchX >= mHolder.mLeftNumber && touchX <= getWidth() - mHolder.mRightNumber) {
                currentPositon = (touchX - mHolder.mLeftNumber) * (mHolder.mDisplayCount - 1) / (getWidth() - mHolder.mRightNumber - mHolder.mLeftNumber) + mHolder.mDisplayStart;
            } else if (touchX < mHolder.mLeftNumber) {
                currentPositon = mHolder.mDisplayStart;
            } else if (touchX > getWidth() - mHolder.mRightNumber) {
                currentPositon = mHolder.mDisplayStart + mHolder.mDisplayCount - 1;
            }
            mHolder.setScreenIndex(currentPositon);
            mHolder.lastTouchX = touchX;
        }
    }

    public void changePosition() {
        if (mHolder.getDataModel() != null) {
            if (lastTouchX >= mHolder.mLeftNumber && lastTouchX <= getWidth() - mHolder.mRightNumber) {
                currentPositon = (lastTouchX - mHolder.mLeftNumber) * (mHolder.mDisplayCount - 1) / (getWidth() - mHolder.mRightNumber - mHolder.mLeftNumber) + mHolder.mDisplayStart;
            } else if (lastTouchX < mHolder.mLeftNumber) {
                currentPositon = mHolder.mDisplayStart;
            } else if (lastTouchX > getWidth() - mHolder.mRightNumber) {
                currentPositon = mHolder.mDisplayStart + mHolder.mDisplayCount - 1;
            }
            mHolder.setScreenIndex(currentPositon);
            mHolder.lastTouchX = lastTouchX;
        }
    }

    public void setHolder(DiffContainerWaihuiTianyan container) {
        mHolder = container;
        mPaint.setTypeface(mHolder.getTypeFace());
    }

    public void setCurrentPosition(int position) {
        currentPositon = position;
        postInvalidate();
    }
}
