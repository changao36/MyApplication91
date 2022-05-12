package com.test.myapplication91;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



public class ListenerActivity extends Activity {

    private TextView textView0, textView1, textView2, textView3;
    private Button button1;
    private LinearLayout linearLayout;
    String maxMemory = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listener);

        maxMemory = getResources().getString(R.string.current_memory);
        textView0 = findViewById(R.id.text1);
        textView1 = findViewById(R.id.text2);
        textView2 = findViewById(R.id.text3);
        textView3 = findViewById(R.id.text4);
        linearLayout = findViewById(R.id.linearlayout);
        button1 = findViewById(R.id.check_if);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getScreenBrightness(ListenerActivity.this) == 15 && getNetworkState() == true) {
                    Toast.makeText(ListenerActivity.this, "系统设置与预设值一样", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ListenerActivity.this, "系统设置发生改变", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button button2 = findViewById(R.id.check_memory);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click();
            }
        });
    }

    public void click() {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        float maxMemory1 = (float) (Runtime.getRuntime().maxMemory() * 1.0 / (1024 * 1024));
        textView1.setText("最大分配内存为:" + maxMemory1);
        float totalMemory = (float) (Runtime.getRuntime().totalMemory() * 1.0 / (1024 * 1024));
        textView2.setText("当前分配的总内存为:" + totalMemory);
        float freeMemory = (float) (Runtime.getRuntime().freeMemory() * 1.0 / (1024 * 1024));
        textView3.setText("剩余内存为:" + freeMemory);
        TextView textView = new TextView(ListenerActivity.this);
        textView.setText("系统剩余内存为：" + String.valueOf(memoryInfo.availMem / (1024 * 1024)) + "MB");
        linearLayout.addView(textView);

        System.out.println(getScreenBrightness(ListenerActivity.this));
    }

    public static int getScreenBrightness(Activity activity) {
        int value = 0;
        ContentResolver cr = activity.getContentResolver();
        try {
            value = Settings.System.getInt(cr, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {

        }
        return value;
    }

    public boolean getNetworkState() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);//使用getSystemService得到ConnectivityManager实例
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo.isAvailable();
    }

}