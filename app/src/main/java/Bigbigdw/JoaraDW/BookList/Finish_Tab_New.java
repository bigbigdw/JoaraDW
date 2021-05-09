package Bigbigdw.JoaraDW.BookList;

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
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Bigbigdw.JoaraDW.Book_Detail.Book_Detail_Cover;
import Bigbigdw.JoaraDW.Config;
import Bigbigdw.JoaraDW.Fragment_New.Book_Pagination;
import Bigbigdw.JoaraDW.JOARADW;
import Bigbigdw.JoaraDW.Main.Main_BookData;
import Bigbigdw.JoaraDW.Main.Main_BookListData;
import Bigbigdw.JoaraDW.R;

public class Finish_Tab_New extends Fragment {
    private Main_BookListAdapter_C NewAdapter;
    private Main_BookListAdapter_C NoblessAdapter;
    private Main_BookListAdapter_C PremiumAdapter;
    private ArrayList<Main_BookListData> items = new ArrayList<>();

    private RequestQueue queue;
    String FinishType = "redate";
    LinearLayout LoadingLayout;
    NestedScrollView ContentsLayout;
    String TOKEN = "";
    TextView GoToAll, GotoPremium, GotoNobless;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_finish_tab, container, false);
        queue = Volley.newRequestQueue(getActivity());

        LoadingLayout = root.findViewById(R.id.LoadingLayout);
        ContentsLayout = root.findViewById(R.id.ContentsLayout);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            LoadingLayout.setVisibility(View.GONE);
            ContentsLayout.setVisibility(View.VISIBLE);
        }, 500);

        if(Config.GETUSERINFO() != null){
            JSONObject GETUSERINFO = Config.GETUSERINFO();
            JSONObject UserInfo;
            try {
                UserInfo = GETUSERINFO.getJSONObject("user");
                TOKEN = UserInfo.getString("token");
            } catch (JSONException e) {
                e.printStackTrace();
                TOKEN = "";
            }
        }

        NewAdapter = new Main_BookListAdapter_C(items);
        NoblessAdapter = new Main_BookListAdapter_C(items);
        PremiumAdapter = new Main_BookListAdapter_C(items);

        BookFinish(root, "/v1/book/list.joa", "&category=0&store=finish&orderby=" + FinishType + "&offset=25&page=1" + "&token=" + TOKEN, R.id.Finish_Tab_NewList, NewAdapter, queue, R.id.Finish_Tab_New);
        BookFinish(root, "/v1/book/list.joa", "&category=0&store=nobless_finish&orderby=" + FinishType + "&offset=25&page=1" + "&token=" + TOKEN, R.id.Finish_Tab_NoblessList, NoblessAdapter, queue, R.id.Finish_Tab_Nobless);
        BookFinish(root, "/v1/book/list.joa", "&category=0&store=premium_finish&orderby=" + FinishType + "&offset=25&page=1" + "&token=" + TOKEN, R.id.Finish_Tab_PremiumList, PremiumAdapter, queue, R.id.Finish_Tab_Premium);

        GoToAll = root.findViewById(R.id.GoToAll);
        GotoPremium = root.findViewById(R.id.GotoPremium);
        GotoNobless = root.findViewById(R.id.GotoNobless);

        GoToAll.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext().getApplicationContext(), Book_Page_Etc.class);
            intent.putExtra("Title", String.format("%s", "최신작 전체 완결"));
            intent.putExtra("TYPE", String.format("%s", "FINISH"));
            intent.putExtra("API_URL", String.format("%s", "/v1/book/list.joa"));
            intent.putExtra("ETC_URL", String.format("%s", "&category=0&store=finish&orderby=" + FinishType + "&offset=25&page=1" + "&token=" + TOKEN));
            startActivity(intent);
        });

        GotoNobless.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext().getApplicationContext(), Book_Page_Etc.class);
            intent.putExtra("Title", String.format("%s", "최신작 노블레스 완결"));
            intent.putExtra("TYPE", String.format("%s", "FINISH"));
            intent.putExtra("API_URL", String.format("%s", "/v1/book/list.joa"));
            intent.putExtra("ETC_URL", String.format("%s", "&category=0&store=nobless_finish&orderby=" + FinishType + "&offset=25&page=1" + "&token=" + TOKEN));
            startActivity(intent);
        });

        GotoPremium.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext().getApplicationContext(), Book_Page_Etc.class);
            intent.putExtra("Title", String.format("%s", "최신작 프리미엄 완결"));
            intent.putExtra("TYPE", String.format("%s", "FINISH"));
            intent.putExtra("API_URL", String.format("%s", "/v1/book/list.joa"));
            intent.putExtra("ETC_URL", String.format("%s", "&category=0&store=premium_finish&orderby=" + FinishType + "&offset=25&page=1" + "&token=" + TOKEN));
            startActivity(intent);
        });

        return root;
    }

    public void BookFinish(View root, String API_URL, String ETC, Integer RecylerView, Main_BookListAdapter_C Adapter, RequestQueue queue, Integer Wrap) {
        Adapter.setOnItemClicklistener((holder, view, position, Value) -> {
            Main_BookListData item = Adapter.getItem(position);
            Book_Pagination.FavToggle(queue, item.getBookCode(), TOKEN);
        });
        RecyclerView recyclerView = root.findViewById(RecylerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(Adapter);
        LinearLayout wrap = root.findViewById(Wrap);
        Adapter.setItems(new Main_BookData().getData(API_URL, ETC, queue, wrap,""));
        Adapter.notifyDataSetChanged();

        Adapter.setOnItemClicklistener((holder, view, position, Value) -> {
            Main_BookListData item = Adapter.getItem(position);
            if(Value.equals("FAV")){
                Book_Pagination.FavToggle(queue, item.getBookCode(), TOKEN);
            } else if (Value.equals("BookDetail")){
                Intent intent = new Intent(requireContext().getApplicationContext(), Book_Detail_Cover.class);
                intent.putExtra("BookCode",String.format("%s", item.getBookCode()));
                intent.putExtra("TOKEN",String.format("%s", TOKEN));
                startActivity(intent);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("onDestroyView", "파괴!");
    }
}
