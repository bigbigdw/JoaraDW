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

import Bigbigdw.JoaraDW.Etc.HELPER;

public class Main_More_EventData {
    ArrayList<Main_More_ListData> items = new ArrayList<>();

    public ArrayList<Main_More_ListData> getData(RequestQueue queue, LinearLayout EventList) {

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, HELPER.API + "/v1/board/event.joa" + HELPER.ETC + " &show_type=android&event_type=normal&offset=20&page=1", null, response -> {
            try {
                JSONArray JSONArray = response.getJSONArray("data");

                for (int i = 0; i < 5; i++) {
                    JSONObject jo = JSONArray.getJSONObject(i);
                    String Title = jo.getString("title");
                    String StartDate = jo.getString("s_date");
                    String Date = StartDate.substring(2,4) + '.' + StartDate.substring(4,6) + '.' + StartDate.substring(6,8);
                    items.add(new Main_More_ListData(Title, Date, "",""));
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



