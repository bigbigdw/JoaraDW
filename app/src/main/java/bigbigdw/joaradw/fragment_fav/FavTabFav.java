package bigbigdw.joaradw.fragment_fav;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

import bigbigdw.joaradw.JOARADW;
import bigbigdw.joaradw.base.BookBaseFragment;
import bigbigdw.joaradw.etc.API;
import bigbigdw.joaradw.etc.HELPER;
import bigbigdw.joaradw.BookPagination;
import bigbigdw.joaradw.main.MainBookListData;
import bigbigdw.joaradw.R;
import bigbigdw.joaradw.model.BookInfo;

public class FavTabFav extends BookBaseFragment {
    private MainBookListAdapterFav adapter;
    private RecyclerView recyclerView;
    private final ArrayList<MainBookListData> items = new ArrayList<>();
    LinearLayout wrap;
    LinearLayout loginLayout;
    String userStatus = "";
    String userToken = "";
    TextView bookFavCoverText;
    RequestQueue queue;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_fav_list_fav, container, false);

        queue = Volley.newRequestQueue(requireActivity());
        recyclerView = root.findViewById(R.id.Fav_FavBookList);
        wrap = root.findViewById(R.id.Tab_Fav);
        loginLayout = root.findViewById(R.id.LoginLayout);
        bookFavCoverText = root.findViewById(R.id.Book_Fav_CoverText);

        checkToken();
        JOARADW app = (JOARADW) requireActivity().getApplicationContext();
        userToken = "&token=" + app.getToken();
        userStatus = app.getStatus();

        setLayout();

        return root;
    }

    public void setLayout(){

        String etc = userToken + "&category=all&store=&class=&offset=10&orderby=bookmark&page=1&query=&mem_time=0";

        if (userStatus.equals("1")) {
            BookPagination.loginCheck(queue, userToken, bookFavCoverText);
            BookPagination.populateDataFav(API.USER_FAVORITE_JOA, etc, queue, wrap, items, loginLayout, "Fav");
            initAdapter();
            initScrollListener();

            adapter.setOnItemClickListener((v, position, value) -> {
                MainBookListData item = adapter.getItem(position);
                adapterListener(item, value, queue);
            });
        }
    }

    private void initAdapter() {
        adapter = new MainBookListAdapterFav(items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void initScrollListener() {

        final boolean[] isLoading = {false};
        final int[] page = {2};

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                    if (!isLoading[0] && layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == items.size() - 1) {

                        items.add(null);
                        adapter.notifyItemInserted(items.size() - 1);

                        String resultURL = HELPER.API + API.USER_FAVORITE_JOA + HELPER.ETC + "&token=" + userToken + "&category=all&store=&class=&offset=10&orderby=bookmark&page="+ page[0] + "&query=&mem_time=0";

                        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, resultURL, null, response -> {

                            Handler handler = new Handler(Looper.myLooper());
                            handler.postDelayed(() -> {
                                items.remove(items.size() - 1);
                                int scrollPosition = items.size();
                                adapter.notifyItemRemoved(scrollPosition);

                                try {
                                    JSONArray flag = response.getJSONArray("books");

                                    for (int i = 0; i < flag.length(); i++) {
                                        JSONObject jo = flag.getJSONObject(i);
                                        BookInfo tempBookInfo = BookInfo.getParseData(jo);
                                        items.add(new MainBookListData(tempBookInfo.getWriter(), tempBookInfo.getTitle(), tempBookInfo.getBookImg(), tempBookInfo.getIsAdult(), tempBookInfo.getIsFinish(), tempBookInfo.getIsPremium(), tempBookInfo.getIsNobless(), tempBookInfo.getIntro(), tempBookInfo.getIsFavorite(),"","","",0,"",tempBookInfo.getBookCode(),tempBookInfo.getCategoryKoName(),tempBookInfo.getCtnChapter()));
                                        wrap.setVisibility(View.VISIBLE);
                                    }

                                    adapter.notifyDataSetChanged();
                                    isLoading[0] = false;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }, 2000);
                        }, error -> Log.d("setItems", "에러!"));
                        queue.add(jsonRequest);
                        isLoading[0] = true;
                        page[0]++;
                    }
            }
        });
    }
}
