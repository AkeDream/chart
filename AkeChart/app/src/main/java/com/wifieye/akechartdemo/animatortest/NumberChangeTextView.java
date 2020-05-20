package com.wifieye.akechartdemo.animatortest;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import static java.math.RoundingMode.HALF_UP;

/**
 * Create by ake on 2019/12/5
 * Describe:
 */
public class NumberChangeTextView extends AppCompatTextView {

    private String numStart="0";//开始

    private String numEnd="0";//结束

    private long mDuration=2000;//动画时间默认两秒

    private boolean isAnim=true;//是否启用动画

    private ValueAnimator valueAnimator;

    public NumberChangeTextView(Context context) {
        super(context);
    }

    public NumberChangeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NumberChangeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setDuration(long mDuration) {
        this.mDuration = mDuration;
    }

    private boolean isInt;

    public void setNumberString(String end){
        setNumberString("0",end);
    };

    public void setNumberString(String start,String end){
        numStart=start;
        numEnd=end;
        String regexInteger = "-?\\d*";
        isInt = numEnd.matches(regexInteger) && start.matches(regexInteger);

        if (checkNumString(start, end)&&isAnim) {
            // 数字合法　开始数字动画
            startAnim();
        } else {
            // 数字不合法　直接调用　setText　设置最终值
            setText(end);
        }

    }

    /**
     * 校验数字的合法性
     *
     * @param numberStart 　开始的数字
     * @param numberEnd   　结束的数字
     * @return 合法性
     */
    private boolean checkNumString(String numberStart, String numberEnd) {

        String regexInteger = "-?\\d*";
        isInt = numberEnd.matches(regexInteger) && numberStart.matches(regexInteger);
        if (isInt) {
            return true;
        }
        String regexDecimal = "-?[1-9]\\d*.\\d*|-?0.\\d*[1-9]\\d*";
        if ("0".equals(numberStart)) {
            if (numberEnd.matches(regexDecimal)) {
                return true;
            }
        }
        if (numberEnd.matches(regexDecimal) && numberStart.matches(regexDecimal)) {
            return true;
        }
        return false;
    }

    public void setAnim(boolean anim) {
        isAnim = anim;
    }

    private void startAnim(){

        valueAnimator = ValueAnimator.ofObject(new BigEvaluator(), new BigDecimal(numStart), new BigDecimal(numEnd));
        valueAnimator.setDuration(mDuration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                BigDecimal animatedValue = (BigDecimal) animation.getAnimatedValue();
                Log.e("TAG", "onAnimationUpdate: "+animatedValue );
//                setText(format(animatedValue));
                String[] split = numEnd.split("\\.");
                int length=0;
                if (split.length>1) {
                    length=split[1].length();
                }
                BigDecimal divide = animatedValue.setScale(length, HALF_UP);
                setText(String.valueOf(divide));
            }
        });
        valueAnimator.start();

    }

    /**
     * 格式化 BigDecimal ,小数部分时保留两位小数并四舍五入
     *
     * @param bd 　BigDecimal
     * @return 格式化后的 String
     */
    private String format(BigDecimal bd) {
        StringBuilder pattern = new StringBuilder();
        if (isInt) {
            pattern.append("#,###");
        } else {
            int length = 0;
            String[] s1 = numStart.split("\\.");
            String[] s2 = numEnd.split("\\.");
            String[] s = s1.length > s2.length ? s1 : s2;
            if (s.length > 1) {
                // 小数部分
                String decimals = s[1];
                if (decimals != null) {
                    length = decimals.length();
                }
            }
            pattern.append("#,##0");
            if (length > 0) {
                pattern.append(".");
                for (int i = 0; i < length; i++) {
                    pattern.append("0");
                }
            }
        }
        DecimalFormat df = new DecimalFormat(pattern.toString());
        return df.format(bd);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
    }

    public class BigEvaluator implements TypeEvaluator<BigDecimal>{

        @Override
        public BigDecimal evaluate(float fraction, BigDecimal startValue, BigDecimal endValue) {

            BigDecimal addDe=(endValue.subtract(startValue)).multiply(new BigDecimal(fraction));
            return startValue.add(addDe);
        }
    }

}
