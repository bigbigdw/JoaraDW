package Bigbigdw.JoaraDW.Test;

import android.os.AsyncTask;
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

    static void getJSON() {

        AsyncTask.execute(new Runnable() {
            String result = null;
            @Override
            public void run() {
                try {
                    // Open the connection
                    URL url = new URL("https://api.joara.com/v1/banner/main_popup.joa?api_key=mw_8ba234e7801ba288554ca07ae44c7&ver=2.6.3&device=mw&deviceuid=5127d5951c983034a16980c8a893ac99d16dbef988ee36882b793aa14ad33604&devicetoken=mw&banner_id=15967");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    InputStream is = conn.getInputStream();

                    // Get the stream
                    StringBuilder builder = new StringBuilder();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }

                    // Set the result
                    result = builder.toString();
                    System.out.println(result);
                }
                catch (Exception e) {
                    // Error calling the rest api
                    Log.e("REST_API", "GET method failed: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }
}
