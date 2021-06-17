package bigbigdw.joaradw.main;

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

public class MainBookDataWebtoon {
    ArrayList<MainBookListData> items = new ArrayList<>();

    public ArrayList<MainBookListData> getData(String apiUrl, String etc, RequestQueue queue, LinearLayout wrap) {

        String resultURL = HELPER.API + apiUrl + HELPER.ETC + etc;

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, resultURL, null, response -> {
            try {
                JSONArray flag = response.getJSONArray("webtoons");

                for (int i = 0; i < flag.length(); i++) {
                    JSONObject jo = flag.getJSONObject(i);

                    String bookImg = jo.getString("webtoon_img");
                    String title = jo.getString("webtoon_title");

                    items.add(new MainBookListData("", title, bookImg, "", "", "", "", "", "", "", "", "", 0, "", "", ""));
                }

                wrap.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });
        queue.add(jsonRequest);
        return items;
    }
}