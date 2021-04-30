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
import Bigbigdw.JoaraDW.Main.Main_BookListData;
import Bigbigdw.JoaraDW.R;

public class Fav_Tab_Fav extends Fragment {
    private Main_BookListAdapter_Fav FavBookListAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Main_BookListData> items = new ArrayList<>();
    LinearLayout Wrap, LoginLayout;
    String USERTOKEN = "";
    String STATUS = "";
    TextView Book_Fav_CoverText;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_fav_list_fav, container, false);

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        recyclerView = root.findViewById(R.id.Fav_FavBookList);
        Wrap = root.findViewById(R.id.Tab_Fav);
        LoginLayout = root.findViewById(R.id.LoginLayout);
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
            USERTOKEN = UserInfo.getString("token");
            STATUS = jsonObject.getString("status");
            Log.d("USERINFO", "읽기 완료");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.d("USERINFO", "읽기 실패");
        }

        String API = "/v1/user/favorite.joa";
        String ETC = "&token=" + USERTOKEN + "&category=all&store=&class=&offset=10&orderby=bookdate&page=1&query=&mem_time=0";

        if (STATUS.equals("1")) {
            Book_Pagination.LoginCheck(queue, "&token=" + USERTOKEN, Book_Fav_CoverText);
            Book_Pagination.populateDataFav(API, ETC, queue, Wrap, items, LoginLayout, "Fav");
            initAdapter();
            initScrollListener(API, queue, Wrap, items, FavBookListAdapter, recyclerView, USERTOKEN);
            FavBookListAdapter.setOnItemClicklistener((holder, view, position, Value) -> {
                Main_BookListData item = FavBookListAdapter.getItem(position);
                Book_Pagination.FavToggle(queue, item.getBookCode(), USERTOKEN);
            });
        }
        
        return root;
    }

    private void initAdapter() {
        FavBookListAdapter = new Main_BookListAdapter_Fav(items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(FavBookListAdapter);
    }

    static void initScrollListener(String API, RequestQueue queue, LinearLayout Wrap, ArrayList<Main_BookListData> items, Main_BookListAdapter_Fav FavBookListAdapter, RecyclerView recyclerView, String USERTOKEN) {

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

                        String ResultURL = HELPER.API + API + HELPER.ETC + "&token=" + USERTOKEN + "&category=all&store=&class=&offset=10&orderby=bookdate&page="+ Page[0] + "&query=&mem_time=0";

                        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, ResultURL, null, response -> {

                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                items.remove(items.size() - 1);
                                int scrollPosition = items.size();
                                FavBookListAdapter.notifyItemRemoved(scrollPosition);

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
                                        items.add(new Main_BookListData(Writer, Title, BookImg, IsAdult, IsFinish, IsPremium, IsNobless, Intro, IsFav,"","","","",0,"",""));
                                        Wrap.setVisibility(View.VISIBLE);
                                    }
                                    Log.d("setItems", "완료!");

                                    FavBookListAdapter.notifyDataSetChanged();
                                    isLoading[0] = false;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }, 2000);
                        }, error -> Log.d("setItems", "에러!"));
                        queue.add(jsonRequest);
                        isLoading[0] = true;
                        Page[0]++;
                    }
                }
            }
        });
    }
}

