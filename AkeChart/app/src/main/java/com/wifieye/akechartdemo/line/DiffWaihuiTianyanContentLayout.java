package com.wifieye.akechartdemo.line;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;


public class DiffWaihuiTianyanContentLayout extends LinearLayout {

    public final static int SINGLE_TOUCH_STATUS = 1;
    public final static int MUTI_TOUCH_STATUS = 2;
    public final static int SINGLE_FINGER_MOVE_STATUS = 3;

    private float mPointDistances = 1f;
    private int mTouchModel = SINGLE_TOUCH_STATUS;

    private DiffContainerWaihuiTianyan mHolder;

    private int mTouchX;
    private int mTouchSlop;

    public DiffWaihuiTianyanContentLayout(Context context) {
        super(context);
        init(context);
    }

    public DiffWaihuiTianyanContentLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DiffWaihuiTianyanContentLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (mHolder.isTraderEnv) {
            return false;
        } else if (mHolder.isBusinessEffect) {
            return false;
        }
        int touchAction = event.getAction();
        switch (touchAction & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mTouchX = (int) event.getX();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mPointDistances = distances(event);
                if (mHolder.isTraderEnv) {

                } else if (mHolder.isBusinessEffect) {

                } else if (mPointDistances > 10f && mHolder.getDisplayModel() == DiffContainerWaihuiTianyan.MinDisplayModel.NORMAL) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    mTouchModel = MUTI_TOUCH_STATUS;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mTouchModel == MUTI_TOUCH_STATUS) {
                    float newDistances = distances(event);
                    float distance = newDistances - mPointDistances;
                    if (distance > 50 && distance < 200) {
                        mHolder.enlargeKLineWidth();
                        mPointDistances = newDistances;
                    } else if (distance < -50 && distance > -200) {
                        mHolder.reduceKLineWidth();
                        mPointDistances = newDistances;
                    }
                } else {
                    float touchX = event.getX();
                    float moveX = touchX - (float) mTouchX;
                    if (mHolder.isTraderEnv) {
                        if (moveX != 0f && !mHolder.changeStockOffsetCheck(moveX > 0 ? 1 : -1)) {
                            getParent().requestDisallowInterceptTouchEvent(true);
                            if (mHolder.getViewPager() != null) {
                                mHolder.getViewPager().requestDisallowInterceptTouchEvent(true);
                            }
                        }
                    } else if (mHolder.isBusinessEffect) {
                        if (moveX != 0f && !mHolder.changeStockOffsetCheck(moveX > 0 ? 1 : -1)) {
                            getParent().requestDisallowInterceptTouchEvent(true);
                            if (mHolder.getViewPager() != null) {
                                mHolder.getViewPager().requestDisallowInterceptTouchEvent(true);
                            }
                        }
                    }
                    if (Math.abs(event.getX() - mTouchX) > mTouchSlop
                        /*&& getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE*/) {
                        mTouchModel = SINGLE_FINGER_MOVE_STATUS;
                        float percent = moveX / (float) getWidth();
                        float offset = percent * mHolder.mDisplayCount;
                        mHolder.changeStockOffset((int) offset);
                        if (mHolder.isTraderEnv) {
                            if (Math.abs(offset) >= 1.0f) {
                                mTouchX = (int) touchX;
                            }
                            if (moveX != 0f && !mHolder.changeStockOffsetCheck(moveX > 0 ? 1 : -1)) {
                                getParent().requestDisallowInterceptTouchEvent(true);
                                if (mHolder.getViewPager() != null) {
                                    mHolder.getViewPager().requestDisallowInterceptTouchEvent(true);
                                }
                            }
                        } else if (mHolder.isBusinessEffect) {
                            if (Math.abs(offset) >= 1.0f) {
                                mTouchX = (int) touchX;
                            }
                            if (moveX != 0f && !mHolder.changeStockOffsetCheck(moveX > 0 ? 1 : -1)) {
                                getParent().requestDisallowInterceptTouchEvent(true);
                                if (mHolder.getViewPager() != null) {
                                    mHolder.getViewPager().requestDisallowInterceptTouchEvent(true);
                                }
                            }
                        } else {
                            getParent().requestDisallowInterceptTouchEvent(true);
                            mTouchX = (int) touchX;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mTouchModel == MUTI_TOUCH_STATUS || mTouchModel == SINGLE_FINGER_MOVE_STATUS) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                    mTouchModel = SINGLE_TOUCH_STATUS;
                    return true;
                }
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mHolder.isTraderEnv) {
            return false;
        } else if (mHolder.isBusinessEffect) {
            return false;
        }
        if (mHolder.isTraderEnv) {
            int touchAction = event.getAction();
            switch (touchAction & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    mTouchX = (int) event.getX();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float touchX = event.getX();
                    float moveX = touchX - (float) mTouchX;
                    if (moveX != 0f && !mHolder.changeStockOffsetCheck(moveX > 0 ? 1: -1)) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                        if (mHolder.getViewPager() != null) {
                            mHolder.getViewPager().requestDisallowInterceptTouchEvent(true);
                        }
                    }
                    if (Math.abs(event.getX() - mTouchX) > mTouchSlop
                        /*&& getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE*/) {
                        mTouchModel = SINGLE_FINGER_MOVE_STATUS;
                        float percent = moveX / (float) getWidth();
                        float offset = percent * mHolder.mDisplayCount;
                        mHolder.changeStockOffset((int) offset);
                        if (mHolder.isTraderEnv) {
                            if (Math.abs(offset) >= 1.0f) {
                                mTouchX = (int) touchX;
                            }
                            getParent().requestDisallowInterceptTouchEvent(true);
                            if (mHolder.getViewPager() != null) {
                                mHolder.getViewPager().requestDisallowInterceptTouchEvent(true);
                            }
                        } else if (mHolder.isBusinessEffect) {
                            if (Math.abs(offset) >= 1.0f) {
                                mTouchX = (int) touchX;
                            }
                            getParent().requestDisallowInterceptTouchEvent(true);
                            if (mHolder.getViewPager() != null) {
                                mHolder.getViewPager().requestDisallowInterceptTouchEvent(true);
                            }
                        } else {
                            getParent().requestDisallowInterceptTouchEvent(true);
                            mTouchX = (int) touchX;
                        }
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    if (mTouchModel == MUTI_TOUCH_STATUS || mTouchModel == SINGLE_FINGER_MOVE_STATUS) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                        mTouchModel = SINGLE_TOUCH_STATUS;
                        return true;
                    }
                    break;
                default:
                    break;
            }
            return true;
        } else if (mHolder.isBusinessEffect) {

        }
        return super.onTouchEvent(event);
    }

    private float distances(MotionEvent event) {
        final int pointerCount = event.getPointerCount();
        if (pointerCount > 1) {
            float x = event.getX(0) - event.getX(1);
            float y = event.getY(0) - event.getY(1);
            return (float) Math.sqrt(x * x + y * y);
        } else {
            return 0;
        }
    }

    public void setHolder(DiffContainerWaihuiTianyan container) {
        mHolder = container;
    }

    public int getTouchModel() {
        return mTouchModel;
    }

}

