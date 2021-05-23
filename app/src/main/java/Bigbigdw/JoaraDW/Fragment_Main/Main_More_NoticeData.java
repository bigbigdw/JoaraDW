package Bigbigdw.JoaraDW.Fragment_Main;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Bigbigdw.JoaraDW.Etc.HELPER;

public class Main_More_NoticeData {
    ArrayList<Main_More_ListData> items = new ArrayList<>();

    public ArrayList<Main_More_ListData> getData(RequestQueue queue, LinearLayout NoticeList) {
        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, HELPER.API + "/v1/board/notice_list.joa" + HELPER.ETC + "&board=&page=1&offset=20", null, response -> {
            try {
                JSONArray JSONArray = response.getJSONArray("notices");

                for (int i = 0; i < 5; i++) {
                    JSONObject jo = JSONArray.getJSONObject(i);
                    String Title = jo.getString("title");
                    String StartDate = jo.getString("created");
                    String Date = StartDate.substring(2,4) + '.' + StartDate.substring(4,6) + '.' + StartDate.substring(6,8);
                    items.add(new Main_More_ListData(Title, Date,"",""));
                    NoticeList.setVisibility(View.VISIBLE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("NOTICE", "에러!!");
            }
        }, error -> {
            Log.d("NOTICE", "연결 실패");
        });
        queue.add(jsonRequest);
        return items;
    }
}



