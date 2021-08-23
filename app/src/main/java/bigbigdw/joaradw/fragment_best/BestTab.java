package bigbigdw.joaradw.fragment_best;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

import bigbigdw.joaradw.JOARADW;
import bigbigdw.joaradw.base.BookBaseFragment;
import bigbigdw.joaradw.etc.API;
import bigbigdw.joaradw.main.OLD_MainBookData;
import bigbigdw.joaradw.main.OLD_MainBookListData;
import bigbigdw.joaradw.main.TabViewModel;
import bigbigdw.joaradw.R;

public class BestTab extends BookBaseFragment {
    private final ArrayList<OLD_MainBookListData> items = new ArrayList<>();
    private static final String ARG_SECTION_NUMBER = "section_number";
    private TabViewModel tabViewModel;
    String bestType = "weekly";
    String userToken = "";
    String tabName = "";
    String etcUrl = "&orderby=cnt_best&offset=100&page=1";
    LinearLayout loadingLayout;
    NestedScrollView contentsLayout;
    JSONObject getuserinfo = null;
    TextView gotoBest;
    TextView gotoNobless;
    TextView gotoPremium;
    TextView gotoFree;
    TextView gotoNew;
    TextView gotoFinish;
    TextView gotoNoblessClassic;
    int index;

    public static BestTab newInstance(int index) {
        BestTab fragment = new BestTab();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabViewModel = new ViewModelProvider(this).get(TabViewModel.class);
        index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        tabViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_best_tab, container, false);


        loadingLayout = root.findViewById(R.id.LoadingLayout);
        contentsLayout = root.findViewById(R.id.ContentsLayout);
        gotoBest = root.findViewById(R.id.GotoBest);
        gotoNobless = root.findViewById(R.id.GotoNobless);
        gotoPremium = root.findViewById(R.id.GotoPremium);
        gotoFree = root.findViewById(R.id.GotoFree);
        gotoNew = root.findViewById(R.id.GotoNew);
        gotoFinish = root.findViewById(R.id.GotoFinish);
        gotoNoblessClassic = root.findViewById(R.id.GotoNoblessClassic);

        Handler handler = new Handler(Looper.myLooper());
        handler.postDelayed(() -> {
            loadingLayout.setVisibility(View.GONE);
            contentsLayout.setVisibility(View.VISIBLE);
        }, 500);

        checkToken();
        JOARADW app = (JOARADW) requireActivity().getApplicationContext();
        userToken = "&token=" + app.getToken();

        setLayout(root);

        return root;
    }

    public void setLayout(View root){

        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        MainBookListAdapterBest allAdapter = new MainBookListAdapterBest(items);
        MainBookListAdapterBest noblessAdapter = new MainBookListAdapterBest(items);
        MainBookListAdapterBest premiumAdapter = new MainBookListAdapterBest(items);
        MainBookListAdapterBest freeAdapter = new MainBookListAdapterBest(items);
        MainBookListAdapterBest seriesAdapter = new MainBookListAdapterBest(items);
        MainBookListAdapterBest finishAdapter = new MainBookListAdapterBest(items);
        MainBookListAdapterBest noblessClassicAdapter = new MainBookListAdapterBest(items);

        tabViewModel.getText().observe(getViewLifecycleOwner(), tabNum -> {
            switch (tabNum) {
                case "TAB1":
                    bestType = "realtime";
                    tabName = "실시간";
                    break;
                case "TAB2":
                    bestType = "today";
                    tabName = "투데이";
                    break;
                case "TAB3":
                    bestType = "weekly";
                    tabName = "주간";
                    break;
                default:
                    bestType = "monthly";
                    tabName = "월간";
                    break;
            }

            String resultEtc = etcUrl + userToken;
            String bestParam = "&best=" + bestType;

            bookBest(root, bestParam + "&store=" + resultEtc, R.id.Best_Tab_AllList, allAdapter, queue, R.id.Best_Tab_All);
            bookBest(root, bestParam + "&store=nobless" + resultEtc, R.id.Best_Tab_NoblessList, noblessAdapter, queue, R.id.Best_Tab_Nobless);
            bookBest(root, bestParam + "&store=premium" + resultEtc, R.id.Best_Tab_PremiumList, premiumAdapter, queue, R.id.Best_Tab_Premium);
            bookBest(root, bestParam + "&store=series" + resultEtc, R.id.Best_Tab_FreeList, freeAdapter, queue, R.id.Best_Tab_Free);
            bookBest(root, bestParam + "&store=lately" + resultEtc, R.id.Best_Tab_SeriesList, seriesAdapter, queue, R.id.Best_Tab_Series);
            bookBest(root, bestParam + "&store=finish" + resultEtc, R.id.Best_Tab_FinishList, finishAdapter, queue, R.id.Best_Tab_Finish);
            bookBest(root, bestParam + "&store=nobless_classic" + resultEtc, R.id.Best_Tab_NoblessClassicList, noblessClassicAdapter, queue, R.id.Best_Tab_NoblessClassic);

            gotoBest.setOnClickListener(v -> goToBookPageEtc(tabName + " 전체 베스트", API.BEST_BOOK_JOA, bestParam + "&store=" + resultEtc));
            gotoNobless.setOnClickListener(v -> goToBookPageEtc(tabName + "노블레스 베스트",API.BEST_BOOK_JOA,  bestParam + "&store=nobless" + resultEtc));
            gotoPremium.setOnClickListener(v -> goToBookPageEtc(tabName + "프리미엄 베스트", API.BEST_BOOK_JOA, bestParam + "&store=premium" + resultEtc));
            gotoFree.setOnClickListener(v -> goToBookPageEtc(tabName + "무료 베스트",API.BEST_BOOK_JOA,  bestParam + "&store=series" + resultEtc));
            gotoNew.setOnClickListener(v -> goToBookPageEtc(tabName + "최신 베스트",API.BEST_BOOK_JOA,  bestParam + "&store=lately" + resultEtc));
            gotoFinish.setOnClickListener(v -> goToBookPageEtc(tabName + "완결 베스트",API.BEST_BOOK_JOA,  bestParam + "&store=finish" + resultEtc));
            gotoNoblessClassic.setOnClickListener(v -> goToBookPageEtc(tabName + "노블레스 클래식 베스트", API.BEST_BOOK_JOA, bestParam + "&store=nobless_classic" + resultEtc));
        });
    }

    public void bookBest(View root, String etc, Integer recylerView, MainBookListAdapterBest adapter, RequestQueue queue, Integer integerWrap) {
        RecyclerView recyclerView = root.findViewById(recylerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        LinearLayout wrap = root.findViewById(integerWrap);
        adapter.setItems(new OLD_MainBookData().getData(API.BEST_BOOK_JOA, etc, queue, wrap, "BEST"));
        adapter.notifyDataSetChanged();

        adapter.setOnItemClickListener((v, position, value) -> {
            OLD_MainBookListData item = adapter.getItem(position);
            adapterListener(item, value, queue);
        });
    }
}
