package com.example.zhenning.zhenninga2ttwo;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.provider.Settings.Secure;

import java.io.FileWriter;


public class MainActivity extends AppCompatActivity {

    private String myIMEI = "";
    private String myAdnroidID = "";

    Button btnGetID;
    TextView viewIMEI;
    TextView viewAndroidID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetID = (Button)findViewById(R.id.btnIMEI);

        viewIMEI = (TextView)findViewById(R.id.IMEI_display);

        viewAndroidID = (TextView) findViewById(R.id.AndroidID_display);

        final TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        btnGetID.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                myIMEI = telephonyManager.getDeviceId();

                viewIMEI.setText("IMEI: " + myIMEI);

                myAdnroidID = Secure.getString(getBaseContext().getContentResolver(), Secure.ANDROID_ID);

                viewAndroidID.setText("Android ID:" + myAdnroidID);

                new AsyncTask<String, String, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                        try {
                            FileWriter fw = new FileWriter(path + "/androidID.txt");
                            fw.write("The IMEI is " + myIMEI + "\n");
                            fw.write("The Android ID is " + myAdnroidID + "\n");
                            fw.flush();
                            fw.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        TextView textView = ((TextView) findViewById(R.id.write_done));
                        textView.setText("Write Done.");
                    }
                };
            }
        });


    }


}