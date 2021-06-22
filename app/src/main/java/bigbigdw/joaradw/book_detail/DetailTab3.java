package bigbigdw.joaradw.book_detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import bigbigdw.joaradw.BookPagination;
import bigbigdw.joaradw.base.BookBaseFragment;
import bigbigdw.joaradw.book_viewer.ViewerSetting;
import bigbigdw.joaradw.etc.API;
import bigbigdw.joaradw.etc.BookList;
import bigbigdw.joaradw.fragment_main.MainMoreAadapter;
import bigbigdw.joaradw.main.MainBookListAdapterA;
import bigbigdw.joaradw.JOARADW;
import bigbigdw.joaradw.main.MainBookListData;
import bigbigdw.joaradw.R;


public class DetailTab3 extends BookBaseFragment {
    private final MainBookListAdapterA bookListAdapterA = new MainBookListAdapterA();
    private final MainMoreAadapter moreAadapter = new MainMoreAadapter();
    private final MainMoreAadapter surveyAdapter = new MainMoreAadapter();
    private final MainMoreAadapter settingAdapter = new MainMoreAadapter();
    LinearLayout wrap;
    LinearLayout cover;
    LinearLayout blank;
    LinearLayout settingList;
    LinearLayout noticeList;
    LinearLayout surveyList;
    LinearLayout wrapViewerSetting;
    String userToken = "";
    String etc = "";
    String bookcode;
    TextView gotoBookNotice;
    TextView gotoBookSetting;
    TextView gotoBookSurvey;
    RequestQueue queue;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.book_detail_tab_3, container, false);

        JOARADW myApp = (JOARADW) requireActivity().getApplicationContext();
        bookcode = myApp.getBookCode();

        wrap = root.findViewById(R.id.TabWrap);
        cover = root.findViewById(R.id.LoadingLayout);
        blank = root.findViewById(R.id.BlankLayout);
        settingList = root.findViewById(R.id.SettingList);
        noticeList = root.findViewById(R.id.NoticeList);
        surveyList = root.findViewById(R.id.SurveyList);
        gotoBookNotice = root.findViewById(R.id.GotoBookNotice);
        gotoBookSetting = root.findViewById(R.id.GotoBookSetting);
        gotoBookSurvey = root.findViewById(R.id.GotoBookSurvey);
        wrapViewerSetting = root.findViewById(R.id.WrapViewerSetting);

        etc = "&recommend_type=book_code&offset=50" + "&token=" + userToken + "&book_code=" + bookcode + "&model_type=all&chapter_cnt=1&finish=&adult=";

        queue = Volley.newRequestQueue(requireActivity());

        checkToken();
        JOARADW app = (JOARADW) requireActivity().getApplicationContext();
        userToken = "&token=" + app.getToken();

        noticeAdapter(root, queue, noticeList);
        settingAdapter(root, queue, settingList);
        surveyAdapter(root, queue, surveyList);

        gotoBookNotice.setOnClickListener(v -> gotoBookPage("NOTICE_PAGE"));
        gotoBookSetting.setOnClickListener(v -> gotoBookPage("SETTING_PAGE"));
        gotoBookSurvey.setOnClickListener(v -> gotoBookPage("SURVEY_PAGE"));
        wrapViewerSetting.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext().getApplicationContext(), ViewerSetting.class);
            startActivity(intent);
        });

        BookList.bookListA(root, API.RECOMMEND_LIST_API_JOA, etc, R.id.BookDetail, bookListAdapterA, queue, R.id.TabWrap);

        bookListAdapterA.setOnItemClickListener((v, position, value) -> {
            MainBookListData item = bookListAdapterA.getItem(position);
            if(value.equals("FAV")){
                BookPagination.favToggle(queue, item.getBookCode(), userToken);
            } else if (value.equals("BookDetail")){
                Intent intent = new Intent(requireContext().getApplicationContext(), BookDetailCover.class);
                intent.putExtra("BookCode",String.format("%s", item.getBookCode()));
                intent.putExtra("TOKEN",String.format("%s", userToken));
                startActivity(intent);
            }
        });

        return root;
    }

    public void gotoBookPage(String pageValue){
        Intent intent = new Intent(requireContext().getApplicationContext(), BookDetailSubPage.class);
        intent.putExtra("TOKEN", String.format("%s", userToken));
        intent.putExtra("BOOKCODE", String.format("%s", bookcode));
        intent.putExtra("VALUE", String.format("%s", pageValue));
        startActivity(intent);
    }

    public void noticeAdapter(View root, RequestQueue queue, LinearLayout noticeList)
    {
        RecyclerView recyclerViewNotice = root.findViewById(R.id.Book_Detail_NoticeList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewNotice.setLayoutManager(manager);
        recyclerViewNotice.setAdapter(moreAadapter);
        moreAadapter.setItems(new DetailTab3Data().getData(queue, noticeList,  "NOTICE"));
        moreAadapter.notifyDataSetChanged();
    }

    public void settingAdapter(View root, RequestQueue queue, LinearLayout eventList)
    {
        RecyclerView recyclerViewSetting = root.findViewById(R.id.Book_Detail_SettingList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewSetting.setLayoutManager(manager);
        recyclerViewSetting.setAdapter(settingAdapter);
        settingAdapter.setItems(new DetailTab3Data().getData(queue, eventList, "SETTING"));
        settingAdapter.notifyDataSetChanged();
    }

    public void surveyAdapter(View root, RequestQueue queue, LinearLayout eventList)
    {
        RecyclerView recyclerViewSurvey = root.findViewById(R.id.Book_Detail_SurveyList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewSurvey.setLayoutManager(manager);
        recyclerViewSurvey.setAdapter(surveyAdapter);
        surveyAdapter.setItems(new DetailTab3Data().getData(queue, eventList,  "SURVEY"));
        surveyAdapter.notifyDataSetChanged();
    }
}