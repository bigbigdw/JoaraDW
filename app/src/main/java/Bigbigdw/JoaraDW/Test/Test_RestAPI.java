package Bigbigdw.JoaraDW.Test;

import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public interface Test_RestAPI {

    static void getJSON(String TAG, String REQUEST_URL, TextView textviewJSONText) {

        Thread thread = new Thread(() -> {

            String result;

            try {

                Log.d(TAG, REQUEST_URL);
                URL url = new URL(REQUEST_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(3000);
                httpURLConnection.setConnectTimeout(3000);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setUseCaches(false);
                httpURLConnection.connect();


                int responseStatusCode = httpURLConnection.getResponseCode();

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();
                httpURLConnection.disconnect();

                result = sb.toString().trim();


            } catch (Exception e) {
                result = e.toString();
            }

            textviewJSONText.setText(result);
        });
        thread.start();
    }
}
