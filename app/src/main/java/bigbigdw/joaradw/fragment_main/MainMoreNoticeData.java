package bigbigdw.joaradw.fragment_main;

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
import java.util.List;

import bigbigdw.joaradw.etc.HELPER;

public class MainMoreNoticeData {
    ArrayList<MainMoreListData> items = new ArrayList<>();

    public List<MainMoreListData> getData(RequestQueue queue, LinearLayout noticeList) {
        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, HELPER.API + "/v1/board/notice_list.joa" + HELPER.ETC + "&board=&page=1&offset=20", null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("notices");

                for (int i = 0; i < 5; i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    String title = jo.getString("title");
                    String startDate = jo.getString("created");
                    String date = startDate.substring(2,4) + '.' + startDate.substring(4,6) + '.' + startDate.substring(6,8);
                    items.add(new MainMoreListData(title, date,"",""));
                    noticeList.setVisibility(View.VISIBLE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("NOTICE", "에러!!");
            }
        }, error -> Log.d("NOTICE", "연결 실패"));
        queue.add(jsonRequest);
        return items;
    }
}



