package bigbigdw.joaradw;

import android.os.Handler;
import android.os.Looper;
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
import bigbigdw.joaradw.main.OLD_MainBookListAdapterC;
import bigbigdw.joaradw.main.OLD_MainBookListData;
import bigbigdw.joaradw.model.BookInfo;

public interface BookPagination {

    String BOOKS = "books";

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

    static void populateDataFav(String apiUrl, String etc, RequestQueue queue, LinearLayout wrap, ArrayList<OLD_MainBookListData> items, LinearLayout cover, String type) {
        String resultURL = HELPER.API + apiUrl + HELPER.ETC + etc;

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, resultURL, null, response -> {
            try {
                    JSONArray flag = response.getJSONArray(BOOKS);

                    for (int i = 0; i < flag.length(); i++) {
                        JSONObject jo = flag.getJSONObject(i);
                        BookInfo tempBookInfo = BookInfo.getParseData(jo);

                        if(type.equals("Fav")){
                            items.add(new OLD_MainBookListData(tempBookInfo.getWriter(), tempBookInfo.getTitle(), tempBookInfo.getBookImg(), tempBookInfo.getIsAdult(), tempBookInfo.getIsFinish(), tempBookInfo.getIsPremium(), tempBookInfo.getIsNobless(), tempBookInfo.getIntro(), tempBookInfo.getIsFavorite(),  tempBookInfo.getCntPageRead(), tempBookInfo.getCntFavorite(), tempBookInfo.getCntRecom(), 0, "", tempBookInfo.getBookCode(),tempBookInfo.getCategoryKoName(),tempBookInfo.getCtnChapter()));
                        }else {
                            items.add(new OLD_MainBookListData(tempBookInfo.getWriter(), tempBookInfo.getTitle(), tempBookInfo.getBookImg(), tempBookInfo.getIsAdult(), tempBookInfo.getIsFinish(), tempBookInfo.getIsPremium(), tempBookInfo.getIsNobless(), tempBookInfo.getIntro(), tempBookInfo.getIsFavorite(),  tempBookInfo.getCntPageRead(), tempBookInfo.getCntFavorite(), tempBookInfo.getCntRecom(), 0, tempBookInfo.getHistorySortno(), tempBookInfo.getBookCode(),tempBookInfo.getCategoryKoName(),tempBookInfo.getCtnChapter()));
                        }

                        cover.setVisibility(View.GONE);
                        wrap.setVisibility(View.VISIBLE);
                    }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d("populateDataFav", "에러!"));

        queue.add(jsonRequest);
    }


    static void populateData(String apiUrl, String etc, RequestQueue queue, LinearLayout wrap, ArrayList<OLD_MainBookListData> items, LinearLayout cover, LinearLayout blank) {
        String resultURL = HELPER.API + apiUrl + HELPER.ETC + etc;

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, resultURL, null, response -> {

            try {
                JSONArray flag = response.getJSONArray(BOOKS);

                if(flag.length() == 0){
                    blank.setVisibility(View.VISIBLE);
                } else {
                    blank.setVisibility(View.GONE);
                }

                for (int i = 0; i < flag.length(); i++) {
                    JSONObject jo = flag.getJSONObject(i);

                    BookInfo tempBookInfo = BookInfo.getParseData(jo);
                    BookInfo tempBookInfoBest = BookInfo.getParseBest(i);

                    items.add(new OLD_MainBookListData(tempBookInfo.getWriter(), tempBookInfo.getTitle(), tempBookInfo.getBookImg(), tempBookInfo.getIsAdult(), tempBookInfo.getIsFinish(), tempBookInfo.getIsPremium(), tempBookInfo.getIsNobless(), tempBookInfo.getIntro(), tempBookInfo.getIsFavorite(),  tempBookInfo.getCntPageRead(), tempBookInfo.getCntFavorite(), tempBookInfo.getCntRecom(),tempBookInfoBest.getBookBestRank(),"1",tempBookInfo.getBookCode(),tempBookInfo.getCategoryKoName(),tempBookInfo.getCtnChapter()));
                    cover.setVisibility(View.GONE);
                    wrap.setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d("populateData", "에러!"));

        queue.add(jsonRequest);
    }

    static void scrollListener(String api, RequestQueue queue, LinearLayout wrap, ArrayList<OLD_MainBookListData> items, OLD_MainBookListAdapterC adpater, RecyclerView recyclerView, String etc) {

        final boolean[] isLoading = {false};
        final int[] page = {2};

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                    if (!isLoading[0] && layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == items.size() - 1) {

                        String resultURL = HELPER.API + api + HELPER.ETC + etc + "&page=" + page[0];

                        items.add(null);
                        adpater.notifyItemInserted(items.size() - 1);

                        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, resultURL, null, response -> {

                            Handler handler = new Handler(Looper.myLooper());
                            handler.postDelayed(() -> {
                                items.remove(items.size() - 1);
                                int scrollPosition = items.size();
                                adpater.notifyItemRemoved(scrollPosition);

                                try {
                                    JSONArray flag = response.getJSONArray("books");

                                    for (int i = 0; i < flag.length(); i++) {
                                        JSONObject jo = flag.getJSONObject(i);
                                        BookInfo tempBookInfo = BookInfo.getParseData(jo);
                                        items.add(new OLD_MainBookListData(tempBookInfo.getWriter(), tempBookInfo.getTitle(), tempBookInfo.getBookImg(), tempBookInfo.getIsAdult(), tempBookInfo.getIsFinish(), tempBookInfo.getIsPremium(), tempBookInfo.getIsNobless(), tempBookInfo.getIntro(), tempBookInfo.getIsFavorite(),"","","",0,"",tempBookInfo.getBookCode(),tempBookInfo.getCategoryKoName(),tempBookInfo.getCtnChapter()));
                                        wrap.setVisibility(View.VISIBLE);
                                    }

                                    adpater.notifyDataSetChanged();
                                    isLoading[0] = false;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }, 2000);
                        }, error -> Log.d("scrollListener", "에러!"));
                        queue.add(jsonRequest);
                        isLoading[0] = true;
                        page[0]++;
                    }
                }
        });
    }

    static void favToggle(RequestQueue queue, String bookCode, String token) {
        String resultURL = HELPER.API + API.USER_FAVORTATE;

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, resultURL, response ->
            Log.d("favToggle", response)
        , error -> Log.d("favToggle", "실패!")) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("api_key", HELPER.API_KEY);
                params.put("ver", HELPER.VER);
                params.put("device", HELPER.DEVICE);
                params.put("deviceuid", HELPER.DEVICE_ID);
                params.put("devicetoken", HELPER.DEVICE_TOKEN);
                params.put("token", token);
                params.put("category", "0");
                params.put("book_code", bookCode);
                return params;
            }
        };
        queue.add(stringRequest);
    }
}
