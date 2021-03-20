package Bigbigdw.JoaraDW.Main;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public interface Main_API {

    static void getJSON(String API_URL, String ETC) {

        AsyncTask.execute(new Runnable() {
            String result = null;
            String API = "https://api.joara.com";
//          String API_URL = "/v1/banner/main_popup.joa";
            String API_KEY = "?api_key=mw_8ba234e7801ba288554ca07ae44c7";
            String VER = "&ver=2.6.3";
            String DEVICE = "&device=mw";
            String DEVICE_ID = "&deviceuid=5127d5951c983034a16980c8a893ac99d16dbef988ee36882b793aa14ad33604";
            String DEVICE_TOKEN = "&devicetoken=mw";
//          String ETC = "&banner_id=15967";

            @Override
            public void run() {
                try {
                    URL url = new URL("https://api.joara.com/v1/home/list.joa?api_key=mw_8ba234e7801ba288554ca07ae44c7&device=mw&deviceuid=5127d5951c983034a16980c8a893ac99d16dbef988ee36882b793aa14ad33604&devicetoken=mw&token=&ver=2.6.3&page=1&section_mode=todaybest&store=nobless&orderby=cnt_best&show_type=home&category=&offset=10");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    InputStream is = conn.getInputStream();

                    StringBuilder builder = new StringBuilder();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }

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
