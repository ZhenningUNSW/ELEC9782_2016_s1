package com.example.zhenning.zhenninga2tthree;

import android.app.ApplicationErrorReport;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int brightness;

    private ContentResolver cResolver;

    private Window window;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("receiver","received something");

            cResolver = getContentResolver();

            window = getWindow();

            TextView textView = (TextView) findViewById(R.id.diplayZone);

            textView.setText("The phone is charging!\n");

            try {

                Settings.System.putInt(cResolver,
                        Settings.System.SCREEN_BRIGHTNESS_MODE,
                        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);

                brightness = Settings.System.getInt(cResolver,
                                Settings.System.SCREEN_BRIGHTNESS);

                brightness = 255;

                Settings.System.putInt(cResolver,
                        Settings.System.SCREEN_BRIGHTNESS,
                        brightness);

            }
            catch (Settings.SettingNotFoundException e) {

                //Throw an error case it couldn't be retrieved
                Log.e("Error", "Cannot access system brightness");

                e.printStackTrace();
            }



        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // finished

        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction(BatteryManager.ACTION_CHARGING);

        registerReceiver(broadcastReceiver, intentFilter);

    }
}
