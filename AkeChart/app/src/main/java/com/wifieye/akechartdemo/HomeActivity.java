package com.wifieye.akechartdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wifieye.akechartdemo.animatortest.AnimatorTestActivity;
import com.wifieye.akechartdemo.animatortest.WaveMoveActivity;
import com.wifieye.akechartdemo.matrixtest.MatrixTestActivity;
import com.wifieye.akechartdemo.otherchart.LineChartActivity;
import com.wifieye.akechartdemo.scale.ScaleActivity;
import com.wifieye.akechartdemo.strip.StripNewActivity;
import com.wifieye.akechartdemo.strip.StripeActivity;
import com.wifieye.akechartdemo.test.Main2Activity;
import com.wifieye.akechartdemo.viewtest.LookActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



    }


    public void btnClick(View view) {

        switch (view.getId()) {
            case R.id.textView:

                startActivity(new Intent(this,MainActivity.class));

                break;
            case R.id.textView2:
                startActivity(new Intent(this, ScaleActivity.class));
                break;
            case R.id.textView3:
                startActivity(new Intent(this, StripeActivity.class));
                break;
            case R.id.textView4:
                startActivity(new Intent(this, WaveMoveActivity.class));
                break;
            case R.id.textView5:
                startActivity(new Intent(this, MatrixTestActivity.class));
                break;
            case R.id.textView7:
                startActivity(new Intent(this, AnimatorTestActivity.class));
                break;
            case R.id.textView11:
                startActivity(new Intent(this, LookActivity.class));
                break;
            case R.id.textView12:
                startActivity(new Intent(this,LineChartActivity.class));
                break;
            case R.id.textView13:

                startActivity(new Intent(this, Main2Activity.class));
                break;
        }

    }
}
