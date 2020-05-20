package com.wifieye.akechartdemo.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.wifieye.akechartdemo.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main2Activity extends AppCompatActivity {

    private static final String TAG = "Main2Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        String url = "https://apphtml.wikifx.com/mobile/download?languageCode=zh-cn&countryCode=cn" +
                "&qrid=d7a6d632-6721-44e7-8165-43004ea7c12f&type=0&app=usersite";

        String paStr="&qrid=(\\w|-)*";//\|.html
        Pattern compile = Pattern.compile(paStr);
        Matcher matcher = compile.matcher(url);
        if (matcher.find()) {
            String group = matcher.group();

            Log.e(TAG, "onCreate: "+group );
        }else {

            Log.e(TAG, "onCreate: "+false );
        }

    }
}
