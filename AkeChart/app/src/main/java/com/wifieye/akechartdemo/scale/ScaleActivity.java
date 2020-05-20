package com.wifieye.akechartdemo.scale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TimePicker;

import com.wifieye.akechartdemo.R;

public class ScaleActivity extends AppCompatActivity {

    private int num=0;
    private Handler handler;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale);

        final CloudScaleView scaleView= findViewById(R.id.cloud_scale_view);

        final InstrumentView viewById = (InstrumentView) findViewById(R.id.instrumentView);
        SeekBar viewById1 = (SeekBar) findViewById(R.id.sb);

        final PointerScaleView testScaleView=findViewById(R.id.test_scale_view);

//        TimePicker timePicker = new TimePicker(this);
//        timePicker.


        handler = new Handler(){

            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                if (num<=100){
                    testScaleView.setProgress(num);
                    sendEmptyMessageDelayed(1,1);
                    num=num+1;
                }

            }
        };

        handler.sendEmptyMessageDelayed(1,10);

        viewById1.setMax(100);
        viewById1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                viewById.setProgress(progress);
                scaleView.setProgress(progress);
                testScaleView.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    public void btnClick(View view) {

        switch (view.getId()) {
            case R.id.btn1:

                num=0;
                handler.sendEmptyMessageDelayed(1,10);

                break;
        }

    }
}
