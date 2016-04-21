package com.example.zhenning.zhenninga2tfour;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.Log;
import com.example.zhenning.zhenninga2tfour.wifiChangedBroadcastReceiver;
/**
 * Created by zhenning on 21/04/16.
 */
public class wifiService extends Service{


    private final String TAG = "wifiService";

    public wifiService(){
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void onCreate() {
        Log.i(TAG, "Service onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                IntentFilter intentFilter = new IntentFilter();

                wifiChangedBroadcastReceiver wifiChanged = new wifiChangedBroadcastReceiver();

                intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);

                registerReceiver( wifiChanged, intentFilter );

            }
        }).start();

        return 0;
    }

}
