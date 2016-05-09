package com.example.jiang.zhenninga3tfour;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;
import java.net.URL;


import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private ProgressDialog mProgressDialog;
    private TextView textView;
    private final String[] urlArray = {"https://www.dropbox.com/s/gjq0ivomwti4vmw/1.txt?dl=0",
                            "https://www.dropbox.com/s/6lolv2ph08wt657/2.txt?dl=0",
                            "https://www.dropbox.com/s/voz5vmnjkzojb14/3.txt?dl=0",
                            "https://www.dropbox.com/s/wv6hika6v2pfakz/4.txt?dl=0",
                            "https://www.dropbox.com/s/7jhtlzfuz2j42d8/5.txt?dl=0"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.btnStartDownload);
        textView = (TextView) findViewById(R.id.textViewDownload);
        mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.setMessage("DownloadProgress");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()){
                    new DownloadFilesTask().execute(urlArray);
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT);
                    toast.show();

                }
            }
        });

    }

    private class DownloadFilesTask  extends AsyncTask<String, Integer, Void> {
        private InputStream input = null;
        private OutputStream output = null;
        private HttpsURLConnection connection = null;
        private String TAG = "DownloadFilesTask";

        @Override
        protected Void doInBackground(String... params) {
            Log.v(TAG, "Do in background!");
            Log.v(TAG, params.toString());
            try {
                int count = params.length;
                for (int i = 0; i < count; i++) {
                    URL url = new URL(params[i]);
                    connection = (HttpsURLConnection)url.openConnection();
                    connection.connect();

                    input = connection.getInputStream();
                    String filename = "/sdcard/downloaded_" + Integer.toString(i);
                    output = new FileOutputStream(filename);
                    int countRead;
                    byte data[] = new byte[2048];
                    while ((countRead = input.read(data)) != -1){
                        if (isCancelled()) {
                            input.close();
                            return null;
                        }
                        output.write(data, 0, countRead);
                    }
                    publishProgress((int) ((i / (float) count) * 100));
                    // Escape early if cancel() is called
                    if (isCancelled()) break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            textView.setText("Download complete!");
            mProgressDialog.dismiss();
        }

        protected void onProgressUpdate(Integer... progress) {
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }
    }
}
