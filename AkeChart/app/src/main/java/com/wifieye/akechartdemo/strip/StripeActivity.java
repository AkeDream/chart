package com.wifieye.akechartdemo.strip;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wifieye.akechartdemo.R;

public class StripeActivity extends AppCompatActivity {


    private StripeProgressBar stripeProgressBar;
    private TextView tvProgpress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe);

        tvProgpress = findViewById(R.id.tv_progress);
        stripeProgressBar = findViewById(R.id.stripe_progress_bar);
        ProgressWaveView waveView=findViewById(R.id.wave_view);
        waveView.setProgress((int) (5* 10)
                , 0xff265263, 0xff28f7dd, 0xff1cbeaa);

    }


    public void btnClick(View view) {


        new ProgressThread().start();

    }

    private class ProgressThread extends Thread {

        private int progress = 0;

        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                progress++;
                if (progress > 100) {
                    progress = 0;
                }
                final int p = progress;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        stripeProgressBar.setProgress(p);
                        tvProgpress.setText(p + "%");
                    }
                });
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

}
