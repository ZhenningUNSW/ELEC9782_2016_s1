package com.example.zhenning.zhenninga2tfour;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;

import java.io.FileWriter;
import java.util.Date;

/**
 * Created by zhenning on 21/04/16.
 */
public class onBootBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent();

        i.setClass(context, wifiService.class);

        context.startService(i);

        new AsyncTask<String, String, String>(){

            @Override
            protected String doInBackground(String... params) {

                String path = Environment.getExternalStorageDirectory().getAbsolutePath();

                try {

                    FileWriter fw = new FileWriter(path + "/OnbootService.txt");

                    Date d = new Date();

                    CharSequence s  = DateFormat.format("EEEE, MMMM d, yyyy ", d.getTime());

                    fw.write("---Onboot services---\n");

                    fw.write("The time is : ");

                    fw.write(s.toString());

                    fw.write("\n");

                    fw.flush();

                    fw.close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {

                Log.d("Onboot services","Services write log file finished!\n");

            }

        }.execute();
    }

}
