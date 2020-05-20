package com.wifieye.akechartdemo.strip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.wifieye.akechartdemo.R;

public class StripNewActivity extends AppCompatActivity {

    private LineProgressItemView itemView;
    private int num=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strip_new);

        itemView = findViewById(R.id.line_item);

        itemView.post(new Runnable() {
            @Override
            public void run() {
                itemView.setAnimator();
            }
        });

        @SuppressLint("HandlerLeak") Handler handler = new Handler(){

            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                if (num<=80){
                    itemView.setProgress(num);
                    sendEmptyMessageDelayed(1,50);
                    num=num+1;
                }

            }
        };

        LineProgressView lineProgressView=findViewById(R.id.progress_view);
        lineProgressView.setAnimator();

        handler.sendEmptyMessageDelayed(1,10);

    }


}
