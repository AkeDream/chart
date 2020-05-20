package com.wifieye.akechartdemo.animatortest;

import android.animation.TypeEvaluator;

/**
 * Create by ake on 2019/12/5
 * Describe:
 */
public class CharEvaluator implements TypeEvaluator<Character> {
    @Override
    public Character evaluate(float fraction, Character startValue, Character endValue) {
        int startInt  = (int)startValue;
        int endInt = (int)endValue;
        int curInt = (int)(startInt + fraction *(endInt - startInt));
        char result = (char)curInt;
        return result;
    }
}