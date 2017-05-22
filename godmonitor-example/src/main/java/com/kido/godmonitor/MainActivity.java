package com.kido.godmonitor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.kido.godmonitor.weaving.GMonitor;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "kido";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doAction1();
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doAction2();
            }
        });
    }

    @GMonitor
    private void doAction1() {
        Log.d(TAG, "you do the action111111.");
    }

    @GMonitor
    private void doAction2() {
        Log.d(TAG, "you do the action222222.");
    }
}
