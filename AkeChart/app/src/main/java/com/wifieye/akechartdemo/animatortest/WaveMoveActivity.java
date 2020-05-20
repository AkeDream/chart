package com.wifieye.akechartdemo.animatortest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.wifieye.akechartdemo.R;
import com.wifieye.akechartdemo.matrixtest.WaveView;

public class WaveMoveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave_move);

        WaveView waveView= findViewById(R.id.wave_view);
        waveView.startAnim();

    }



}
