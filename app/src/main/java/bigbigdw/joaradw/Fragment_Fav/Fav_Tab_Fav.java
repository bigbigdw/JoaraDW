package bigbigdw.joaradw.Fragment_Fav;

import android.content.Intent;
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

import java.util.ArrayList;

import bigbigdw.joaradw.Book_Detail.Book_Detail;
import bigbigdw.joaradw.Config;
import bigbigdw.joaradw.etc.HELPER;
import bigbigdw.joaradw.BookPagination;
import bigbigdw.joaradw.main.MainBookListData;
import bigbigdw.joaradw.R;

public class Fav_Tab_Fav extends Fragment {
    private Main_BookListAdapter_Fav Adapter;
    private RecyclerView recyclerView;
    private ArrayList<MainBookListData> items = new ArrayList<>();
    LinearLayout Wrap, LoginLayout;
    String STATUS = "", TOKEN = "", API = "/v1/user/favorite.joa";
    TextView Book_Fav_CoverText;
    RequestQueue queue;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_fav_list_fav, container, false);

        queue = Volley.newRequestQueue(getActivity());
        recyclerView = root.findViewById(R.id.Fav_FavBookList);
        Wrap = root.findViewById(R.id.Tab_Fav);
        LoginLayout = root.findViewById(R.id.LoginLayout);
        Book_Fav_CoverText = root.findViewById(R.id.Book_Fav_CoverText);

        if (Config.getuserinfo() != null) {
            JSONObject GETUSERINFO = Config.getuserinfo();
            JSONObject UserInfo;
            try {
                UserInfo = GETUSERINFO.getJSONObject("user");
                TOKEN = UserInfo.getString("token");
                STATUS = GETUSERINFO.getString("status");
            } catch (JSONException e) {
                e.printStackTrace();
                TOKEN = "";
            }
        }

        String ETC = "&token=" + TOKEN + "&category=all&store=&class=&offset=10&orderby=bookmark&page=1&query=&mem_time=0";

        if (STATUS.equals("1")) {
            BookPagination.loginCheck(queue, "&token=" + TOKEN, Book_Fav_CoverText);
            BookPagination.populateDataFav(API, ETC, queue, Wrap, items, LoginLayout, "Fav");
            initAdapter();
            initScrollListener();

            Adapter.setOnItemClickListener((v, position, Value) -> {
                MainBookListData item = Adapter.getItem(position);
                if (Value.equals("FAV")) {
                    BookPagination.favToggle(queue, item.getBookCode(), TOKEN);
                } else if (Value.equals("BookDetail")) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), Book_Detail.class);
                    intent.putExtra("BookCode", String.format("%s", item.getBookCode()));
                    intent.putExtra("TOKEN", String.format("%s", TOKEN));
                    startActivity(intent);
                }
            });
        }
        
        return root;
    }

    private void initAdapter() {
        Adapter = new Main_BookListAdapter_Fav(items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(Adapter);
    }

    public void initScrollListener() {

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
                        Adapter.notifyItemInserted(items.size() - 1);

                        String ResultURL = HELPER.API + API + HELPER.ETC + "&token=" + TOKEN + "&category=all&store=&class=&offset=10&orderby=bookmark&page="+ Page[0] + "&query=&mem_time=0";

                        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, ResultURL, null, response -> {

                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                items.remove(items.size() - 1);
                                int scrollPosition = items.size();
                                Adapter.notifyItemRemoved(scrollPosition);

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

                                        items.add(new MainBookListData(Writer, Title, BookImg, IsAdult, IsFinish, IsPremium, IsNobless, Intro, IsFav,"","","",0,"",BookCode,BookCategory));
                                        Wrap.setVisibility(View.VISIBLE);
                                    }

                                    Adapter.notifyDataSetChanged();
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

