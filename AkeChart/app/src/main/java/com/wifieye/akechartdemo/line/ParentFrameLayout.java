package com.wifieye.akechartdemo.line;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;



import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.wifieye.akechartdemo.R;


public class ParentFrameLayout extends FrameLayout {
    private int mWidth;
    private int mHeight;
    private View m_diff_container;

    public ParentFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParentFrameLayout(Context context) {
        super(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        m_diff_container = getChildAt(0);
//        m_diff_container = findViewById(R.id.diff_container);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if ((h > 0 && h != oldh) || (w > 0 && w != oldw)) {
            this.mWidth = w;
            this.mHeight = h;
            layoutAsNeeded();
            if (m_diff_container .getId()==R.id.diff_container) {
                LayoutParams lp = (LayoutParams) m_diff_container.getLayoutParams();
                lp.height = h;
            }
        }
    }

    private void layoutAsNeeded() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
        } else {
        }
    }
}
