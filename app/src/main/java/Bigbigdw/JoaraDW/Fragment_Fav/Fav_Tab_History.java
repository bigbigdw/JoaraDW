package Bigbigdw.JoaraDW.Fragment_Fav;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Bigbigdw.JoaraDW.Etc.HELPER;
import Bigbigdw.JoaraDW.Fragment_New.Book_Pagination;
import Bigbigdw.JoaraDW.Login.Login_Main;
import Bigbigdw.JoaraDW.Main.Main_BookListData;
import Bigbigdw.JoaraDW.R;

public class Fav_Tab_History extends Fragment {
    private Main_BookList_Adapter_History NewBookListAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Main_BookListData> items = new ArrayList<>();
    LinearLayout Wrap, LoginLayout;
    String USERTOKEN = "&token=";
    String STATUS = "";
    TextView Book_Fav_CoverText;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_fav_list_history, container, false);
        recyclerView = root.findViewById(R.id.Fav_HistoryBookList);
        Wrap = root.findViewById(R.id.Tab_History);
        LoginLayout = root.findViewById(R.id.LoginLayout);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        Book_Fav_CoverText = root.findViewById(R.id.Book_Fav_CoverText);

        try {
            FileReader fr = new FileReader(getActivity().getDataDir() + "/userInfo.json");
            BufferedReader br = new BufferedReader(fr);
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line).append("\n");
                line = br.readLine();
            }
            br.close();
            String result = sb.toString();
            JSONObject jsonObject = new JSONObject(result);
            JSONObject UserInfo = jsonObject.getJSONObject("user");
            USERTOKEN = "&token=" + UserInfo.getString("token");
            STATUS = jsonObject.getString("status");
            Log.d("Fav_Tab_History", "읽기 완료");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.d("Fav_Tab_History", "읽기 실패");
        }

        String API = "/v1/user/historybooks.joa";
        String ETC = USERTOKEN + "&category=0&page=1&mem_time=0";

        if (STATUS.equals("1")) {
            Book_Pagination.LoginCheck(queue, USERTOKEN, Book_Fav_CoverText);
            Book_Pagination.populateDataFav(API, ETC, queue, Wrap, items, LoginLayout, "History");
            initAdapter();
            initScrollListener(API, queue, Wrap, items, NewBookListAdapter, recyclerView, USERTOKEN);
        }

        return root;
    }

    private void initAdapter() {
        NewBookListAdapter = new Main_BookList_Adapter_History(items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(NewBookListAdapter);
    }

//    static void populateData(String API_URL, String ETC, RequestQueue queue, LinearLayout Wrap, ArrayList<Main_BookListData> items, LinearLayout Cover, String STATUS, LinearLayout LoginLayout) {
//        String ResultURL = HELPER.API + API_URL + HELPER.ETC + ETC;
//
//        Log.d("ResultURL =", ResultURL);
//
//        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, ResultURL, null, response -> {
//            try {
//
//                if (response.getString("status").equals("1") && STATUS.equals("1")) {
//
//                    LoginLayout.setVisibility(View.GONE);
//
//                    JSONArray flag = response.getJSONArray("books");
//
//                    for (int i = 0; i < flag.length(); i++) {
//                        JSONObject jo = flag.getJSONObject(i);
//
//                        String BookImg = jo.getString("book_img");
//                        String Title = jo.getString("subject");
//                        String Writer = jo.getString("writer_name");
//                        String IsAdult = jo.getString("is_adult");
//                        String IsFinish = jo.getString("is_finish");
//                        String IsPremium = jo.getString("is_premium");
//                        String IsNobless = jo.getString("is_nobless");
//                        String Intro = jo.getString("intro");
//                        String IsFav = jo.getString("is_favorite");
//                        String ReadHistory = jo.getString("history_sortno");
//                        items.add(new Main_BookListData(Writer, Title, BookImg, IsAdult, IsFinish, IsPremium, IsNobless, Intro, IsFav, "", "", "", "", 0, ReadHistory, ""));
//                        Cover.setVisibility(View.GONE);
//                        Wrap.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    LoginLayout.setVisibility(View.VISIBLE);
//                }
//
//
//                Log.d("Fav_Tab_History", "완료!");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }, error -> Log.d("Fav_Tab_History", "에러!"));
//
//        queue.add(jsonRequest);
//    }

    static void initScrollListener(String API, RequestQueue queue, LinearLayout Wrap, ArrayList<Main_BookListData> items, Main_BookList_Adapter_History FavBookListAdapter, RecyclerView recyclerView, String USERTOKEN) {

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

                        items.add(null);
                        FavBookListAdapter.notifyItemInserted(items.size() - 1);

                        String ResultURL = HELPER.API + API + HELPER.ETC + USERTOKEN + "&category=0&page=" + Page[0] + "&mem_time=0";
                        Log.d("ResultURL", ResultURL);
                        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, ResultURL, null, response -> {

                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                items.remove(items.size() - 1);
                                int scrollPosition = items.size();
                                FavBookListAdapter.notifyItemRemoved(scrollPosition);

                                try {
                                    int MaxPage = (int) Math.ceil(response.getInt("total_cnt") / 50);

                                    if (Page[0] < MaxPage + 2) {
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
                                            String ReadHistory = jo.getString("history_sortno");
                                            items.add(new Main_BookListData(Writer, Title, BookImg, IsAdult, IsFinish, IsPremium, IsNobless, Intro, IsFav, "", "", "", "", 0, ReadHistory, ""));
                                            Wrap.setVisibility(View.VISIBLE);
                                        }
                                        Log.d("Fav_Tab_History", "완료!");

                                        FavBookListAdapter.notifyDataSetChanged();
                                        isLoading[0] = false;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }, 2000);
                        }, error -> Log.d("Fav_Tab_History", "에러!"));
                        queue.add(jsonRequest);
                        isLoading[0] = true;
                        Page[0]++;
                    }
                }
            }
        });
    }
}
