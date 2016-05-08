package com.example.jiang.zhenninga3tthree;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText editText;
    private TextView textViewConfidence;
    private TextView textViewReliable;
    private TextView textViewLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.getResult);
        editText = (EditText) findViewById(R.id.editText);
        textViewConfidence = (TextView) findViewById(R.id.resultConfidence);
        textViewReliable = (TextView) findViewById(R.id.resultReliable);
        textViewLanguage = (TextView) findViewById(R.id.resultLanguage);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()){

                    String wordtoCheck=editText.getText().toString();
                    if(wordtoCheck!=null)
                        new CallLanguageDetector().execute(wordtoCheck);
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(), "Please enter a valid word", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT);
                    toast.show();

                }
            }
        });

    }

    private class CallLanguageDetector extends AsyncTask<String, Void, String> {
        final String API_KEY = "47ad8da614483ac8b4e5aa90626770e2";
        InputStream inputStream = null;
        private String urlAddress = "http://ws.detectlanguage.com/0.2/detect?q=";


        @Override
        protected String doInBackground(String... params) {
            try {
                urlAddress = urlAddress + params[0] + "&key=" + API_KEY;
                URL url = new URL(urlAddress);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    Log.v("LanguageDetector", stringBuilder.toString());
                    return stringBuilder.toString();
                } finally {
                    connection.disconnect();
                }
            } catch (IOException e) {
                return null;
            }
        }

        protected void onPostExecute(String result){
            Log.v("onPostExecute",result);
            try {
                JSONObject reader = new JSONObject(result);
                JSONObject data = reader.getJSONObject("data");
                JSONArray detections = data.optJSONArray("detections");

                // Only parse the first result
                JSONObject detection = detections.getJSONObject(0);
                String languageDetected = detection.getString("language");
                String confidence = detection.optString("confidence").toString();
                String isReliable = detection.optString("isReliable").toString();
                languageDetected = "Language is : " + languageDetected;
                confidence = "Confidence is : " + confidence;
                isReliable = "Reliable? " + isReliable;
                textViewConfidence.setText(confidence);
                textViewReliable.setText(isReliable);
                textViewLanguage.setText(languageDetected);
            }
            catch (JSONException e){
                Log.v("LanguageDetector", "JSON throw a error!");
                e.printStackTrace();
            }

        }
    }
}
