package com.wifieye.akechartdemo.strip;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;



public class ProgressWaveView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mLeftPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mRightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mTextSize;
    private Rect mRect = new Rect();
    private Path mBgPath = new Path();
    private Path mProPath = new Path();
    private Path mGapPath = new Path();
    private final float mGapWidth = 12;
    private final float mGap = 16;
    private final float mGapHeight = 16;
    private float mGapMove = 0;
    private final float mGapMoveSpeed = 2.0f;
    private float padding = 0;//dip3;
    private int left_color = 0xff4cc1ff;
    private int right_color = 0xff4787e5;
    private int bg_color = 0xffedf4fe;
    private float duration = 1000f;
    private long startTime = 0L;
    private float total_progress = 100f;
    private float progress = 0f;
    private boolean isAnimate = true;

    public ProgressWaveView(Context context) {
        super(context);
        init();
    }

    public ProgressWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        mTextSize = 24;
        mPaint.setTextSize(mTextSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        float width = getWidth();
        float height = getHeight();
        if (width > padding && height > padding && progress >= 0 && progress <= 100f) {
            try {

                mBgPath.reset();
                float radius_left = (height - padding * 2f) / 2f;
                float radius_right = height / 2f;
                float startX = padding + radius_left;
                float startY = height - padding;
                float p = (progress / total_progress) * width;
                float now = SystemClock.elapsedRealtime() - startTime;
                if (progress > 0 && now >= 0 && now <= duration) {
                    p = now / duration * p;
                    postInvalidate();
                }
                mBgPath.moveTo(startX, startY);
                mBgPath.arcTo(padding, padding, padding + radius_left * 2f, height - padding, 90, 180, false);
                mBgPath.lineTo(width - padding - radius_left, padding);
                mBgPath.arcTo(width - padding - radius_left * 2f, padding, width - padding, height - padding, 270, 180, false);
                mBgPath.lineTo(startX, startY);
                mBgPath.close();
                mBgPaint.setAntiAlias(true);
                mBgPaint.setStyle(Style.FILL);
                mBgPaint.setStrokeWidth(2);
                mBgPaint.setColor(bg_color);
                canvas.drawPath(mBgPath, mBgPaint);

                if (progress > 0) {
                    mProPath.reset();
                    mProPath.moveTo(startX, startY);
                    mProPath.arcTo(padding, padding, padding + radius_left * 2f, height - padding, 90, 180, false);
//                mProPath.lineTo(padding + p * 2 / 3, padding);
                    mProPath.lineTo(padding + p - radius_right * 2, 0);
//                    mProPath.cubicTo(padding + p * 0.8f, padding, padding + p * 0.95f, 0, padding + p, 0);
                    mProPath.arcTo(padding + p - radius_right * 2, 0, padding + p, height, 270, 180, false);
//                mProPath.lineTo(padding + p * 2 / 3, startY);
                    mProPath.lineTo(startX, startY);
//                    mProPath.cubicTo(padding + p * 0.95f, height, padding + p * 0.8f, startY, startX, startY);
                    mProPath.close();
                    mPaint.setAntiAlias(true);
                    mPaint.setStyle(Style.FILL);
                    mPaint.setStrokeWidth(2);
                    mPaint.setColor(right_color);

                    mLeftPaint.setAntiAlias(true);
                    mLeftPaint.setStyle(Style.FILL);
                    mLeftPaint.setStrokeWidth(2);
                    mLeftPaint.setColor(left_color);

                    mRightPaint.setAntiAlias(true);
                    mRightPaint.setStyle(Style.FILL);
                    mRightPaint.setStrokeWidth(2);
                    mRightPaint.setColor(right_color);
                    canvas.drawPath(mProPath, mLeftPaint);
                    if (isAnimate) {
                        canvas.save();
                        canvas.clipPath(mProPath);
                        mGapMove = mGapMove % (mGap + mGapWidth);
                        for (float x = mGapMove; x <= width; x = (x + mGap + mGapWidth)) {
                            mGapPath.reset();
                            mGapPath.moveTo(x, height);
                            mGapPath.lineTo(x + height, 0);
                            mGapPath.lineTo(x + height + mGapWidth, 0);
                            mGapPath.lineTo(x + mGapWidth, height);
                            mGapPath.close();
                            canvas.drawPath(mGapPath, mRightPaint);
                        }

                        for (float x = (mGapMove - (mGap + mGapWidth)); x >= - (mGap + mGapWidth); x = (x - (mGap + mGapWidth))) {
                            mGapPath.reset();
                            mGapPath.moveTo(x, height);
                            mGapPath.lineTo(x + height, 0);
                            mGapPath.lineTo(x + height + mGapWidth, 0);
                            mGapPath.lineTo(x + mGapWidth, height);
                            mGapPath.close();
                            canvas.drawPath(mGapPath, mRightPaint);
                        }
                        mGapMove = mGapMove + mGapMoveSpeed;
                        canvas.restore();
//                        postInvalidate();
                    }
//                canvas.drawCircle(padding + p, height / 2f, radius_left, mBgPaint);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        canvas.restore();
    }

    public void setProgress(int i, int bg_color, int left_color, int right_color) {
        this.bg_color = bg_color;
        this.left_color = left_color;
        this.right_color = right_color;
        progress = i;
        startTime = SystemClock.elapsedRealtime();
        postInvalidate();
    }

    public int getProgress() {
        return (int)progress;
    }
}

