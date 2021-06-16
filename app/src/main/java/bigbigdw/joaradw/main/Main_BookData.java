
package bigbigdw.joaradw.main;

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

import bigbigdw.joaradw.etc.HELPER;
import bigbigdw.joaradw.R;

public class Main_BookData {

    ArrayList<Main_BookListData> items = new ArrayList<>();

    public ArrayList<Main_BookListData> getData(String API_URL, String ETC, RequestQueue queue, LinearLayout Wrap, String Type) {
        String ResultURL = HELPER.API + API_URL + HELPER.ETC + ETC;
        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, ResultURL, null, response -> {
            try {
                int Length;
                int BookBestRank = 0;
                int initNum = 0;
                String isBuff = null;

                JSONArray flag = response.getJSONArray("books");

                if (Type.equals("BEST")) {
                    Length = Math.min(flag.length(), 5);
                    initNum = 2;
//                    Length = Math.min(flag.length(), 3);
                } else {
                    Length = flag.length();
                }

                for (int i = initNum; i < Length; i++) {
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
                    String BookCode = jo.getString("book_code");
                    String BookCategory = jo.getString("category_ko_name");
                    String BestViewed = jo.getString("cnt_page_read");
                    String BestFav = jo.getString("cnt_favorite");
                    String BestRecommend = jo.getString("cnt_recom");

                    if (i == initNum) {
                        BookBestRank = R.drawable.icon_best_1;
                    } else if (i == initNum + 1) {
                        BookBestRank = R.drawable.icon_best_2;
                    } else if (i == initNum + 2) {
                        BookBestRank = R.drawable.icon_best_3;
                    } else if (i == initNum + 3) {
                        BookBestRank = R.drawable.icon_best_4;
                    } else if (i == initNum + 4) {
                        BookBestRank = R.drawable.icon_best_5;
                    } else if (i == initNum + 5) {
                        BookBestRank = R.drawable.icon_best_6;
                    } else if (i == initNum + 6) {
                        BookBestRank = R.drawable.icon_best_7;
                    } else if (i == initNum + 7) {
                        BookBestRank = R.drawable.icon_best_8;
                    } else {
                        BookBestRank = R.drawable.icon_best_9;
                    }

                    items.add(new Main_BookListData(Writer, Title, BookImg, IsAdult, IsFinish, IsPremium, IsNobless, Intro, IsFav, BestViewed, BestFav, BestRecommend, BookBestRank, "", BookCode, BookCategory));
                    Wrap.setVisibility(View.VISIBLE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Wrap.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Main_BookData", "에러!");
            }
        });
        queue.add(jsonRequest);
        return items;
    }
}
