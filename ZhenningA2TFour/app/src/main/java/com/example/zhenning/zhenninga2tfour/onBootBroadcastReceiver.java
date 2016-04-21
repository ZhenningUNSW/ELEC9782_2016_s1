package com.example.zhenning.zhenninga2tfour;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by zhenning on 21/04/16.
 */
public class onBootBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent();

        i.setClass(context, wifiService.class);

        context.startService(i);
    }
}
