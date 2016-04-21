package com.example.zhenning.zhenninga2tfour;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;


/**
 * Created by zhenning on 21/04/16.
 */
public class wifiChangedBroadcastReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("WifiReceiver", "got something");

        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);



    }

}
