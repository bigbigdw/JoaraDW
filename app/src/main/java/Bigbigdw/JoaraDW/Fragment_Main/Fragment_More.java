package Bigbigdw.JoaraDW.Fragment_Main;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Queue;

import Bigbigdw.JoaraDW.BookList.Book_Page_Etc;
import Bigbigdw.JoaraDW.BookList.Fragment_Main;
import Bigbigdw.JoaraDW.Book_Detail.Book_Detail_Cover;
import Bigbigdw.JoaraDW.Config;
import Bigbigdw.JoaraDW.Main.Main;
import Bigbigdw.JoaraDW.Main.Main_BookData_JSON;
import Bigbigdw.JoaraDW.R;

public class Fragment_More extends Fragment {

    private final Main_More_Aadapter NoticeAdapter = new Main_More_Aadapter();
    private final Main_More_Aadapter EventAdapter = new Main_More_Aadapter();
    LinearLayout NoticeList, EventList, Wrap77Fes, WrapKidamu, WrapNOTY, WrapPromised, WrapBookSnipe, WrapRecommend, WrapAward, WrapMillion, GotoBlog, GotoFaceBook, GotoInstagram;
    Bundle bundle;
    String USERTOKEN = "";
    JSONObject GETUSERINFO = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main_more, container, false);
        NoticeList = root.findViewById(R.id.NoticeList);
        EventList = root.findViewById(R.id.EventList);
        Wrap77Fes = root.findViewById(R.id.Wrap77Fes);
        WrapKidamu = root.findViewById(R.id.WrapKidamu);
        WrapNOTY = root.findViewById(R.id.WrapNOTY);
        WrapPromised = root.findViewById(R.id.WrapPromised);
        WrapBookSnipe = root.findViewById(R.id.WrapBookSnipe);
        WrapRecommend = root.findViewById(R.id.WrapRecommend);
        WrapAward = root.findViewById(R.id.WrapAward);
        WrapMillion = root.findViewById(R.id.WrapMillion);
        GotoBlog = root.findViewById(R.id.GotoBlog);
        GotoFaceBook = root.findViewById(R.id.GotoFaceBook);
        GotoInstagram = root.findViewById(R.id.GotoInstagram);
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        NoticeAdapter(root, queue, NoticeList);
        EventAdapter(root, queue, EventList);

        if(Config.GETUSERINFO() != null){
            GETUSERINFO = Config.GETUSERINFO();
            JSONObject UserInfo;
            try {
                UserInfo = GETUSERINFO.getJSONObject("user");
                USERTOKEN = UserInfo.getString("token");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        bundle = new Bundle();

        Wrap77Fes.setOnClickListener(v -> GotoFragment_New(1));
        WrapKidamu.setOnClickListener(v -> GotoFragment_New(2));
        WrapNOTY.setOnClickListener(v -> GotoFragment_New(3));
        WrapPromised.setOnClickListener(v -> GotoFragment_New(4));

        WrapBookSnipe.setOnClickListener(v -> GotoBookPage("취향 저격", "/v1/book/recommend_list_api.joa", "&token=" + USERTOKEN + "&book_code=&offset=50"));
        WrapRecommend.setOnClickListener(v -> GotoBookPage("추천 소설", "/v1/home/list.joa", "&token=" + USERTOKEN + "&page=1&section_mode=recommend_book&offset=50"));
        WrapAward.setOnClickListener(v -> GotoBookPage("수상작", "/v1/book/list.joa", "&token=" + USERTOKEN + "&section_mode=contest_free_award&offset=50"));
        WrapMillion.setOnClickListener(v -> GotoBookPage("천만 인증", "/v1/home/list.joa", "&token=" + USERTOKEN + "&section_mode=page_read_book&offset=50"));

        GotoBlog.setOnClickListener(v -> GotoURL("https://m.blog.naver.com/joarablog"));
        GotoFaceBook.setOnClickListener(v -> GotoURL("https://www.facebook.com/Joara.page/"));
        GotoInstagram.setOnClickListener(v -> GotoURL("https://www.instagram.com/joara_official/"));

        return root;
    }

    public void GotoFragment_New(int TabNum){
        bundle.putInt("TabNum", TabNum);
        NavHostFragment.findNavController(Fragment_More.this)
                .navigate(R.id.action_Fragment_More_to_Fragment_New, bundle);
    }

    public void GotoBookPage(String Title, String API_URL, String ETC_URL){
        Intent intent = new Intent(requireContext().getApplicationContext(), Book_Page_Etc.class);
        intent.putExtra("Title", String.format("%s", Title));
        intent.putExtra("API_URL", String.format("%s", API_URL));
        intent.putExtra("ETC_URL", String.format("%s", ETC_URL));
        startActivity(intent);
    }

    public void GotoURL(String URL){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
        startActivity(intent);
    }


    public void NoticeAdapter(View root, RequestQueue queue, LinearLayout NoticeList)
    {
        RecyclerView recyclerViewFestival = root.findViewById(R.id.Main_More_noticeList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewFestival.setLayoutManager(manager);
        recyclerViewFestival.setAdapter(NoticeAdapter);
        NoticeAdapter.setItems(new Main_More_NoticeData().getData(queue, NoticeList));
        NoticeAdapter.notifyDataSetChanged();
    }

    public void EventAdapter(View root, RequestQueue queue, LinearLayout EventList)
    {
        RecyclerView recyclerViewFestival = root.findViewById(R.id.Main_More_eventList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewFestival.setLayoutManager(manager);
        recyclerViewFestival.setAdapter(EventAdapter);
        EventAdapter.setItems(new Main_More_EventData().getData(queue, EventList));
        EventAdapter.notifyDataSetChanged();
    }
}