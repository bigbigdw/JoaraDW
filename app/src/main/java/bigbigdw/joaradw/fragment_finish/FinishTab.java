package bigbigdw.joaradw.fragment_finish;

import android.content.Intent;
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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;



import java.util.ArrayList;

import bigbigdw.joaradw.JOARADW;
import bigbigdw.joaradw.base.BookBaseFragment;
import bigbigdw.joaradw.etc.API;
import bigbigdw.joaradw.book_detail.BookDetailCover;
import bigbigdw.joaradw.BookPagination;
import bigbigdw.joaradw.etc.BookList;
import bigbigdw.joaradw.main.OLD_MainBookListAdapterC;
import bigbigdw.joaradw.main.OLD_MainBookListData;
import bigbigdw.joaradw.main.TabViewModel;
import bigbigdw.joaradw.R;

public class FinishTab extends BookBaseFragment {
    private final ArrayList<OLD_MainBookListData> items = new ArrayList<>();
    private static final String ARG_SECTION_NUMBER = "section_number";
    private TabViewModel tabViewModel;
    String finishType = "redate";
    String userToken = "";
    String tokenParam;
    String etcUrl = "&category=0&offset=10&page=1&store=finish&orderby=";
    LinearLayout loadingLayout;
    NestedScrollView contentsLayout;
    TextView goToAll;
    TextView gotoPremium;
    TextView gotoNobless;

    public static FinishTab newInstance(int index) {
        FinishTab fragment = new FinishTab();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabViewModel = new ViewModelProvider(this).get(TabViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        tabViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_finish_tab, container, false);

        loadingLayout = root.findViewById(R.id.LoadingLayout);
        contentsLayout = root.findViewById(R.id.ContentsLayout);
        goToAll = root.findViewById(R.id.GoToAll);
        gotoPremium = root.findViewById(R.id.GotoPremium);
        gotoNobless = root.findViewById(R.id.GotoNobless);

        Handler handler = new Handler(Looper.myLooper());
        handler.postDelayed(() -> {
            loadingLayout.setVisibility(View.GONE);
            contentsLayout.setVisibility(View.VISIBLE);
        }, 500);

        checkToken();
        JOARADW app = (JOARADW) requireActivity().getApplicationContext();
        userToken = app.getToken();
        tokenParam = "&token=" + userToken;

        setLayout(root);

        return root;
    }

    public void setLayout(View root){
        RequestQueue queue = Volley.newRequestQueue(requireActivity());
        OLD_MainBookListAdapterC newAdapter = new OLD_MainBookListAdapterC(items);
        OLD_MainBookListAdapterC noblessAdapter = new OLD_MainBookListAdapterC(items);
        OLD_MainBookListAdapterC premiumAdapter = new OLD_MainBookListAdapterC(items);
        String resultEtcUrl = etcUrl + finishType + tokenParam;

        tabViewModel.getText().observe(getViewLifecycleOwner(), tabNum -> {
            switch (tabNum) {
                case "TAB1":
                    finishType = "redate";
                    break;
                case "TAB2":
                    finishType = "cnt_page_read";
                    break;
                case "TAB3":
                    finishType = "cnt_favorite";
                    break;
                default:
                    finishType = "cnt_recom";
                    break;
            }
            bookFinish(root, resultEtcUrl, R.id.Finish_Tab_NewList, newAdapter, queue, R.id.Finish_Tab_New);
            bookFinish(root, resultEtcUrl, R.id.Finish_Tab_NoblessList, noblessAdapter, queue, R.id.Finish_Tab_Nobless);
            bookFinish(root, resultEtcUrl, R.id.Finish_Tab_PremiumList, premiumAdapter, queue, R.id.Finish_Tab_Premium);
        });
        goToAll.setOnClickListener(v -> goToBookPageEtc("최신작 전체 완결", API.BOOK_LIST_JOA, resultEtcUrl));
        gotoNobless.setOnClickListener(v -> goToBookPageEtc("최신작 노블레스 완결", API.BOOK_LIST_JOA, resultEtcUrl));
        gotoPremium.setOnClickListener(v -> goToBookPageEtc("최신작 프리미엄 완결", API.BOOK_LIST_JOA, resultEtcUrl));
    }

    public void bookFinish(View root, String etc, Integer recylerView, OLD_MainBookListAdapterC adapter, RequestQueue queue, Integer wrap) {
        BookList.bookListC(root, API.BOOK_LIST_JOA, etc, recylerView, adapter, queue, wrap);

        adapter.setOnItemClickListener((v, position, value) -> {
            OLD_MainBookListData item = adapter.getItem(position);
            if (value.equals("FAV")) {
                BookPagination.favToggle(queue, item.getBookCode(), userToken);
            } else if (value.equals("BookDetail")) {
                Intent intent = new Intent(requireContext().getApplicationContext(), BookDetailCover.class);
                intent.putExtra("BookCode", String.format("%s", item.getBookCode()));
                intent.putExtra("TOKEN", String.format("%s", userToken));
                startActivity(intent);
            }
        });
    }
}
