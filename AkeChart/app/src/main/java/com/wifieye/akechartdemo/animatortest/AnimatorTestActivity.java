package com.wifieye.akechartdemo.animatortest;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wifieye.akechartdemo.R;

import java.security.Key;

public class AnimatorTestActivity extends AppCompatActivity {

    private TextView tv;
    private MyTextView textView9;
    private NumberChangeTextView numberTv;
    private ValueAnimator animator;


    private ImageView menu;
    private ImageView item1,item2,item3,item4,item5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator_test);

        tv = findViewById(R.id.textView6);
        textView9=findViewById(R.id.textView9);
        numberTv = findViewById(R.id.textView10);

        menu=findViewById(R.id.menu);
        item1=findViewById(R.id.item1);
        item2=findViewById(R.id.item2);
        item3=findViewById(R.id.item3);
        item4=findViewById(R.id.item4);
        item5=findViewById(R.id.item5);

        setView();

    }

    private void setView() {




    }

    private void doOfObjectAnim(){

        PropertyValuesHolder valuesHolder = PropertyValuesHolder.ofObject("CharText", new CharEvaluator(), new Character('A'), new Character('Z'));
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(textView9, valuesHolder);
        objectAnimator.setDuration(3000);
        objectAnimator.start();

    }

    private void setAnimator() {



        PropertyValuesHolder rotationHolder = PropertyValuesHolder.ofFloat("Rotation", 60f, -60f, 40f, -40f, -20f, 20f, 10f, -10f, 0f);
        PropertyValuesHolder colorHolder = PropertyValuesHolder.ofInt("BackgroundColor", 0xffffffff, 0xffff00ff, 0xffffff00, 0xffffffff);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(tv, rotationHolder, colorHolder);
        animator.setDuration(3000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();

    }

    private void setValue(){

        animator = ValueAnimator.ofFloat(0, 1.0f);
        animator.setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //空指针
                tv.setText(animation.getAnimatedValue().toString());
            }
        });

        animator.start();

    }

    private void setFrame(){

        Keyframe frame0 = Keyframe.ofFloat(0f, 0);
        Keyframe frame1 = Keyframe.ofFloat(0.1f, -20f);
        Keyframe frame2 = Keyframe.ofFloat(0.2f, 20f);
        Keyframe frame3 = Keyframe.ofFloat(0.3f, -20f);
        Keyframe frame4 = Keyframe.ofFloat(0.4f, 20f);
        Keyframe frame5 = Keyframe.ofFloat(0.5f, -20f);
        Keyframe frame6 = Keyframe.ofFloat(0.6f, 20f);
        Keyframe frame7 = Keyframe.ofFloat(0.7f, -20f);
        Keyframe frame8 = Keyframe.ofFloat(0.8f, 20f);
        Keyframe frame9 = Keyframe.ofFloat(0.9f, -20f);
        Keyframe frame10 = Keyframe.ofFloat(1, 0);
        PropertyValuesHolder frameHolder = PropertyValuesHolder.ofKeyframe("rotation",frame0,frame1,frame2,frame3,frame4,frame5,frame6,frame7,frame8,frame9,frame10);

        Animator animator = ObjectAnimator.ofPropertyValuesHolder(tv,frameHolder);
        animator.setDuration(1000);
        animator.start();

    }


    private boolean isStart=true;
    public void btnClick(View view) {

        switch (view.getId()) {
            case R.id.textView8:
//                setAnimator();
                doOfObjectAnim();
//                setValue();
                setFrame();

                numberTv.setNumberString("1235687.140");

                break;

            case R.id.menu:
                if (isStart){
                    doAnimateOpen(item1, 0, 5, 500);
                    doAnimateOpen(item2, 1, 5, 500);
                    doAnimateOpen(item3, 2, 5, 500);
                    doAnimateOpen(item4, 3, 5, 500);
                    doAnimateOpen(item5, 4, 5, 500);
                }     else {
                    doAnimateClose(item1, 0, 5, 500);
                    doAnimateClose(item2, 1, 5, 500);
                    doAnimateClose(item3, 2, 5, 500);
                    doAnimateClose(item4, 3, 5, 500);
                    doAnimateClose(item5, 4, 5, 500);
                }

                isStart=!isStart;

                break;

            case R.id.item1:
            case R.id.item2:
            case R.id.item3:
            case R.id.item4:
            case R.id.item5:



//                Toast.makeText(this, "你点击了" + view, Toast.LENGTH_SHORT).show();

                break;

                default:break;

        }

    }

    private void doAnimateClose(final View view, int index, int total,
                                int radius) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
        double degree = Math.PI * index / ((total - 1) * 2);
        int translationX = -(int) (radius * Math.sin(degree));
        int translationY = -(int) (radius * Math.cos(degree));
        AnimatorSet set = new AnimatorSet();
        //包含平移、缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", translationX, 0),
                ObjectAnimator.ofFloat(view, "translationY", translationY, 0),
                ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f),
                ObjectAnimator.ofFloat(view, "scaleY", 1f, 0f),
                ObjectAnimator.ofFloat(view, "alpha", 1f, 0f));

        set.setDuration(1 * 500).start();

        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (view.getVisibility() != View.GONE) {
                    view.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        set.addPauseListener(new Animator.AnimatorPauseListener() {
            @Override
            public void onAnimationPause(Animator animation) {
                if (view.getVisibility() != View.GONE) {
                    view.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationResume(Animator animation) {

            }
        });
    }



    private void doAnimateOpen(View view, int index, int total, int radius) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
        double degree = Math.toRadians(90)/(total - 1) * index;
        int translationX = -(int) (radius * Math.sin(degree));
        int translationY = -(int) (radius * Math.cos(degree));

        AnimatorSet set = new AnimatorSet();
        //包含平移、缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", 0, translationX),
                ObjectAnimator.ofFloat(view, "translationY", 0, translationY),
                ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f),
                ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f),
                ObjectAnimator.ofFloat(view, "alpha", 0f, 1));
        //动画周期为500ms
        set.setDuration(1 * 500).start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (animator != null) {
            animator.cancel();
        }
        tv=null;
        textView9=null;
    }


}
