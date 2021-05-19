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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Bigbigdw.JoaraDW.Book_Detail.Book_Detail_Cover;
import Bigbigdw.JoaraDW.Config;
import Bigbigdw.JoaraDW.Book_Pagination;
import Bigbigdw.JoaraDW.Main.Main_BookListData;
import Bigbigdw.JoaraDW.Main.Tab_ViewModel;
import Bigbigdw.JoaraDW.R;

public class Finish_Tab extends Fragment {
    private final ArrayList<Main_BookListData> items = new ArrayList<>();
    private static final String ARG_SECTION_NUMBER = "section_number";
    private Bigbigdw.JoaraDW.Main.Tab_ViewModel Tab_ViewModel;
    private RequestQueue queue;
    String FinishType = "redate", TOKEN = "", APIURL = "/v1/book/list.joa";
    LinearLayout LoadingLayout;
    NestedScrollView ContentsLayout;
    TextView GoToAll, GotoPremium, GotoNobless;

    public static Finish_Tab newInstance(int index) {
        Finish_Tab fragment = new Finish_Tab();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tab_ViewModel = new ViewModelProvider(this).get(Tab_ViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        Tab_ViewModel.setIndex(index);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_finish_tab, container, false);
        queue = Volley.newRequestQueue(getActivity());

        LoadingLayout = root.findViewById(R.id.LoadingLayout);
        ContentsLayout = root.findViewById(R.id.ContentsLayout);
        GoToAll = root.findViewById(R.id.GoToAll);
        GotoPremium = root.findViewById(R.id.GotoPremium);
        GotoNobless = root.findViewById(R.id.GotoNobless);

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

        Main_BookListAdapter_C newAdapter = new Main_BookListAdapter_C(items);
        Main_BookListAdapter_C noblessAdapter = new Main_BookListAdapter_C(items);
        Main_BookListAdapter_C premiumAdapter = new Main_BookListAdapter_C(items);

        Tab_ViewModel.getText().observe(getViewLifecycleOwner(), TabNum -> {
            switch (TabNum) {
                case "TAB1":
                    FinishType = "redate";
                    break;
                case "TAB2":
                    FinishType = "cnt_page_read";
                    break;
                case "TAB3":
                    FinishType = "cnt_favorite";
                    break;
                default:
                    FinishType = "cnt_recom";
                    break;
            }
            BookFinish(root, APIURL, "&category=0&store=finish&orderby=" + FinishType + "&offset=10&page=1" + "&token=" + TOKEN, R.id.Finish_Tab_NewList, newAdapter, queue, R.id.Finish_Tab_New);
            BookFinish(root, APIURL, "&category=0&store=nobless_finish&orderby=" + FinishType + "&offset=10&page=1" + "&token=" + TOKEN, R.id.Finish_Tab_NoblessList, noblessAdapter, queue, R.id.Finish_Tab_Nobless);
            BookFinish(root, APIURL, "&category=0&store=premium_finish&orderby=" + FinishType + "&offset=10&page=1" + "&token=" + TOKEN, R.id.Finish_Tab_PremiumList, premiumAdapter, queue, R.id.Finish_Tab_Premium);
        });

        GoToAll.setOnClickListener(v -> GotoActivity("최신작 전체 완결", "&category=0&store=finish&orderby=" + FinishType + "&offset=25&page=1" + "&token=" + TOKEN));
        GotoNobless.setOnClickListener(v -> GotoActivity("최신작 노블레스 완결", "&category=0&store=nobless_finish&orderby=" + FinishType + "&offset=25&page=1" + "&token=" + TOKEN));
        GotoPremium.setOnClickListener(v -> GotoActivity("최신작 프리미엄 완결", "&category=0&store=premium_finish&orderby=" + FinishType + "&offset=25&page=1" + "&token=" + TOKEN));

        return root;
    }

    public void BookFinish(View root, String API_URL, String ETC, Integer RecylerView, Main_BookListAdapter_C Adapter, RequestQueue queue, Integer Wrap) {
        BookList.BookList_C(root, API_URL, ETC, RecylerView, Adapter, queue, Wrap);

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

    public void GotoActivity(String Title, String ETC_URL) {
        Intent intent = new Intent(requireContext().getApplicationContext(), Book_Page_Etc.class);
        intent.putExtra("Title", String.format("%s", Title));
        intent.putExtra("TYPE", String.format("%s", "FINISH"));
        intent.putExtra("API_URL", String.format("%s", "/v1/book/list.joa"));
        intent.putExtra("ETC_URL", String.format("%s", ETC_URL));
        startActivity(intent);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("onDestroyView", "파괴!");
    }
}
