package Bigbigdw.JoaraDW.Fragment_Main;

import android.content.res.AssetManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main_More_EventData {
    ArrayList<Main_More_ListData> items = new ArrayList<>();

    public ArrayList<Main_More_ListData> getData(RequestQueue queue, LinearLayout EventList) {

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, "https://api.joara.com/v1/board/event.joa?api_key=mw_8ba234e7801ba288554ca07ae44c7&ver=2.6.3&device=mw&deviceuid=5127d5951c983034a16980c8a893ac99d16dbef988ee36882b793aa14ad33604&devicetoken=mw&show_type=android&event_type=normal&offset=20&page=1", null, response -> {
            try {
                JSONArray JSONArray = response.getJSONArray("data");

                System.out.println(JSONArray);

                for (int i = 0; i < 5; i++) {
                    JSONObject jo = JSONArray.getJSONObject(i);
                    String Title = jo.getString("title");
                    String StartDate = jo.getString("s_date");
                    items.add(new Main_More_ListData(Title, StartDate));
                    EventList.setVisibility(View.VISIBLE);
                    Log.d("EVENT", "성공!");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Log.d("EVENT", " 접속 실패");
        });
        queue.add(jsonRequest);

        return items;
    }
}



