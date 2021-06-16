package bigbigdw.joaradw;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bigbigdw.joaradw.etc.API;
import bigbigdw.joaradw.etc.HELPER;
import bigbigdw.joaradw.main.Main_BookListAdapter_C;
import bigbigdw.joaradw.main.Main_BookListData;

public interface BookPagination {

    static void loginCheck(RequestQueue queue, String token, TextView bookFavCoverText) {
        String resultURL = HELPER.API + API.USER_TOKEN_CHECK_JOA + HELPER.ETC + token;

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, resultURL, null, response -> {

            try {
                if (response.getString("status").equals("1")) {
                    bookFavCoverText.setText("작품을 불러오는 중입니다...");
                } else {
                    bookFavCoverText.setText("로그인이 필요합니다");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d("LoginCheck", "에러!"));

        queue.add(jsonRequest);
    }

    static void populateDataFav(String apiUrl, String etc, RequestQueue queue, LinearLayout wrap, ArrayList<Main_BookListData> items, LinearLayout Cover, String Type) {
        String resultURL = HELPER.API + apiUrl + HELPER.ETC + etc;

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, resultURL, null, response -> {
            try {
                    JSONArray flag = response.getJSONArray("books");

                    for (int i = 0; i < flag.length(); i++) {
                        JSONObject jo = flag.getJSONObject(i);

                        String bookImg = jo.getString("book_img");
                        String title = jo.getString("subject");
                        String writer = jo.getString("writer_name");
                        String isAdult = jo.getString("is_adult");
                        String isFinish = jo.getString("is_finish");
                        String isPremium = jo.getString("is_premium");
                        String isNobless = jo.getString("is_nobless");
                        String intro = jo.getString("intro");
                        String isFavorite = jo.getString("is_favorite");
                        String bookCode = jo.getString("book_code");
                        String categoryKoName = jo.getString("category_ko_name");
                        String cntPageRead = jo.getString("cnt_page_read");
                        String cntFavorite = jo.getString("cnt_favorite");
                        String cntRecom = jo.getString("cnt_recom");


                        if(Type.equals("Fav")){
                            items.add(new Main_BookListData(writer, title, bookImg, isAdult, isFinish, isPremium, isNobless, intro, isFavorite,  cntPageRead, cntFavorite, cntRecom, 0, "", bookCode,categoryKoName));
                        }else {
                            String historySortno = jo.getString("history_sortno");
                            items.add(new Main_BookListData(writer, title, bookImg, isAdult, isFinish, isPremium, isNobless, intro, isFavorite,  cntPageRead, cntFavorite, cntRecom, 0, historySortno, bookCode,categoryKoName));
                        }

                        Cover.setVisibility(View.GONE);
                        wrap.setVisibility(View.VISIBLE);
                    }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d("populateDataFav", "에러!"));

        queue.add(jsonRequest);
    }


    static void populateData(String API_URL, String ETC, RequestQueue queue, LinearLayout Wrap, ArrayList<Main_BookListData> items, LinearLayout Cover, LinearLayout Blank, String Type) {
        String ResultURL = HELPER.API + API_URL + HELPER.ETC + ETC;

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, ResultURL, null, response -> {

            try {
                JSONArray flag = response.getJSONArray("books");

                if(flag.length() == 0){
                    Blank.setVisibility(View.VISIBLE);
                } else {
                    Blank.setVisibility(View.GONE);
                }

                for (int i = 0; i < flag.length(); i++) {
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

                    Integer BookBestRank = 0;

                    if(i == 0){
                        BookBestRank = R.drawable.icon_best_1;
                    } else if (i == 1){
                        BookBestRank = R.drawable.icon_best_2;
                    } else if (i == 2) {
                        BookBestRank = R.drawable.icon_best_3;
                    } else if (i == 3) {
                        BookBestRank = R.drawable.icon_best_4;
                    } else if (i == 4) {
                        BookBestRank = R.drawable.icon_best_5;
                    } else if (i == 5) {
                        BookBestRank = R.drawable.icon_best_6;
                    } else if (i == 6) {
                        BookBestRank = R.drawable.icon_best_7;
                    } else if (i == 7) {
                        BookBestRank = R.drawable.icon_best_8;
                    } else if  (i == 8) {
                        BookBestRank = R.drawable.icon_best_9;
                    } 

                    items.add(new Main_BookListData(Writer, Title, BookImg, IsAdult, IsFinish, IsPremium, IsNobless, Intro, IsFav,BestViewed,BestFav,BestRecommend,BookBestRank,"1",BookCode,BookCategory));
                    Cover.setVisibility(View.GONE);
                    Wrap.setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d("Book_Pagination", "에러!"));

        queue.add(jsonRequest);
    }

    static void ScrollListener(String API, RequestQueue queue, LinearLayout Wrap, ArrayList<Main_BookListData> items, Main_BookListAdapter_C Adpater, RecyclerView recyclerView, String ETC) {

        final boolean[] isLoading = {false};
        final int[] Page = {2};

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading[0]) {
                    if (layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == items.size() - 1) {

                        String ResultURL = HELPER.API + API + HELPER.ETC + ETC + "&page=" + Page[0];
                        Log.d("ScrollListener",ResultURL);

                        items.add(null);
                        Adpater.notifyItemInserted(items.size() - 1);

                        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, ResultURL, null, response -> {

                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                items.remove(items.size() - 1);
                                int scrollPosition = items.size();
                                Adpater.notifyItemRemoved(scrollPosition);

                                try {
                                    JSONArray flag = response.getJSONArray("books");

                                    for (int i = 0; i < flag.length(); i++) {
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
                                        items.add(new Main_BookListData(Writer, Title, BookImg, IsAdult, IsFinish, IsPremium, IsNobless, Intro, IsFav,"","","",0,"",BookCode,BookCategory));
                                        Wrap.setVisibility(View.VISIBLE);
                                    }

                                    Adpater.notifyDataSetChanged();
                                    isLoading[0] = false;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }, 2000);
                        }, error -> Log.d("Book_Pagination", "에러!"));
                        queue.add(jsonRequest);
                        isLoading[0] = true;
                        Page[0]++;
                    }
                }
            }
        });
    }

    static void FavToggle(RequestQueue queue, String BookCode, String TOKEN) {
        String ResultURL = HELPER.API + "/v1/user/favorite.joa";

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, ResultURL, response -> {
            Log.d("Book_Pagination", response);
        }, error -> {
            Log.d("Book_Pagination", "실패!");
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("api_key", HELPER.API_KEY);
                params.put("ver", HELPER.VER);
                params.put("device", HELPER.DEVICE);
                params.put("deviceuid", HELPER.DEVICE_ID);
                params.put("devicetoken", HELPER.DEVICE_TOKEN);
                params.put("token", TOKEN);
                params.put("category", "0");
                params.put("book_code", BookCode);
                return params;
            }
        };
        queue.add(stringRequest);
    }

}
