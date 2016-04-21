package com.example.zhenning.zhenninga2tfour;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.DateFormat;
import android.util.Log;

import java.util.Date;


/**
 * Created by zhenning on 21/04/16.
 */
public class wifiChangedBroadcastReceiver extends BroadcastReceiver{


    private final String TAG = "WifiReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "got something");

        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);

        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        Date d = new Date();

        CharSequence s  = DateFormat.format("EEEE, MMMM d, yyyy ", d.getTime());

        Log.d(TAG, "The time is : " + s.toString() + "\n");

        if (info.isConnected()){

            Log.d(TAG, "The Wifi info is : " + wifiInfo.toString() + "\n");

            Log.d(TAG, "The Connection Type is : " + info.getType() + "\n");

            Log.d(TAG, "The SSID is : " + wifiInfo.getSSID() + "\n");

            Log.d(TAG, "The BSSID is : " + wifiInfo.getBSSID() + "\n");

        }
        else{

            Log.d(TAG, "The network info is : " + info.toString() + "\n");

            Log.d(TAG, "The Connection Type is : " + info.getType() + "\n");

        }


    }

}
