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

import bigbigdw.joaradw.etc.HELPER;

public class MainMoreEventData {
    ArrayList<MainMoreListData> items = new ArrayList<>();

    public ArrayList<MainMoreListData> getData(RequestQueue queue, LinearLayout eventList) {

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, HELPER.API + "/v1/board/event.joa" + HELPER.ETC + " &show_type=android&event_type=normal&offset=20&page=1", null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("data");

                for (int i = 0; i < 5; i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    String title = jo.getString("title");
                    String startDate = jo.getString("s_date");
                    String date = startDate.substring(2, 4) + '.' + startDate.substring(4, 6) + '.' + startDate.substring(6, 8);
                    items.add(new MainMoreListData(title, date, "", ""));
                    eventList.setVisibility(View.VISIBLE);
                    Log.d("EVENT", "성공!");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d("EVENT", " 접속 실패"));
        queue.add(jsonRequest);

        return items;
    }
}



