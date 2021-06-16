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

    public ArrayList<MainMoreListData> getData(RequestQueue queue, LinearLayout EventList) {

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, HELPER.API + "/v1/board/event.joa" + HELPER.ETC + " &show_type=android&event_type=normal&offset=20&page=1", null, response -> {
            try {
                JSONArray JSONArray = response.getJSONArray("data");

                for (int i = 0; i < 5; i++) {
                    JSONObject jo = JSONArray.getJSONObject(i);
                    String Title = jo.getString("title");
                    String StartDate = jo.getString("s_date");
                    String Date = StartDate.substring(2,4) + '.' + StartDate.substring(4,6) + '.' + StartDate.substring(6,8);
                    items.add(new MainMoreListData(Title, Date, "",""));
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



