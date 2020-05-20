package com.wifieye.akechartdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;


import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wifieye.akechartdemo.pie.ColorRandom;
import com.wifieye.akechartdemo.pie.MePieChartView;
import com.wifieye.akechartdemo.pie.PanelPieChart2;
import com.wifieye.akechartdemo.pie.PieModel;


public class MainActivity extends AppCompatActivity {

    private MePieChartView id_pie_chart;

    private TextView		id_tv_1, id_tv_2, id_tv_3, id_tv_4;

    private List<PieModel>	pieModelList	= new ArrayList<>();

    private List<Integer>	colorList		= new ArrayList<>();

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_linear);
        PanelPieChart2 panelPieChart2 = new PanelPieChart2(this);
        layout.addView(panelPieChart2);

        id_tv_1 = findViewById(R.id.id_tv_1);
        id_tv_2 = findViewById(R.id.id_tv_2);
        id_tv_3 = findViewById(R.id.id_tv_3);
        id_tv_4 = findViewById(R.id.id_tv_4);
        id_pie_chart = findViewById(R.id.id_pie_chart);

        ColorRandom colorRandom = new ColorRandom(10);
        for (int i = 0; i < 4; i++) {
            int colors = (int) colorRandom.getColors().get(i);
            if (i == 0) {
                pieModelList.add(new PieModel(colors, 0.1f));
            } else {
                pieModelList.add(new PieModel(colors, 0.3f));
            }
        }

        id_pie_chart.setData(pieModelList);
//        id_pie_chart.startAnima();

        List<Double> mList = new ArrayList<>();
        mList.add(0.25);
        mList.add(0.35);
        mList.add(0.15);
        mList.add(0.05);
        mList.add(0.20);

        id_tv_1.setOnClickListener(new View.OnClickListener() {

            @Override public void onClick(View v) {
                if (pieModelList.get(0).selected) {
                    pieModelList.get(0).selected = false;
                } else {
                    pieModelList.get(0).selected = true;
                }
                id_pie_chart.setData(pieModelList);
//                id_pie_chart.invalidate();
            }
        });

        id_tv_2.setOnClickListener(new View.OnClickListener() {

            @Override public void onClick(View v) {
                if (pieModelList.get(1).selected) {
                    pieModelList.get(1).selected = false;
                } else {
                    pieModelList.get(1).selected = true;
                }
                id_pie_chart.setData(pieModelList);
                id_pie_chart.invalidate();
            }
        });

        id_tv_3.setOnClickListener(new View.OnClickListener() {

            @Override public void onClick(View v) {
                if (pieModelList.get(2).selected) {
                    pieModelList.get(2).selected = false;
                } else {
                    pieModelList.get(2).selected = true;
                }
                id_pie_chart.setData(pieModelList);
                id_pie_chart.invalidate();
            }
        });

        id_tv_4.setOnClickListener(new View.OnClickListener() {

            @Override public void onClick(View v) {
                if (pieModelList.get(3).selected) {
                    pieModelList.get(3).selected = false;
                } else {
                    pieModelList.get(3).selected = true;
                }
                id_pie_chart.setData(pieModelList);
                id_pie_chart.invalidate();
            }
        });




    }

}
