
package com.wifieye.akechartdemo.otherchart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wifieye.akechartdemo.R;
import com.wifieye.akechartdemo.line.DiffContainerWaihuiTianyan;
import com.wifieye.akechartdemo.line.DiffDataBean;
import com.wifieye.akechartdemo.lineme.CloudBarView;
import com.wifieye.akechartdemo.lineme.CloudBrokenView;
import com.wifieye.akechartdemo.lineme.EquityDirectBean;
import com.wifieye.akechartdemo.lineme.MultContentLayout;
import com.wifieye.akechartdemo.lineme.PointValues;
import com.wifieye.akechartdemo.lineme.ProfitBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LineChartActivity extends AppCompatActivity {

    BrokenLineView2 chartView;
    List<String> xValues = new ArrayList<>();   //x轴数据集合
    List<Float> yValues = new ArrayList<>();  //y轴数据集合

    TextureView textureView;
    public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyyMMddHHmm");
    private DiffContainerWaihuiTianyan diffContainerWaihuiTianyan;

    public List<DiffDataBean.ContentBean.DBean> detailsList = new ArrayList<>();

    String data="{\"Content\":{\"code\":\"0361950064\",\"d\":[{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583683500,\"time\":3090005.0,\"timeString\":\"00:05\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583683800,\"time\":3090010.0,\"timeString\":\"00:10\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583684100,\"time\":3090015.0,\"timeString\":\"00:15\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583684400,\"time\":3090020.0,\"timeString\":\"00:20\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583684700,\"time\":3090025.0,\"timeString\":\"00:25\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583685000,\"time\":3090030.0,\"timeString\":\"00:30\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583685300,\"time\":3090035.0,\"timeString\":\"00:35\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583685600,\"time\":3090040.0,\"timeString\":\"00:40\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583685900,\"time\":3090045.0,\"timeString\":\"00:45\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583686200,\"time\":3090050.0,\"timeString\":\"00:50\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583686500,\"time\":3090055.0,\"timeString\":\"00:55\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583686800,\"time\":3090100.0,\"timeString\":\"01:00\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583687100,\"time\":3090105.0,\"timeString\":\"01:05\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583687400,\"time\":3090110.0,\"timeString\":\"01:10\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583687700,\"time\":3090115.0,\"timeString\":\"01:15\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583688000,\"time\":3090120.0,\"timeString\":\"01:20\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583688300,\"time\":3090125.0,\"timeString\":\"01:25\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583688600,\"time\":3090130.0,\"timeString\":\"01:30\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583688900,\"time\":3090135.0,\"timeString\":\"01:35\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583689200,\"time\":3090140.0,\"timeString\":\"01:40\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583689500,\"time\":3090145.0,\"timeString\":\"01:45\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583689800,\"time\":3090150.0,\"timeString\":\"01:50\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583690100,\"time\":3090155.0,\"timeString\":\"01:55\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583690400,\"time\":3090200.0,\"timeString\":\"02:00\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583690700,\"time\":3090205.0,\"timeString\":\"02:05\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583691000,\"time\":3090210.0,\"timeString\":\"02:10\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583691300,\"time\":3090215.0,\"timeString\":\"02:15\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583691600,\"time\":3090220.0,\"timeString\":\"02:20\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583691900,\"time\":3090225.0,\"timeString\":\"02:25\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583692200,\"time\":3090230.0,\"timeString\":\"02:30\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583692500,\"time\":3090235.0,\"timeString\":\"02:35\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583692800,\"time\":3090240.0,\"timeString\":\"02:40\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583693100,\"time\":3090245.0,\"timeString\":\"02:45\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583693400,\"time\":3090250.0,\"timeString\":\"02:50\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583693700,\"time\":3090255.0,\"timeString\":\"02:55\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583694000,\"time\":3090300.0,\"timeString\":\"03:00\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583694300,\"time\":3090305.0,\"timeString\":\"03:05\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583694600,\"time\":3090310.0,\"timeString\":\"03:10\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583694900,\"time\":3090315.0,\"timeString\":\"03:15\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583695200,\"time\":3090320.0,\"timeString\":\"03:20\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583695500,\"time\":3090325.0,\"timeString\":\"03:25\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583695800,\"time\":3090330.0,\"timeString\":\"03:30\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583696100,\"time\":3090335.0,\"timeString\":\"03:35\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583696400,\"time\":3090340.0,\"timeString\":\"03:40\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583696700,\"time\":3090345.0,\"timeString\":\"03:45\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583697000,\"time\":3090350.0,\"timeString\":\"03:50\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583697300,\"time\":3090355.0,\"timeString\":\"03:55\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583697600,\"time\":3090400.0,\"timeString\":\"04:00\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583697900,\"time\":3090405.0,\"timeString\":\"04:05\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583698200,\"time\":3090410.0,\"timeString\":\"04:10\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583698500,\"time\":3090415.0,\"timeString\":\"04:15\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583698800,\"time\":3090420.0,\"timeString\":\"04:20\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583699100,\"time\":3090425.0,\"timeString\":\"04:25\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583699400,\"time\":3090430.0,\"timeString\":\"04:30\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583699700,\"time\":3090435.0,\"timeString\":\"04:35\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583700000,\"time\":3090440.0,\"timeString\":\"04:40\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583700300,\"time\":3090445.0,\"timeString\":\"04:45\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583700600,\"time\":3090450.0,\"timeString\":\"04:50\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583700900,\"time\":3090455.0,\"timeString\":\"04:55\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"2.9\",\"t\":1583701200,\"time\":3090500.0,\"timeString\":\"05:00\",\"type\":0,\"value\":2.9},{\"last_xTmpT\":0.0,\"s\":\"10.8\",\"t\":1583701500,\"time\":3090505.0,\"timeString\":\"05:05\",\"type\":0,\"value\":10.8},{\"last_xTmpT\":0.0,\"s\":\"7.1\",\"t\":1583701800,\"time\":3090510.0,\"timeString\":\"05:10\",\"type\":0,\"value\":7.1},{\"last_xTmpT\":0.0,\"s\":\"5.7\",\"t\":1583702100,\"time\":3090515.0,\"timeString\":\"05:15\",\"type\":0,\"value\":5.7},{\"last_xTmpT\":0.0,\"s\":\"6.1\",\"t\":1583702400,\"time\":3090520.0,\"timeString\":\"05:20\",\"type\":0,\"value\":6.1},{\"last_xTmpT\":0.0,\"s\":\"4.3\",\"t\":1583702700,\"time\":3090525.0,\"timeString\":\"05:25\",\"type\":0,\"value\":4.3},{\"last_xTmpT\":0.0,\"s\":\"5.8\",\"t\":1583703000,\"time\":3090530.0,\"timeString\":\"05:30\",\"type\":0,\"value\":5.8},{\"last_xTmpT\":0.0,\"s\":\"6.5\",\"t\":1583703300,\"time\":3090535.0,\"timeString\":\"05:35\",\"type\":0,\"value\":6.5},{\"last_xTmpT\":0.0,\"s\":\"8.2\",\"t\":1583703600,\"time\":3090540.0,\"timeString\":\"05:40\",\"type\":0,\"value\":8.2},{\"last_xTmpT\":0.0,\"s\":\"4.3\",\"t\":1583703900,\"time\":3090545.0,\"timeString\":\"05:45\",\"type\":0,\"value\":4.3},{\"last_xTmpT\":0.0,\"s\":\"10.0\",\"t\":1583704200,\"time\":3090550.0,\"timeString\":\"05:50\",\"type\":0,\"value\":10.0},{\"last_xTmpT\":0.0,\"s\":\"7.8\",\"t\":1583704500,\"time\":3090555.0,\"timeString\":\"05:55\",\"type\":0,\"value\":7.8},{\"last_xTmpT\":0.0,\"s\":\"7.5\",\"t\":1583704800,\"time\":3090600.0,\"timeString\":\"06:00\",\"type\":0,\"value\":7.5},{\"last_xTmpT\":0.0,\"s\":\"9.9\",\"t\":1583705100,\"time\":3090605.0,\"timeString\":\"06:05\",\"type\":0,\"value\":9.9},{\"last_xTmpT\":0.0,\"s\":\"8.0\",\"t\":1583705400,\"time\":3090610.0,\"timeString\":\"06:10\",\"type\":0,\"value\":8.0},{\"last_xTmpT\":0.0,\"s\":\"2.0\",\"t\":1583705700,\"time\":3090615.0,\"timeString\":\"06:15\",\"type\":0,\"value\":2.0},{\"last_xTmpT\":0.0,\"s\":\"1.8\",\"t\":1583706000,\"time\":3090620.0,\"timeString\":\"06:20\",\"type\":0,\"value\":1.8},{\"last_xTmpT\":0.0,\"s\":\"1.9\",\"t\":1583706300,\"time\":3090625.0,\"timeString\":\"06:25\",\"type\":0,\"value\":1.9},{\"last_xTmpT\":0.0,\"s\":\"2.1\",\"t\":1583706600,\"time\":3090630.0,\"timeString\":\"06:30\",\"type\":0,\"value\":2.1},{\"last_xTmpT\":0.0,\"s\":\"2.0\",\"t\":1583706900,\"time\":3090635.0,\"timeString\":\"06:35\",\"type\":0,\"value\":2.0},{\"last_xTmpT\":0.0,\"s\":\"1.7\",\"t\":1583707200,\"time\":3090640.0,\"timeString\":\"06:40\",\"type\":0,\"value\":1.7},{\"last_xTmpT\":0.0,\"s\":\"1.8\",\"t\":1583707500,\"time\":3090645.0,\"timeString\":\"06:45\",\"type\":0,\"value\":1.8},{\"last_xTmpT\":0.0,\"s\":\"2.0\",\"t\":1583707800,\"time\":3090650.0,\"timeString\":\"06:50\",\"type\":0,\"value\":2.0},{\"last_xTmpT\":0.0,\"s\":\"2.2\",\"t\":1583708100,\"time\":3090655.0,\"timeString\":\"06:55\",\"type\":0,\"value\":2.2},{\"last_xTmpT\":0.0,\"s\":\"2.2\",\"t\":1583708400,\"time\":3090700.0,\"timeString\":\"07:00\",\"type\":0,\"value\":2.2},{\"last_xTmpT\":0.0,\"s\":\"2.0\",\"t\":1583708700,\"time\":3090705.0,\"timeString\":\"07:05\",\"type\":0,\"value\":2.0},{\"last_xTmpT\":0.0,\"s\":\"1.8\",\"t\":1583709000,\"time\":3090710.0,\"timeString\":\"07:10\",\"type\":0,\"value\":1.8},{\"last_xTmpT\":0.0,\"s\":\"2.1\",\"t\":1583709300,\"time\":3090715.0,\"timeString\":\"07:15\",\"type\":0,\"value\":2.1},{\"last_xTmpT\":0.0,\"s\":\"1.7\",\"t\":1583709600,\"time\":3090720.0,\"timeString\":\"07:20\",\"type\":0,\"value\":1.7},{\"last_xTmpT\":0.0,\"s\":\"1.6\",\"t\":1583709900,\"time\":3090725.0,\"timeString\":\"07:25\",\"type\":0,\"value\":1.6},{\"last_xTmpT\":0.0,\"s\":\"1.5\",\"t\":1583710200,\"time\":3090730.0,\"timeString\":\"07:30\",\"type\":0,\"value\":1.5},{\"last_xTmpT\":0.0,\"s\":\"1.4\",\"t\":1583710500,\"time\":3090735.0,\"timeString\":\"07:35\",\"type\":0,\"value\":1.4},{\"last_xTmpT\":0.0,\"s\":\"1.3\",\"t\":1583710800,\"time\":3090740.0,\"timeString\":\"07:40\",\"type\":0,\"value\":1.3},{\"last_xTmpT\":0.0,\"s\":\"1.5\",\"t\":1583711100,\"time\":3090745.0,\"timeString\":\"07:45\",\"type\":0,\"value\":1.5},{\"last_xTmpT\":0.0,\"s\":\"1.4\",\"t\":1583711400,\"time\":3090750.0,\"timeString\":\"07:50\",\"type\":0,\"value\":1.4},{\"last_xTmpT\":0.0,\"s\":\"1.4\",\"t\":1583711700,\"time\":3090755.0,\"timeString\":\"07:55\",\"type\":0,\"value\":1.4},{\"last_xTmpT\":0.0,\"s\":\"1.1\",\"t\":1583712000,\"time\":3090800.0,\"timeString\":\"08:00\",\"type\":0,\"value\":1.1},{\"last_xTmpT\":0.0,\"s\":\"1.1\",\"t\":1583712300,\"time\":3090805.0,\"timeString\":\"08:05\",\"type\":0,\"value\":1.1},{\"last_xTmpT\":0.0,\"s\":\"1.2\",\"t\":1583712600,\"time\":3090810.0,\"timeString\":\"08:10\",\"type\":0,\"value\":1.2},{\"last_xTmpT\":0.0,\"s\":\"1.2\",\"t\":1583712900,\"time\":3090815.0,\"timeString\":\"08:15\",\"type\":0,\"value\":1.2},{\"last_xTmpT\":0.0,\"s\":\"1.3\",\"t\":1583713200,\"time\":3090820.0,\"timeString\":\"08:20\",\"type\":0,\"value\":1.3},{\"last_xTmpT\":0.0,\"s\":\"1.3\",\"t\":1583713500,\"time\":3090825.0,\"timeString\":\"08:25\",\"type\":0,\"value\":1.3},{\"last_xTmpT\":0.0,\"s\":\"1.4\",\"t\":1583713800,\"time\":3090830.0,\"timeString\":\"08:30\",\"type\":0,\"value\":1.4},{\"last_xTmpT\":0.0,\"s\":\"1.4\",\"t\":1583714100,\"time\":3090835.0,\"timeString\":\"08:35\",\"type\":0,\"value\":1.4},{\"last_xTmpT\":0.0,\"s\":\"1.3\",\"t\":1583714400,\"time\":3090840.0,\"timeString\":\"08:40\",\"type\":0,\"value\":1.3},{\"last_xTmpT\":0.0,\"s\":\"1.3\",\"t\":1583714700,\"time\":3090845.0,\"timeString\":\"08:45\",\"type\":0,\"value\":1.3},{\"last_xTmpT\":0.0,\"s\":\"1.2\",\"t\":1583715000,\"time\":3090850.0,\"timeString\":\"08:50\",\"type\":0,\"value\":1.2},{\"last_xTmpT\":0.0,\"s\":\"1.2\",\"t\":1583715300,\"time\":3090855.0,\"timeString\":\"08:55\",\"type\":0,\"value\":1.2},{\"last_xTmpT\":0.0,\"s\":\"1.3\",\"t\":1583715600,\"time\":3090900.0,\"timeString\":\"09:00\",\"type\":0,\"value\":1.3},{\"last_xTmpT\":0.0,\"s\":\"1.2\",\"t\":1583715900,\"time\":3090905.0,\"timeString\":\"09:05\",\"type\":0,\"value\":1.2},{\"last_xTmpT\":0.0,\"s\":\"1.2\",\"t\":1583716200,\"time\":3090910.0,\"timeString\":\"09:10\",\"type\":0,\"value\":1.2},{\"last_xTmpT\":0.0,\"s\":\"1.3\",\"t\":1583716500,\"time\":3090915.0,\"timeString\":\"09:15\",\"type\":0,\"value\":1.3},{\"last_xTmpT\":0.0,\"s\":\"1.2\",\"t\":1583716800,\"time\":3090920.0,\"timeString\":\"09:20\",\"type\":0,\"value\":1.2},{\"last_xTmpT\":0.0,\"s\":\"1.2\",\"t\":1583717100,\"time\":3090925.0,\"timeString\":\"09:25\",\"type\":0,\"value\":1.2},{\"last_xTmpT\":0.0,\"s\":\"1.3\",\"t\":1583717400,\"time\":3090930.0,\"timeString\":\"09:30\",\"type\":0,\"value\":1.3},{\"last_xTmpT\":0.0,\"s\":\"1.3\",\"t\":1583717700,\"time\":3090935.0,\"timeString\":\"09:35\",\"type\":0,\"value\":1.3},{\"last_xTmpT\":0.0,\"s\":\"1.2\",\"t\":1583718000,\"time\":3090940.0,\"timeString\":\"09:40\",\"type\":0,\"value\":1.2},{\"last_xTmpT\":0.0,\"s\":\"1.6\",\"t\":1583718300,\"time\":3090945.0,\"timeString\":\"09:45\",\"type\":0,\"value\":1.6},{\"last_xTmpT\":0.0,\"s\":\"3.9\",\"t\":1583718600,\"time\":3090950.0,\"timeString\":\"09:50\",\"type\":0,\"value\":3.9},{\"last_xTmpT\":0.0,\"s\":\"6.1\",\"t\":1583718900,\"time\":3090955.0,\"timeString\":\"09:55\",\"type\":0,\"value\":6.1},{\"last_xTmpT\":0.0,\"s\":\"3.2\",\"t\":1583719200,\"time\":3091000.0,\"timeString\":\"10:00\",\"type\":0,\"value\":3.2},{\"last_xTmpT\":0.0,\"s\":\"2.7\",\"t\":1583719500,\"time\":3091005.0,\"timeString\":\"10:05\",\"type\":0,\"value\":2.7},{\"last_xTmpT\":0.0,\"s\":\"1.9\",\"t\":1583719800,\"time\":3091010.0,\"timeString\":\"10:10\",\"type\":0,\"value\":1.9},{\"last_xTmpT\":0.0,\"s\":\"2.3\",\"t\":1583720100,\"time\":3091015.0,\"timeString\":\"10:15\",\"type\":0,\"value\":2.3},{\"last_xTmpT\":0.0,\"s\":\"2.3\",\"t\":1583720400,\"time\":3091020.0,\"timeString\":\"10:20\",\"type\":0,\"value\":2.3},{\"last_xTmpT\":0.0,\"s\":\"1.9\",\"t\":1583720700,\"time\":3091025.0,\"timeString\":\"10:25\",\"type\":0,\"value\":1.9},{\"last_xTmpT\":0.0,\"s\":\"2.2\",\"t\":1583721000,\"time\":3091030.0,\"timeString\":\"10:30\",\"type\":0,\"value\":2.2},{\"last_xTmpT\":0.0,\"s\":\"1.5\",\"t\":1583721300,\"time\":3091035.0,\"timeString\":\"10:35\",\"type\":0,\"value\":1.5},{\"last_xTmpT\":0.0,\"s\":\"1.7\",\"t\":1583721600,\"time\":3091040.0,\"timeString\":\"10:40\",\"type\":0,\"value\":1.7},{\"last_xTmpT\":0.0,\"s\":\"1.8\",\"t\":1583721900,\"time\":3091045.0,\"timeString\":\"10:45\",\"type\":0,\"value\":1.8},{\"last_xTmpT\":0.0,\"s\":\"2.1\",\"t\":1583722200,\"time\":3091050.0,\"timeString\":\"10:50\",\"type\":0,\"value\":2.1},{\"last_xTmpT\":0.0,\"s\":\"1.9\",\"t\":1583722500,\"time\":3091055.0,\"timeString\":\"10:55\",\"type\":0,\"value\":1.9},{\"last_xTmpT\":0.0,\"s\":\"1.9\",\"t\":1583722800,\"time\":3091100.0,\"timeString\":\"11:00\",\"type\":0,\"value\":1.9},{\"last_xTmpT\":0.0,\"s\":\"2.3\",\"t\":1583723100,\"time\":3091105.0,\"timeString\":\"11:05\",\"type\":0,\"value\":2.3},{\"last_xTmpT\":0.0,\"s\":\"2.2\",\"t\":1583723400,\"time\":3091110.0,\"timeString\":\"11:10\",\"type\":0,\"value\":2.2},{\"last_xTmpT\":0.0,\"s\":\"2.0\",\"t\":1583723700,\"time\":3091115.0,\"timeString\":\"11:15\",\"type\":0,\"value\":2.0},{\"last_xTmpT\":0.0,\"s\":\"1.9\",\"t\":1583724000,\"time\":3091120.0,\"timeString\":\"11:20\",\"type\":0,\"value\":1.9},{\"last_xTmpT\":0.0,\"s\":\"3.1\",\"t\":1583724300,\"time\":3091125.0,\"timeString\":\"11:25\",\"type\":0,\"value\":3.1},{\"last_xTmpT\":0.0,\"s\":\"2.6\",\"t\":1583724600,\"time\":3091130.0,\"timeString\":\"11:30\",\"type\":0,\"value\":2.6},{\"last_xTmpT\":0.0,\"s\":\"2.0\",\"t\":1583724900,\"time\":3091135.0,\"timeString\":\"11:35\",\"type\":0,\"value\":2.0},{\"last_xTmpT\":0.0,\"s\":\"2.8\",\"t\":1583725200,\"time\":3091140.0,\"timeString\":\"11:40\",\"type\":0,\"value\":2.8},{\"last_xTmpT\":0.0,\"s\":\"1.8\",\"t\":1583725500,\"time\":3091145.0,\"timeString\":\"11:45\",\"type\":0,\"value\":1.8},{\"last_xTmpT\":0.0,\"s\":\"2.2\",\"t\":1583725800,\"time\":3091150.0,\"timeString\":\"11:50\",\"type\":0,\"value\":2.2},{\"last_xTmpT\":0.0,\"s\":\"2.0\",\"t\":1583726100,\"time\":3091155.0,\"timeString\":\"11:55\",\"type\":0,\"value\":2.0},{\"last_xTmpT\":0.0,\"s\":\"2.4\",\"t\":1583726400,\"time\":3091200.0,\"timeString\":\"12:00\",\"type\":0,\"value\":2.4},{\"last_xTmpT\":0.0,\"s\":\"2.3\",\"t\":1583726700,\"time\":3091205.0,\"timeString\":\"12:05\",\"type\":0,\"value\":2.3},{\"last_xTmpT\":0.0,\"s\":\"1.9\",\"t\":1583727000,\"time\":3091210.0,\"timeString\":\"12:10\",\"type\":0,\"value\":1.9},{\"last_xTmpT\":0.0,\"s\":\"1.9\",\"t\":1583727300,\"time\":3091215.0,\"timeString\":\"12:15\",\"type\":0,\"value\":1.9},{\"last_xTmpT\":0.0,\"s\":\"1.7\",\"t\":1583727600,\"time\":3091220.0,\"timeString\":\"12:20\",\"type\":0,\"value\":1.7},{\"last_xTmpT\":0.0,\"s\":\"1.7\",\"t\":1583727900,\"time\":3091225.0,\"timeString\":\"12:25\",\"type\":0,\"value\":1.7},{\"last_xTmpT\":0.0,\"s\":\"1.5\",\"t\":1583728200,\"time\":3091230.0,\"timeString\":\"12:30\",\"type\":0,\"value\":1.5},{\"last_xTmpT\":0.0,\"s\":\"1.6\",\"t\":1583728500,\"time\":3091235.0,\"timeString\":\"12:35\",\"type\":0,\"value\":1.6},{\"last_xTmpT\":0.0,\"s\":\"1.8\",\"t\":1583728800,\"time\":3091240.0,\"timeString\":\"12:40\",\"type\":0,\"value\":1.8},{\"last_xTmpT\":0.0,\"s\":\"1.6\",\"t\":1583729100,\"time\":3091245.0,\"timeString\":\"12:45\",\"type\":0,\"value\":1.6},{\"last_xTmpT\":0.0,\"s\":\"1.7\",\"t\":1583729400,\"time\":3091250.0,\"timeString\":\"12:50\",\"type\":0,\"value\":1.7},{\"last_xTmpT\":0.0,\"s\":\"1.7\",\"t\":1583729700,\"time\":3091255.0,\"timeString\":\"12:55\",\"type\":0,\"value\":1.7},{\"last_xTmpT\":0.0,\"s\":\"1.6\",\"t\":1583730000,\"time\":3091300.0,\"timeString\":\"13:00\",\"type\":0,\"value\":1.6},{\"last_xTmpT\":0.0,\"s\":\"1.8\",\"t\":1583730300,\"time\":3091305.0,\"timeString\":\"13:05\",\"type\":0,\"value\":1.8},{\"last_xTmpT\":0.0,\"s\":\"1.7\",\"t\":1583730600,\"time\":3091310.0,\"timeString\":\"13:10\",\"type\":0,\"value\":1.7},{\"last_xTmpT\":0.0,\"s\":\"1.8\",\"t\":1583730900,\"time\":3091315.0,\"timeString\":\"13:15\",\"type\":0,\"value\":1.8},{\"last_xTmpT\":0.0,\"s\":\"1.7\",\"t\":1583731200,\"time\":3091320.0,\"timeString\":\"13:20\",\"type\":0,\"value\":1.7},{\"last_xTmpT\":0.0,\"s\":\"1.7\",\"t\":1583731500,\"time\":3091325.0,\"timeString\":\"13:25\",\"type\":0,\"value\":1.7},{\"last_xTmpT\":0.0,\"s\":\"1.7\",\"t\":1583731800,\"time\":3091330.0,\"timeString\":\"13:30\",\"type\":0,\"value\":1.7},{\"last_xTmpT\":0.0,\"s\":\"1.6\",\"t\":1583732100,\"time\":3091335.0,\"timeString\":\"13:35\",\"type\":0,\"value\":1.6},{\"last_xTmpT\":0.0,\"s\":\"1.7\",\"t\":1583732400,\"time\":3091340.0,\"timeString\":\"13:40\",\"type\":0,\"value\":1.7},{\"last_xTmpT\":0.0,\"s\":\"1.5\",\"t\":1583732700,\"time\":3091345.0,\"timeString\":\"13:45\",\"type\":0,\"value\":1.5},{\"last_xTmpT\":0.0,\"s\":\"1.6\",\"t\":1583733000,\"time\":3091350.0,\"timeString\":\"13:50\",\"type\":0,\"value\":1.6},{\"last_xTmpT\":0.0,\"s\":\"1.6\",\"t\":1583733300,\"time\":3091355.0,\"timeString\":\"13:55\",\"type\":0,\"value\":1.6},{\"last_xTmpT\":0.0,\"s\":\"1.3\",\"t\":1583733600,\"time\":3091400.0,\"timeString\":\"14:00\",\"type\":0,\"value\":1.3},{\"last_xTmpT\":0.0,\"s\":\"1.6\",\"t\":1583733900,\"time\":3091405.0,\"timeString\":\"14:05\",\"type\":0,\"value\":1.6},{\"last_xTmpT\":0.0,\"s\":\"1.7\",\"t\":1583734200,\"time\":3091410.0,\"timeString\":\"14:10\",\"type\":0,\"value\":1.7},{\"last_xTmpT\":0.0,\"s\":\"1.5\",\"t\":1583734500,\"time\":3091415.0,\"timeString\":\"14:15\",\"type\":0,\"value\":1.5},{\"last_xTmpT\":0.0,\"s\":\"1.4\",\"t\":1583734800,\"time\":3091420.0,\"timeString\":\"14:20\",\"type\":0,\"value\":1.4},{\"last_xTmpT\":0.0,\"s\":\"1.3\",\"t\":1583735100,\"time\":3091425.0,\"timeString\":\"14:25\",\"type\":0,\"value\":1.3},{\"last_xTmpT\":0.0,\"s\":\"1.4\",\"t\":1583735400,\"time\":3091430.0,\"timeString\":\"14:30\",\"type\":0,\"value\":1.4},{\"last_xTmpT\":0.0,\"s\":\"1.6\",\"t\":1583735700,\"time\":3091435.0,\"timeString\":\"14:35\",\"type\":0,\"value\":1.6},{\"last_xTmpT\":0.0,\"s\":\"1.5\",\"t\":1583736000,\"time\":3091440.0,\"timeString\":\"14:40\",\"type\":0,\"value\":1.5},{\"last_xTmpT\":0.0,\"s\":\"1.6\",\"t\":1583736300,\"time\":3091445.0,\"timeString\":\"14:45\",\"type\":0,\"value\":1.6},{\"last_xTmpT\":0.0,\"s\":\"1.4\",\"t\":1583736600,\"time\":3091450.0,\"timeString\":\"14:50\",\"type\":0,\"value\":1.4},{\"last_xTmpT\":0.0,\"s\":\"1.5\",\"t\":1583736900,\"time\":3091455.0,\"timeString\":\"14:55\",\"type\":0,\"value\":1.5},{\"last_xTmpT\":0.0,\"s\":\"1.5\",\"t\":1583737200,\"time\":3091500.0,\"timeString\":\"15:00\",\"type\":0,\"value\":1.5},{\"last_xTmpT\":0.0,\"s\":\"1.5\",\"t\":1583737500,\"time\":3091505.0,\"timeString\":\"15:05\",\"type\":0,\"value\":1.5},{\"last_xTmpT\":0.0,\"s\":\"1.4\",\"t\":1583737800,\"time\":3091510.0,\"timeString\":\"15:10\",\"type\":0,\"value\":1.4},{\"last_xTmpT\":0.0,\"s\":\"1.8\",\"t\":1583738100,\"time\":3091515.0,\"timeString\":\"15:15\",\"type\":0,\"value\":1.8},{\"last_xTmpT\":0.0,\"s\":\"1.7\",\"t\":1583738400,\"time\":3091520.0,\"timeString\":\"15:20\",\"type\":0,\"value\":1.7},{\"last_xTmpT\":0.0,\"s\":\"1.8\",\"t\":1583738700,\"time\":3091525.0,\"timeString\":\"15:25\",\"type\":0,\"value\":1.8},{\"last_xTmpT\":0.0,\"s\":\"1.6\",\"t\":1583739000,\"time\":3091530.0,\"timeString\":\"15:30\",\"type\":0,\"value\":1.6},{\"last_xTmpT\":0.0,\"s\":\"1.6\",\"t\":1583739300,\"time\":3091535.0,\"timeString\":\"15:35\",\"type\":0,\"value\":1.6},{\"last_xTmpT\":0.0,\"s\":\"1.6\",\"t\":1583739600,\"time\":3091540.0,\"timeString\":\"15:40\",\"type\":0,\"value\":1.6},{\"last_xTmpT\":0.0,\"s\":\"1.5\",\"t\":1583739900,\"time\":3091545.0,\"timeString\":\"15:45\",\"type\":0,\"value\":1.5},{\"last_xTmpT\":0.0,\"s\":\"1.5\",\"t\":1583740200,\"time\":3091550.0,\"timeString\":\"15:50\",\"type\":0,\"value\":1.5},{\"last_xTmpT\":0.0,\"s\":\"1.6\",\"t\":1583740500,\"time\":3091555.0,\"timeString\":\"15:55\",\"type\":0,\"value\":1.6},{\"last_xTmpT\":0.0,\"s\":\"1.4\",\"t\":1583740800,\"time\":3091600.0,\"timeString\":\"16:00\",\"type\":0,\"value\":1.4},{\"last_xTmpT\":0.0,\"s\":\"1.3\",\"t\":1583741100,\"time\":3091605.0,\"timeString\":\"16:05\",\"type\":0,\"value\":1.3},{\"last_xTmpT\":0.0,\"s\":\"1.3\",\"t\":1583741400,\"time\":3091610.0,\"timeString\":\"16:10\",\"type\":0,\"value\":1.3},{\"last_xTmpT\":0.0,\"s\":\"1.3\",\"t\":1583741700,\"time\":3091615.0,\"timeString\":\"16:15\",\"type\":0,\"value\":1.3},{\"last_xTmpT\":0.0,\"s\":\"1.2\",\"t\":1583742000,\"time\":3091620.0,\"timeString\":\"16:20\",\"type\":0,\"value\":1.2},{\"last_xTmpT\":0.0,\"s\":\"1.2\",\"t\":1583742300,\"time\":3091625.0,\"timeString\":\"16:25\",\"type\":0,\"value\":1.2},{\"last_xTmpT\":0.0,\"s\":\"1.3\",\"t\":1583742600,\"time\":3091630.0,\"timeString\":\"16:30\",\"type\":0,\"value\":1.3},{\"last_xTmpT\":0.0,\"s\":\"1.3\",\"t\":1583742900,\"time\":3091635.0,\"timeString\":\"16:35\",\"type\":0,\"value\":1.3},{\"last_xTmpT\":0.0,\"s\":\"1.4\",\"t\":1583743200,\"time\":3091640.0,\"timeString\":\"16:40\",\"type\":0,\"value\":1.4},{\"last_xTmpT\":0.0,\"s\":\"1.3\",\"t\":1583743500,\"time\":3091645.0,\"timeString\":\"16:45\",\"type\":0,\"value\":1.3},{\"last_xTmpT\":0.0,\"s\":\"1.3\",\"t\":1583743800,\"time\":3091650.0,\"timeString\":\"16:50\",\"type\":0,\"value\":1.3},{\"last_xTmpT\":0.0,\"s\":\"1.3\",\"t\":1583744100,\"time\":3091655.0,\"timeString\":\"16:55\",\"type\":0,\"value\":1.3},{\"last_xTmpT\":0.0,\"s\":\"1.3\",\"t\":1583744400,\"time\":3091700.0,\"timeString\":\"17:00\",\"type\":0,\"value\":1.3}],\"n\":\"EURUSD\"},\"RequestId\":\"d8982bfd-69be-4036-a49e-21c363fa950d\",\"Timestamp\":\"2020-03-09T08:58:04Z\"}";

    private CloudBrokenView brokenView;

    String pointData="{\n" +
            "  \"ErrorCode\": 200,\n" +
            "  \"Data\": [\n" +
            "\t{\n" +
            "\t  \"CloseTime\": \"2019-08-13 06:39:41\",\n" +
            "\t  \"Equity\": \"86034.91\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t  \"CloseTime\": \"2019-07-31 09:57:25\",\n" +
            "\t  \"Equity\": \"84772.49\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t  \"CloseTime\": \"2019-07-31 09:49:05\",\n" +
            "\t  \"Equity\": \"84772.62\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t  \"CloseTime\": \"2019-07-31 09:14:50\",\n" +
            "\t  \"Equity\": \"84772.08\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t  \"CloseTime\": \"2019-07-18 05:57:31\",\n" +
            "\t  \"Equity\": \"9977.56\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t  \"CloseTime\": \"2019-07-17 03:04:55\",\n" +
            "\t  \"Equity\": \"10000.00\"\n" +
            "\t}\n" +
            "        ,{\n" +
            "\t  \"CloseTime\": \"2019-08-13 06:39:41\",\n" +
            "\t  \"Equity\": \"86034.91\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t  \"CloseTime\": \"2019-07-31 09:57:25\",\n" +
            "\t  \"Equity\": \"84772.49\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t  \"CloseTime\": \"2019-07-31 09:49:05\",\n" +
            "\t  \"Equity\": \"84772.62\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t  \"CloseTime\": \"2019-07-31 09:14:50\",\n" +
            "\t  \"Equity\": \"84772.08\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t  \"CloseTime\": \"2019-07-18 05:57:31\",\n" +
            "\t  \"Equity\": \"9977.56\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t  \"CloseTime\": \"2019-07-17 03:04:55\",\n" +
            "\t  \"Equity\": \"10000.00\"\n" +
            "\t}\n" +
            "  ],\n" +
            "  \"requestId\": \"4051ee70-df80-11e9-8c53-f9436fe2458d\"\n" +
            "}";

    String pD="{\n" +
            "  \"ErrorCode\": 200,\n" +
            "  \"Data\": [\n" +
            "\t{\n" +
            "\t  \"CloseTime\": \"2019-08-13 06:39:41\",\n" +
            "\t  \"Equity\": \"86034.91\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t  \"CloseTime\": \"2019-07-31 09:57:25\",\n" +
            "\t  \"Equity\": \"84772.49\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t  \"CloseTime\": \"2019-07-31 09:49:05\",\n" +
            "\t  \"Equity\": \"84772.62\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t  \"CloseTime\": \"2019-07-31 09:14:50\",\n" +
            "\t  \"Equity\": \"84772.08\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t  \"CloseTime\": \"2019-07-18 05:57:31\",\n" +
            "\t  \"Equity\": \"9977.56\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t  \"CloseTime\": \"2019-07-17 03:04:55\",\n" +
            "\t  \"Equity\": \"10000.00\"\n" +
            "\t}\n" +
            "        \n" +
            "  ],\n" +
            "  \"requestId\": \"4051ee70-df80-11e9-8c53-f9436fe2458d\"\n" +
            "}";

    String profitData="{\n" +
            "  \"ErrorCode\": 200,\n" +
            "  \"Data\": [\n" +
            "\t{\n" +
            "\t  \"Month\": \"2019-07\",\n" +
            "\t  \"Profit\": \"-5.53\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t  \"Month\": \"2019-08\",\n" +
            "\t  \"Profit\": \"-8.44\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t  \"Month\": \"2019-09\",\n" +
            "\t  \"Profit\": \"-8.26\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t  \"Month\": \"2019-10\",\n" +
            "\t  \"Profit\": \"-15.52\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t  \"Month\": \"2019-11\",\n" +
            "\t  \"Profit\": \"-6.89\"\n" +
            "\t}\n" +
            "  ],\n" +
            "  \"requestId\": \"897daa30-0f3c-11ea-8dea-6de3494e64fa\"\n" +
            "}";
    private CloudBarView cloudBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);

        initData();

        chartView = findViewById(R.id.customView1);
        // xy轴集合自己添加数据
        chartView.setxValues(xValues);
        chartView.setyValues(yValues);

        textureView=findViewById(R.id.textureView);

        diffContainerWaihuiTianyan=findViewById(R.id.diff_container);

        brokenView=findViewById(R.id.cloud_broken_view);

        cloudBarView = findViewById(R.id.cloud_bar_view);

        MultContentLayout multContentLayout=findViewById(R.id.multContentLayout);

        try {
            JSONObject jsonObject = new JSONObject(data);

            JSONObject content = jsonObject.getJSONObject("Content");

            JSONArray d = content.getJSONArray("d");
            for (int i = 0; i < d.length(); i++) {
                DiffDataBean.ContentBean.DBean dBean = new DiffDataBean.ContentBean.DBean();
                dBean.setS(d.getJSONObject(i).getString("s"));
                dBean.setT(d.getJSONObject(i).getInt("t"));
                dBean.setValue(d.getJSONObject(i).getDouble("value"));


                double time = Double.parseDouble(DATE_FORMAT_DATE.format(new java.util.Date((long) d.getJSONObject(i).getInt("t") * 1000L)));
                time = time % 1000000000d;
                dBean.setTime(time);

                detailsList.add(dBean);

            }

            diffContainerWaihuiTianyan.setData(detailsList);
            multContentLayout.setData(detailsList);

            NumberFormat instance = NumberFormat.getInstance();
            //brokenView
            EquityDirectBean equityDirectBean = new Gson().fromJson(pointData, EquityDirectBean.class);
            PointValues pointValues = new PointValues();
            List<EquityDirectBean.DataBean> beanData = equityDirectBean.getData();
            String []xVa=new String[beanData.size()];
            double [] yVa=new double[beanData.size()];
            for (int i = 0; i < beanData.size(); i++) {
                xVa[i]=beanData.get(i).getCloseTime();
                String equity = beanData.get(i).getEquity();

                instance.setMinimumFractionDigits(2);
                double y = instance.parse(equity).doubleValue();
                yVa[i]= y;
            }
            String[] xva2=new String[]{"1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月","13月","14月","15月"};
            double [] yva2=new double[]{-2323,-54756,-4345,-7656,-75,-885,-54,-343,-464,-756,-434,-56456,-45645,-34534,-34534};
//            String[] xva2=new String[]{"1月"};
//            double [] yva2=new double[]{0};
            pointValues.setxValues(xva2);
            pointValues.setyValues(yva2);
            brokenView.setPointValues(pointValues);

            String[] xva3=new String[]{"2019-03","2019-03","2019-03","2019-03","2019-03","2019-03","2019-03","2019-03","2019-03","2019-03"};
            double [] yva3=new double[]{-138.55,-962.25,-345,656,-75,-885,-54,-343,-464,-1556};

            PointValues pointValues2 = new PointValues();
            pointValues2.setxValues(xva3);
            pointValues2.setyValues(yva3);
            ProfitBean profitBean = new Gson().fromJson(profitData, ProfitBean.class);

            cloudBarView.setPointValues(pointValues2);


        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }




    }

    private void initData() {

        xValues.add("12.01");
        xValues.add("12.02");
        xValues.add("12.03");
        xValues.add("12.04");
        xValues.add("12.05");
        xValues.add("12.06");
        xValues.add("12.07");
        xValues.add("12.08");
        xValues.add("12.09");
        xValues.add("12.10");
        xValues.add("12.11");
        xValues.add("12.21");
        xValues.add("12.22");
        xValues.add("12.23");
        xValues.add("12.24");
        xValues.add("12.25");
        xValues.add("12.26");
        xValues.add("12.27");
        xValues.add("12.28");
        xValues.add("12.29");
        xValues.add("12.30");
        xValues.add("12.31");

        yValues.add(5f);
        yValues.add(14f);
        yValues.add(8f);
        yValues.add(12f);
        yValues.add(7f);
        yValues.add(17f);
        yValues.add(17f);
        yValues.add(17f);
        yValues.add(17f);
        yValues.add(17f);
        yValues.add(17f);


        yValues.add(5f);
        yValues.add(14f);
        yValues.add(8f);
        yValues.add(12f);
        yValues.add(7f);
        yValues.add(17f);
        yValues.add(19f);
        yValues.add(17f);
        yValues.add(20f);
        yValues.add(23f);
        yValues.add(17f);


    }

}
