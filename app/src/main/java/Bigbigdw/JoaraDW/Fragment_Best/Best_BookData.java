package Bigbigdw.JoaraDW.Fragment_Best;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Bigbigdw.JoaraDW.Etc.HELPER;
import Bigbigdw.JoaraDW.Main.Main_BookListData;
import Bigbigdw.JoaraDW.R;

public class Best_BookData {

    ArrayList<Main_BookListData> items = new ArrayList<>();

    public ArrayList<Main_BookListData> getData(String API_URL, String ETC, RequestQueue queue, LinearLayout Wrap) {
        String ResultURL = HELPER.API + API_URL + HELPER.ETC + ETC;

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, ResultURL, null, response -> {
            try {
                JSONArray flag = response.getJSONArray("books");

                int Length = Math.min(flag.length(), 3);

                for (int i = 0; i < Length; i++) {
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
                    String BestCount = jo.getString("cnt_best");
                    String BestViewed = jo.getString("cnt_page_read");
                    String BestFav = jo.getString("cnt_favorite");
                    String BestRecommend = jo.getString("cnt_recom");
                    Integer BookBestRank = 0;

                    if(i == 0){
                       BookBestRank = R.drawable.icon_best_1;
                    } else if (i == 1){
                        BookBestRank = R.drawable.icon_best_2;
                    } else {
                        BookBestRank = R.drawable.icon_best_3;
                    }

                    items.add(new Main_BookListData(Writer, Title, BookImg, IsAdult, IsFinish, IsPremium, IsNobless, Intro, IsFav,BestCount,BestViewed,BestFav,BestRecommend,BookBestRank,"",""));
                    Wrap.setVisibility(View.VISIBLE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Best_BookData", "에러!");
            }
        });
        queue.add(jsonRequest);
        return items;
    }
}
