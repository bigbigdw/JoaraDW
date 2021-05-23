package Bigbigdw.JoaraDW.Book_Detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import Bigbigdw.JoaraDW.Config;
import Bigbigdw.JoaraDW.Book_Pagination;
import Bigbigdw.JoaraDW.Etc.BookList;
import Bigbigdw.JoaraDW.Fragment_Main.Main_More_Aadapter;
import Bigbigdw.JoaraDW.Main.Main_BookListAdapter_A;
import Bigbigdw.JoaraDW.JOARADW;
import Bigbigdw.JoaraDW.Main.Main_BookListData;
import Bigbigdw.JoaraDW.R;


public class Detail_Tab_3 extends Fragment {
    private final Main_BookListAdapter_A BookListAdapter = new Main_BookListAdapter_A();
    private final Main_More_Aadapter NoticeAdapter = new Main_More_Aadapter();
    private final Main_More_Aadapter SurveyAdapter = new Main_More_Aadapter();
    private final Main_More_Aadapter SettingAdapter = new Main_More_Aadapter();
    LinearLayout Wrap, Cover, Blank, SettingList,NoticeList , SurveyList;
    String TOKEN = "" , ETC = "" , BOOKCODE, API_URL = "/v1/book/recommend_list_api.joa";
    TextView GotoBookNotice, GotoBookSetting, GotoBookSurvey;
    RequestQueue queue;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.book_detail_tab_3, container, false);

        JOARADW myApp = (JOARADW) requireActivity().getApplicationContext();
        BOOKCODE = myApp.getBookCode();

        Wrap = root.findViewById(R.id.TabWrap);
        Cover = root.findViewById(R.id.LoadingLayout);
        Blank = root.findViewById(R.id.BlankLayout);
        SettingList = root.findViewById(R.id.SettingList);
        NoticeList = root.findViewById(R.id.NoticeList);
        SurveyList = root.findViewById(R.id.SurveyList);
        GotoBookNotice = root.findViewById(R.id.GotoBookNotice);
        GotoBookSetting = root.findViewById(R.id.GotoBookSetting);
        GotoBookSurvey = root.findViewById(R.id.GotoBookSurvey);

        ETC = "&recommend_type=book_code&offset=50" + "&token=" + TOKEN + "&book_code=" + BOOKCODE + "&model_type=all&chapter_cnt=1&finish=&adult=";

        queue = Volley.newRequestQueue(requireActivity());

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

        NoticeAdapter(root, queue, NoticeList, BOOKCODE);
        SettingAdapter(root, queue, SettingList, BOOKCODE);
        SurveyAdapter(root, queue, SurveyList, BOOKCODE);

        GotoBookNotice.setOnClickListener(v -> GotoBookPage(Book_Detail_SubPage.class, "NOTICE_PAGE"));
        GotoBookSetting.setOnClickListener(v -> GotoBookPage(Book_Detail_SubPage.class, "SETTING_PAGE"));
        GotoBookSurvey.setOnClickListener(v -> GotoBookPage(Book_Detail_SubPage.class, "SURVEY_PAGE"));

        BookList.BookList_A(root, API_URL, ETC, R.id.BookDetail, BookListAdapter, queue, R.id.TabWrap);

        BookListAdapter.setOnItemClickListener((v, position, Value) -> {
            Main_BookListData item = BookListAdapter.getItem(position);
            if(Value.equals("FAV")){
                Book_Pagination.FavToggle(queue, item.getBookCode(), TOKEN);
            } else if (Value.equals("BookDetail")){
                Intent intent = new Intent(requireContext().getApplicationContext(), Book_Detail_Cover.class);
                intent.putExtra("BookCode",String.format("%s", item.getBookCode()));
                intent.putExtra("TOKEN",String.format("%s", TOKEN));
                startActivity(intent);
            }
        });

        return root;
    }

    public void GotoBookPage(Class BookPage, String PageValue){
        Intent intent = new Intent(requireContext().getApplicationContext(), BookPage);
        intent.putExtra("TOKEN", String.format("%s", TOKEN));
        intent.putExtra("BOOKCODE", String.format("%s", BOOKCODE));
        intent.putExtra("VALUE", String.format("%s", PageValue));
        startActivity(intent);
    }

    public void NoticeAdapter(View root, RequestQueue queue, LinearLayout NoticeList, String BookCode)
    {
        RecyclerView recyclerViewNotice = root.findViewById(R.id.Book_Detail_NoticeList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewNotice.setLayoutManager(manager);
        recyclerViewNotice.setAdapter(NoticeAdapter);
        NoticeAdapter.setItems(new Detail_Tab_3_Data().getData(queue, NoticeList, BookCode, "NOTICE"));
        NoticeAdapter.notifyDataSetChanged();
    }

    public void SettingAdapter(View root, RequestQueue queue, LinearLayout EventList, String BookCode)
    {
        RecyclerView recyclerViewSetting = root.findViewById(R.id.Book_Detail_SettingList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewSetting.setLayoutManager(manager);
        recyclerViewSetting.setAdapter(SettingAdapter);
        SettingAdapter.setItems(new Detail_Tab_3_Data().getData(queue, EventList, BookCode,"SETTING"));
        SettingAdapter.notifyDataSetChanged();
    }

    public void SurveyAdapter(View root, RequestQueue queue, LinearLayout EventList, String BookCode)
    {
        RecyclerView recyclerViewSurvey = root.findViewById(R.id.Book_Detail_SurveyList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewSurvey.setLayoutManager(manager);
        recyclerViewSurvey.setAdapter(SurveyAdapter);
        SurveyAdapter.setItems(new Detail_Tab_3_Data().getData(queue, EventList, BookCode, "SURVEY"));
        SurveyAdapter.notifyDataSetChanged();
    }
}