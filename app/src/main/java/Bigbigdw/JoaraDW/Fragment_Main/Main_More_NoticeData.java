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
import java.util.Queue;

import Bigbigdw.JoaraDW.Etc.HELPER;
import Bigbigdw.JoaraDW.Etc.Popup;
import Bigbigdw.JoaraDW.Main.Main_BookListData_A;

public class Main_More_NoticeData {
    ArrayList<Main_More_ListData> items = new ArrayList<>();

    public ArrayList<Main_More_ListData> getData(RequestQueue queue, LinearLayout NoticeList) {

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, "https://api.joara.com/v1/board/notice_list.joa?api_key=mw_8ba234e7801ba288554ca07ae44c7&ver=2.6.3&device=mw&deviceuid=5127d5951c983034a16980c8a893ac99d16dbef988ee36882b793aa14ad33604&devicetoken=mw&board=&page=1&offset=20", null, response -> {
            try {
                JSONArray JSONArray = response.getJSONArray("notices");

                for (int i = 0; i < 5; i++) {
                    JSONObject jo = JSONArray.getJSONObject(i);

                    String Title = jo.getString("title");
                    String StartDate = jo.getString("created");
                    items.add(new Main_More_ListData(Title, StartDate));
                    System.out.println("HELLO" + items);
                    NoticeList.setVisibility(View.VISIBLE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Log.d("NOTICE", "연결 실패");
        });
        queue.add(jsonRequest);
        return items;
    };
}



