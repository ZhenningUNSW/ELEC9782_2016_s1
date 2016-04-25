package com.example.zhenning.zhenninga2tfour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(MainActivity.this, wifiService.class);

        startService(intent);

        Log.i("MainActivity", "Service Started!\n");
    }
}
