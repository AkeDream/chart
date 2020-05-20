package com.wifieye.akechartdemo.viewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;

import com.wifieye.akechartdemo.R;

public class LookActivity extends BaseActivity {

    private Paint paint=new Paint();

    private static final String TAG = "LookActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look);


        if (paint != null) {
            Log.e(TAG, "onCreate: not" );
        }else {
            Log.e(TAG, "onCreate: null" );

        }

    }

    @Override
    protected void setView() {
        super.setView();

        if (paint != null) {
            Log.e(TAG, "setView: not" );
        }else {
            Log.e(TAG, "setView: null" );
        }

    }

}
