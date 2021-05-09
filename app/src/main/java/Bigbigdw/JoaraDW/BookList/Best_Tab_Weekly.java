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

import java.util.ArrayList;

import Bigbigdw.JoaraDW.BookList.Main_BookListAdapter_Best;
import Bigbigdw.JoaraDW.Book_Detail.Book_Detail_Cover;
import Bigbigdw.JoaraDW.Config;
import Bigbigdw.JoaraDW.Fragment_New.Book_Pagination;
import Bigbigdw.JoaraDW.Main.Main_BookData;
import Bigbigdw.JoaraDW.Main.Main_BookListData;
import Bigbigdw.JoaraDW.R;

public class Best_Tab_Weekly extends Fragment {
    private ArrayList<Main_BookListData> items = new ArrayList<>();
    private RequestQueue queue;
    String BestType = "weekly";
    LinearLayout LoadingLayout;
    NestedScrollView ContentsLayout;
    String USERTOKEN = "";
    JSONObject GETUSERINFO = null;
    TextView GotoBest, GotoNobless, GotoPremium, GotoFree, GotoNew, GotoFinish, GotoNoblessClassic;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_best_tab, container, false);
        queue = Volley.newRequestQueue(getActivity());

        LoadingLayout = root.findViewById(R.id.LoadingLayout);
        ContentsLayout = root.findViewById(R.id.ContentsLayout);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            LoadingLayout.setVisibility(View.GONE);
            ContentsLayout.setVisibility(View.VISIBLE);
        }, 500);

        if (Config.GETUSERINFO() != null) {
            GETUSERINFO = Config.GETUSERINFO();
            JSONObject UserInfo;
            try {
                UserInfo = GETUSERINFO.getJSONObject("user");
                USERTOKEN = UserInfo.getString("token");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Main_BookListAdapter_Best AllAdapter = new Main_BookListAdapter_Best(items);
        Main_BookListAdapter_Best NoblessAdapter = new Main_BookListAdapter_Best(items);
        Main_BookListAdapter_Best PremiumAdapter = new Main_BookListAdapter_Best(items);
        Main_BookListAdapter_Best FreeAdapter = new Main_BookListAdapter_Best(items);
        Main_BookListAdapter_Best SeriesAdapter = new Main_BookListAdapter_Best(items);
        Main_BookListAdapter_Best FinishAdapter = new Main_BookListAdapter_Best(items);
        Main_BookListAdapter_Best NoblessClassicAdapter = new Main_BookListAdapter_Best(items);

        BookBest(root, "/v1/best/book.joa", "&best=" + BestType + "&store=&orderby=cnt_best&offset=25&page=1" + "&token=" + USERTOKEN, R.id.Best_Tab_AllList, AllAdapter, queue, R.id.Best_Tab_All);
        BookBest(root, "/v1/best/book.joa", "&best=" + BestType + "&store=nobless&orderby=cnt_best&offset=25&page=1" + "&token=" + USERTOKEN, R.id.Best_Tab_NoblessList, NoblessAdapter, queue, R.id.Best_Tab_Nobless);
        BookBest(root, "/v1/best/book.joa", "&best=" + BestType + "&store=premium&orderby=cnt_best&offset=25&page=1" + "&token=" + USERTOKEN, R.id.Best_Tab_PremiumList, PremiumAdapter, queue, R.id.Best_Tab_Premium);
        BookBest(root, "/v1/best/book.joa", "&best=" + BestType + "&store=series&orderby=cnt_best&offset=25&page=1" + "&token=" + USERTOKEN, R.id.Best_Tab_FreeList, FreeAdapter, queue, R.id.Best_Tab_Free);
        BookBest(root, "/v1/best/book.joa", "&best=" + BestType + "&store=lately&orderby=cnt_best&offset=25&page=1" + "&token=" + USERTOKEN, R.id.Best_Tab_SeriesList, SeriesAdapter, queue, R.id.Best_Tab_Series);
        BookBest(root, "/v1/best/book.joa", "&best=" + BestType + "&store=finish&orderby=cnt_best&offset=25&page=1" + "&token=" + USERTOKEN, R.id.Best_Tab_FinishList, FinishAdapter, queue, R.id.Best_Tab_Finish);
        BookBest(root, "/v1/best/book.joa", "&best=" + BestType + "&store=nobless_classic&orderby=cnt_best&offset=25&page=1" + "&token=" + USERTOKEN, R.id.Best_Tab_NoblessClassicList, NoblessClassicAdapter, queue, R.id.Best_Tab_NoblessClassic);

        GotoBest = root.findViewById(R.id.GotoBest);
        GotoNobless = root.findViewById(R.id.GotoNobless);
        GotoPremium = root.findViewById(R.id.GotoPremium);
        GotoFree = root.findViewById(R.id.GotoFree);
        GotoNew = root.findViewById(R.id.GotoNew);
        GotoFinish = root.findViewById(R.id.GotoFinish);
        GotoNoblessClassic = root.findViewById(R.id.GotoNoblessClassic);

        GotoBest.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext().getApplicationContext(), Book_Page_Etc.class);
            intent.putExtra("Title", String.format("%s", "주간 전체 베스트"));
            intent.putExtra("TYPE", String.format("%s", "BEST"));
            intent.putExtra("API_URL", String.format("%s", "/v1/best/book.joa"));
            intent.putExtra("ETC_URL", String.format("%s", "&best=" + BestType + "&store=&orderby=cnt_best&offset=100&page=1" + "&token=" + USERTOKEN));
            startActivity(intent);
        });

        GotoNobless.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext().getApplicationContext(), Book_Page_Etc.class);
            intent.putExtra("Title", String.format("%s", "주간 노블레스 베스트"));
            intent.putExtra("TYPE", String.format("%s", "BEST"));
            intent.putExtra("API_URL", String.format("%s", "/v1/best/book.joa"));
            intent.putExtra("ETC_URL", String.format("%s", "&best=" + BestType + "&store=nobless&orderby=cnt_best&offset=100&page=1" + "&token=" + USERTOKEN));
            startActivity(intent);
        });

        GotoPremium.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext().getApplicationContext(), Book_Page_Etc.class);
            intent.putExtra("Title", String.format("%s", "주간 프리미엄 베스트"));
            intent.putExtra("TYPE", String.format("%s", "BEST"));
            intent.putExtra("API_URL", String.format("%s", "/v1/best/book.joa"));
            intent.putExtra("ETC_URL", String.format("%s", "&best=" + BestType + "&store=premium&orderby=cnt_best&offset=100&page=1" + "&token=" + USERTOKEN));
            startActivity(intent);
        });

        GotoFree.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext().getApplicationContext(), Book_Page_Etc.class);
            intent.putExtra("Title", String.format("%s", "주간 무료 베스트"));
            intent.putExtra("TYPE", String.format("%s", "BEST"));
            intent.putExtra("API_URL", String.format("%s", "/v1/best/book.joa"));
            intent.putExtra("ETC_URL", String.format("%s", "&best=" + BestType + "&store=series&orderby=cnt_best&offset=100&page=1" + "&token=" + USERTOKEN));
            startActivity(intent);
        });

        GotoNew.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext().getApplicationContext(), Book_Page_Etc.class);
            intent.putExtra("Title", String.format("%s", "주간 신규 베스트"));
            intent.putExtra("TYPE", String.format("%s", "BEST"));
            intent.putExtra("API_URL", String.format("%s", "/v1/best/book.joa"));
            intent.putExtra("ETC_URL", String.format("%s", "&best=" + BestType + "&store=lately&orderby=cnt_best&offset=100&page=1" + "&token=" + USERTOKEN));
            startActivity(intent);
        });

        GotoFinish.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext().getApplicationContext(), Book_Page_Etc.class);
            intent.putExtra("Title", String.format("%s", "주간 완결 베스트"));
            intent.putExtra("TYPE", String.format("%s", "BEST"));
            intent.putExtra("API_URL", String.format("%s", "/v1/best/book.joa"));
            intent.putExtra("ETC_URL", String.format("%s", "&best=" + BestType + "&store=finish&orderby=cnt_best&offset=25&page=1" + "&token=" + USERTOKEN));
            startActivity(intent);
        });

        GotoNoblessClassic.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext().getApplicationContext(), Book_Page_Etc.class);
            intent.putExtra("Title", String.format("%s", "주간 노블레스클래식 베스트"));
            intent.putExtra("TYPE", String.format("%s", "BEST"));
            intent.putExtra("API_URL", String.format("%s", "/v1/home/list.joa"));
            intent.putExtra("ETC_URL", String.format("%s", "&best=" + BestType + "&store=nobless_classic&orderby=cnt_best&offset=25&page=1" + "&token=" + USERTOKEN));
            startActivity(intent);
        });

        return root;
    }

    public void BookBest(View root, String API_URL, String ETC, Integer RecylerView, Main_BookListAdapter_Best Adapter, RequestQueue queue, Integer Wrap) {
        RecyclerView recyclerView = root.findViewById(RecylerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(Adapter);
        LinearLayout wrap = root.findViewById(Wrap);
        Adapter.setItems(new Main_BookData().getData(API_URL, ETC, queue, wrap, "BEST"));
        Adapter.notifyDataSetChanged();

        Adapter.setOnItemClicklistener((holder, view, position, Value) -> {
            Main_BookListData item = Adapter.getItem(position);
            if (Value.equals("FAV")) {
                Book_Pagination.FavToggle(queue, item.getBookCode(), USERTOKEN);
            } else if (Value.equals("BookDetail")) {
                Intent intent = new Intent(requireContext().getApplicationContext(), Book_Detail_Cover.class);
                intent.putExtra("BookCode", String.format("%s", item.getBookCode()));
                intent.putExtra("TOKEN", String.format("%s", USERTOKEN));
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