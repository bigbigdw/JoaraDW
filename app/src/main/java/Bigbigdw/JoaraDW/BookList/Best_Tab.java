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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Bigbigdw.JoaraDW.Book_Detail.Book_Detail_Cover;
import Bigbigdw.JoaraDW.Config;
import Bigbigdw.JoaraDW.Book_Pagination;
import Bigbigdw.JoaraDW.Main.Main_BookData;
import Bigbigdw.JoaraDW.Main.Main_BookListData;
import Bigbigdw.JoaraDW.Main.Tab_ViewModel;
import Bigbigdw.JoaraDW.R;

public class Best_Tab extends Fragment {
    private RequestQueue queue;
    private ArrayList<Main_BookListData> items = new ArrayList<>();
    private static final String ARG_SECTION_NUMBER = "section_number";
    private Tab_ViewModel Tab_ViewModel;
    String BestType = "weekly", TOKEN = "", TabName = "", API_URL = "/v1/best/book.joa", ETC_URL = "&orderby=cnt_best&offset=100&page=1";
    LinearLayout LoadingLayout;
    NestedScrollView ContentsLayout;
    JSONObject GETUSERINFO = null;
    TextView GotoBest, GotoNobless, GotoPremium, GotoFree, GotoNew, GotoFinish, GotoNoblessClassic;
    int index;

    public static Best_Tab newInstance(int index) {
        Best_Tab fragment = new Best_Tab();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tab_ViewModel = new ViewModelProvider(this).get(Tab_ViewModel.class);
        index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        Tab_ViewModel.setIndex(index);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_best_tab, container, false);
        queue = Volley.newRequestQueue(getActivity());

        LoadingLayout = root.findViewById(R.id.LoadingLayout);
        ContentsLayout = root.findViewById(R.id.ContentsLayout);
        GotoBest = root.findViewById(R.id.GotoBest);
        GotoNobless = root.findViewById(R.id.GotoNobless);
        GotoPremium = root.findViewById(R.id.GotoPremium);
        GotoFree = root.findViewById(R.id.GotoFree);
        GotoNew = root.findViewById(R.id.GotoNew);
        GotoFinish = root.findViewById(R.id.GotoFinish);
        GotoNoblessClassic = root.findViewById(R.id.GotoNoblessClassic);

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
                TOKEN = UserInfo.getString("token");
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

        Tab_ViewModel.getText().observe(getViewLifecycleOwner(), TabNum -> {
            switch (TabNum) {
                case "TAB1":
                    BestType = "realtime";
                    TabName = "실시간";
                    break;
                case "TAB2":
                    BestType = "today";
                    TabName = "투데이";
                    break;
                case "TAB3":
                    BestType = "weekly";
                    TabName = "주간";
                    break;
                default:
                    BestType = "monthly";
                    TabName = "월간";
                    break;
            }

            BookBest(root, API_URL, "&best=" + BestType + "&store=" + ETC_URL + "&token=" + TOKEN, R.id.Best_Tab_AllList, AllAdapter, queue, R.id.Best_Tab_All);
            BookBest(root, API_URL, "&best=" + BestType + "&store=nobless" + ETC_URL + "&token=" + TOKEN, R.id.Best_Tab_NoblessList, NoblessAdapter, queue, R.id.Best_Tab_Nobless);
            BookBest(root, API_URL, "&best=" + BestType + "&store=premium" + ETC_URL + "&token=" + TOKEN, R.id.Best_Tab_PremiumList, PremiumAdapter, queue, R.id.Best_Tab_Premium);
            BookBest(root, API_URL, "&best=" + BestType + "&store=series" + ETC_URL + "&token=" + TOKEN, R.id.Best_Tab_FreeList, FreeAdapter, queue, R.id.Best_Tab_Free);
            BookBest(root, API_URL, "&best=" + BestType + "&store=lately" + ETC_URL + "&token=" + TOKEN, R.id.Best_Tab_SeriesList, SeriesAdapter, queue, R.id.Best_Tab_Series);
            BookBest(root, API_URL, "&best=" + BestType + "&store=finish" + ETC_URL + "&token=" + TOKEN, R.id.Best_Tab_FinishList, FinishAdapter, queue, R.id.Best_Tab_Finish);
            BookBest(root, API_URL, "&best=" + BestType + "&store=nobless_classic" + ETC_URL + "&token=" + TOKEN, R.id.Best_Tab_NoblessClassicList, NoblessClassicAdapter, queue, R.id.Best_Tab_NoblessClassic);

            GotoBest.setOnClickListener(v -> GotoPage(TabName + " 전체 베스트", "&best=" + BestType + "&store=" + ETC_URL + "&token=" + TOKEN));
            GotoNobless.setOnClickListener(v -> GotoPage(TabName + " 노블레스 베스트", "&best=" + BestType + "&store=nobless" + ETC_URL + "&token=" + TOKEN));
            GotoPremium.setOnClickListener(v -> GotoPage(TabName + " 프리미엄 베스트", "&best=" + BestType + "&store=premium" + ETC_URL + "&token=" + TOKEN));
            GotoFree.setOnClickListener(v -> GotoPage(TabName + " 무료 베스트", "&best=" + BestType + "&store=series" + ETC_URL + "&token=" + TOKEN));
            GotoNew.setOnClickListener(v -> GotoPage(TabName + " 무료 베스트", "&best=" + BestType + "&store=lately" + ETC_URL + "&token=" + TOKEN));
            GotoFinish.setOnClickListener(v -> GotoPage(TabName + " 무료 베스트", "&best=" + BestType + "&store=finish" + ETC_URL + "&token=" + TOKEN));
            GotoNoblessClassic.setOnClickListener(v -> GotoPage(TabName + " 무료 베스트", "&best=" + BestType + "&store=nobless_classic" + ETC_URL + "&token=" + TOKEN));
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
                Book_Pagination.FavToggle(queue, item.getBookCode(), TOKEN);
            } else if (Value.equals("BookDetail")) {
                Intent intent = new Intent(requireContext().getApplicationContext(), Book_Detail_Cover.class);
                intent.putExtra("BookCode", String.format("%s", item.getBookCode()));
                intent.putExtra("TOKEN", String.format("%s", TOKEN));
                startActivity(intent);
            }
        });
    }

    public void GotoPage(String Title, String ETC) {
        Intent intent = new Intent(requireContext().getApplicationContext(), Book_Page_Etc.class);
        intent.putExtra("Title", String.format("%s", Title));
        intent.putExtra("TYPE", String.format("%s", "BEST"));
        intent.putExtra("API_URL", String.format("%s", "/v1/best/book.joa"));
        intent.putExtra("ETC_URL", String.format("%s", ETC));
        startActivity(intent);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
