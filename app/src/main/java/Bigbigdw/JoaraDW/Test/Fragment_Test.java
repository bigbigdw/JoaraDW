package Bigbigdw.JoaraDW.Test;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Bigbigdw.JoaraDW.Etc.HELPER;
import Bigbigdw.JoaraDW.Fragment_New.Main_BookListAdapter_New;
import Bigbigdw.JoaraDW.Main.Main_BookData;
import Bigbigdw.JoaraDW.Main.Main_BookListData_A;
import Bigbigdw.JoaraDW.R;

public class Fragment_Test extends Fragment {
    private TestAdapter NewBookListAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Main_BookListData_A> items = new ArrayList<>();
    private boolean isLoading = false;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab_new_test, container, false);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        Integer Page = 1;
        String API = "/v1/book/list.joa";
        String ETC = "&store=&orderby=redate&offset=25&page=" + Page + "&class=";
        recyclerView = root.findViewById(R.id.Main_NewTest);
        LinearLayout Wrap = root.findViewById(R.id.Tab_NewTest);

        populateData(API, ETC, queue, Wrap, Page);
        initAdapter();
        initScrollListener(API, "&store=&orderby=redate&offset=25&page=" + Page + "&class=", queue, Wrap, Page);

        return root;
    }

    private void setItems(String API_URL, String ETC, RequestQueue queue, LinearLayout Wrap, Integer Page) {
        String ResultURL = HELPER.API + API_URL + HELPER.ETC + ETC;
        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, ResultURL, null, response -> {
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
                    items.add(new Main_BookListData_A(Writer, Title, BookImg, IsAdult, IsFinish, IsPremium, IsNobless, Intro, IsFav));
                    Wrap.setVisibility(View.VISIBLE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Main_BookData", "에러!");
            }
        });
        queue.add(jsonRequest);
        Page++;
    }


    private void populateData(String API, String ETC, RequestQueue queue, LinearLayout Wrap, Integer Page) {
        setItems(API, ETC, queue, Wrap, Page);
    }

    private void initAdapter() {
        NewBookListAdapter = new TestAdapter(items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(NewBookListAdapter);
    }

    private void initScrollListener(String API, String ETC, RequestQueue queue, LinearLayout Wrap, Integer Page) {
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == items.size() - 1) {
                        //리스트 마지막
                        loadMore(API, ETC, queue, Wrap, Page);
                        isLoading = true;
                    }
                }
            }
        });
    }


    private void loadMore(String API, String ETC, RequestQueue queue, LinearLayout Wrap, Integer Page) {
        items.add(null);
        NewBookListAdapter.notifyItemInserted(items.size() - 1);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                items.remove(items.size() - 1);
                int scrollPosition = items.size();
                NewBookListAdapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + 24;

                while (currentSize - 1 < nextLimit) {
                    setItems(API, "&store=&orderby=redate&offset=25&page=" + Page + "&class=", queue, Wrap, Page);
                    currentSize++;
                }

                NewBookListAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);
    }
}