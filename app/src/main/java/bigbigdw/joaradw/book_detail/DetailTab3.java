package bigbigdw.joaradw.book_detail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import bigbigdw.joaradw.BookPagination;
import bigbigdw.joaradw.base.BookBaseFragment;
import bigbigdw.joaradw.book_viewer.ViewerSetting;
import bigbigdw.joaradw.etc.API;
import bigbigdw.joaradw.etc.BookList;
import bigbigdw.joaradw.etc.HELPER;
import bigbigdw.joaradw.novel.AadapterMainMore;
import bigbigdw.joaradw.novel.OLD_MainBookListAdapterA;
import bigbigdw.joaradw.JOARADW;
import bigbigdw.joaradw.novel.OLD_MainBookListData;
import bigbigdw.joaradw.R;


public class DetailTab3 extends BookBaseFragment {
    private final OLD_MainBookListAdapterA bookListAdapterA = new OLD_MainBookListAdapterA();
    private final AadapterMainMore moreAadapter = new AadapterMainMore();
    private final AadapterMainMore surveyAdapter = new AadapterMainMore();
    private final AadapterMainMore settingAdapter = new AadapterMainMore();
    LinearLayout wrap;
    LinearLayout cover;
    LinearLayout blank;
    LinearLayout settingList;
    LinearLayout noticeList;
    LinearLayout surveyList;
    LinearLayout wrapViewerSetting;
    LinearLayout wrapSupportList;
    LinearLayout wrapBookReport;
    String userToken = "";
    String etc = "";
    String bookcode;
    TextView gotoBookNotice;
    TextView gotoBookSetting;
    TextView gotoBookSurvey;
    RequestQueue queue;
    JOARADW myApp;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.book_detail_tab_3, container, false);

        myApp = (JOARADW) requireActivity().getApplicationContext();
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
        wrapSupportList = root.findViewById(R.id.WrapSupportList);
        wrapBookReport = root.findViewById(R.id.WrapBookReport);

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

        wrapSupportList.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext().getApplicationContext(), BookSupportersCoupon.class);
            intent.putExtra("BookCode",String.format("%s", bookcode));
            startActivity(intent);
        });

        wrapBookReport.setOnClickListener(v->{
            if(app.getIsLogined()) {
                postBookReportCheck();
            } else {
                Toast.makeText(requireContext().getApplicationContext(), "로그인을 해야합니다.", Toast.LENGTH_SHORT).show();
            }

        });

        BookList.bookListA(root, API.RECOMMEND_LIST_API_JOA, etc, R.id.BookDetail, bookListAdapterA, queue, R.id.TabWrap);

        bookListAdapterA.setOnItemClickListener((v, position, value) -> {
            OLD_MainBookListData item = bookListAdapterA.getItem(position);
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

    /**
     * 이미 신고한 작품인지 검사
     */
    private void postBookReportCheck() {
        String resultURL = HELPER.API + API.BOARD_REPORT_CHECK + HELPER.ETC + "&token=" + myApp.getToken() + "&category=" + "0" + "&report_idx=" + myApp.getBookCode() + "&report_type=" + "literature" + "&penalty_id=" + myApp.getWriterID();

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, resultURL, null, response -> {
            Log.d("@@@@", response.toString());
            try {
                if (response.getString("status").equals("1")) {
                Intent intent = new Intent(requireContext().getApplicationContext(), BookReport.class);
                intent.putExtra("BookCode",String.format("%s", bookcode));
                intent.putExtra("Title",String.format("%s", myApp.getBookTitle()));
                intent.putExtra("Writer",String.format("%s", myApp.getWriterName()));
                intent.putExtra("Writer_Id",String.format("%s", myApp.getWriterID()));
                intent.putExtra("Token",String.format("%s", myApp.getToken()));
                startActivity(intent);
                } else {
                    Toast.makeText(requireContext().getApplicationContext(), "이미 신고한 작품입니다.", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d("LoginCheck", "에러!"));

        queue.add(jsonRequest);
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
        moreAadapter.setItems(new DetailTab3Data().getData(queue, noticeList,  "NOTICE", bookcode));
        moreAadapter.notifyDataSetChanged();
    }

    public void settingAdapter(View root, RequestQueue queue, LinearLayout eventList)
    {
        RecyclerView recyclerViewSetting = root.findViewById(R.id.Book_Detail_SettingList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewSetting.setLayoutManager(manager);
        recyclerViewSetting.setAdapter(settingAdapter);
        settingAdapter.setItems(new DetailTab3Data().getData(queue, eventList, "SETTING", bookcode));
        settingAdapter.notifyDataSetChanged();
    }

    public void surveyAdapter(View root, RequestQueue queue, LinearLayout eventList)
    {
        RecyclerView recyclerViewSurvey = root.findViewById(R.id.Book_Detail_SurveyList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewSurvey.setLayoutManager(manager);
        recyclerViewSurvey.setAdapter(surveyAdapter);
        surveyAdapter.setItems(new DetailTab3Data().getData(queue, eventList,  "SURVEY", bookcode));
        surveyAdapter.notifyDataSetChanged();
    }
}