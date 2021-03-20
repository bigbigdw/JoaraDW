package Bigbigdw.JoaraDW.Main;

import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Main_BookData {

    ArrayList<Main_BookListData_A> items = new ArrayList<>();
    public ArrayList<Main_BookListData_A> getData(String API_URL, String ETC) {

        AsyncTask.execute(new Runnable() {
            String result = null;
            String API = "https://api.joara.com";
            String API_KEY = "?api_key=mw_8ba234e7801ba288554ca07ae44c7";
            String VER = "&ver=2.6.3";
            String DEVICE = "&device=mw";
            String DEVICE_ID = "&deviceuid=5127d5951c983034a16980c8a893ac99d16dbef988ee36882b793aa14ad33604";
            String DEVICE_TOKEN = "&devicetoken=mw";

            @Override
            public void run() {
                try {
                    URL url = new URL(API + API_URL + API_KEY + VER + DEVICE + DEVICE_ID + DEVICE_TOKEN + ETC);
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

                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray flag = jsonObject.getJSONArray("books");


                    for (int i = 0; i < flag.length(); i++) {
                        JSONObject jo = flag.getJSONObject(i);

                        String BookImg = jo.getString("book_img");
                        String Title = jo.getString("subject");
                        String Writer = jo.getString("writer_name");
                        String IsAdult = jo.getString("is_adult");
                        String IsFinish = jo.getString("is_finish");
                        String IsPremium = jo.getString("is_premium");
                        String IsNobless = jo.getString("is_nobless");
                        String Intro = jo.getString("intro");
                        String IsFav = jo.getString("is_favorite");

                        items.add(new Main_BookListData_A(Writer, Title, BookImg, IsAdult, IsFinish, IsPremium, IsNobless, Intro, IsFav));
                    }
                }
                catch (Exception e) {
                    Log.e("REST_API", "GET method failed: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        return items;
    }
}
