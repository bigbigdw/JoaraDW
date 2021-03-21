
package Bigbigdw.JoaraDW.Main;

import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

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

    public ArrayList<Main_BookListData_A> getData(String API_URL, String ETC, RequestQueue queue) {
        String API = "https://api.joara.com";
        String API_KEY = "?api_key=mw_8ba234e7801ba288554ca07ae44c7";
        String VER = "&ver=2.6.3";
        String DEVICE = "&device=mw";
        String DEVICE_ID = "&deviceuid=5127d5951c983034a16980c8a893ac99d16dbef988ee36882b793aa14ad33604";
        String DEVICE_TOKEN = "&devicetoken=mw";
        String ResultURL = API + API_URL + API_KEY + VER + DEVICE + DEVICE_ID + DEVICE_TOKEN + ETC;

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, ResultURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray flag = response.getJSONArray("books");

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

                    System.out.println("1성공!");
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("2실패!");
                }
            }
        }, error -> {

        });
        queue.add(jsonRequest);
        return items;
    }
}
