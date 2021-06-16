package bigbigdw.joaradw.fragment_main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import bigbigdw.joaradw.JOARADW;
import bigbigdw.joaradw.R;
import bigbigdw.joaradw.base.BookBaseFragment;
import bigbigdw.joaradw.etc.API;

public class FragmentMore extends BookBaseFragment {

    private final MainMoreAadapter noticeAdapter = new MainMoreAadapter();
    private final MainMoreAadapter eventAdapter = new MainMoreAadapter();
    LinearLayout noticeList;
    LinearLayout eventList;
    LinearLayout wrap77Fes;
    LinearLayout wrapKidamu;
    LinearLayout wrapNOTY;
    LinearLayout wrapPromised;
    LinearLayout wrapBookSnipe;
    LinearLayout wrapRecommend;
    LinearLayout wrapAward;
    LinearLayout wrapMillion;
    LinearLayout gotoBlog;
    LinearLayout gotoFaceBook;
    LinearLayout gotoInstagram;
    String userToken = "";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main_more, container, false);
        noticeList = root.findViewById(R.id.NoticeList);
        eventList = root.findViewById(R.id.EventList);
        wrap77Fes = root.findViewById(R.id.Wrap77Fes);
        wrapKidamu = root.findViewById(R.id.WrapKidamu);
        wrapNOTY = root.findViewById(R.id.WrapNOTY);
        wrapPromised = root.findViewById(R.id.WrapPromised);
        wrapBookSnipe = root.findViewById(R.id.WrapBookSnipe);
        wrapRecommend = root.findViewById(R.id.WrapRecommend);
        wrapAward = root.findViewById(R.id.WrapAward);
        wrapMillion = root.findViewById(R.id.WrapMillion);
        gotoBlog = root.findViewById(R.id.GotoBlog);
        gotoFaceBook = root.findViewById(R.id.GotoFaceBook);
        gotoInstagram = root.findViewById(R.id.GotoInstagram);
        RequestQueue queue = Volley.newRequestQueue(requireActivity());

        noticeAdapter(root, queue, noticeList);
        eventAdapter(root, queue, eventList);

        JOARADW app = (JOARADW) requireActivity().getApplicationContext();
        userToken = "&token=" +  app.getToken();

        wrap77Fes.setOnClickListener(v -> gotoMore(1 ,R.id.action_Fragment_More_to_Fragment_New, FragmentMore.this));
        wrapKidamu.setOnClickListener(v -> gotoMore(2,R.id.action_Fragment_More_to_Fragment_New, FragmentMore.this));
        wrapNOTY.setOnClickListener(v -> gotoMore(3,R.id.action_Fragment_More_to_Fragment_New, FragmentMore.this));
        wrapPromised.setOnClickListener(v -> gotoMore(4,R.id.action_Fragment_More_to_Fragment_New, FragmentMore.this));

        wrapBookSnipe.setOnClickListener(v -> goToBookPageEtc("취향 저격", API.BOOK_RECOMMEND_LIST_API_JOA, userToken + "&book_code=&offset=50"));
        wrapRecommend.setOnClickListener(v -> goToBookPageEtc("추천 소설", API.HOME_LIST_JOA, userToken + "&page=1&section_mode=recommend_book&offset=50"));
        wrapAward.setOnClickListener(v -> goToBookPageEtc("수상작", API.BOOK_LIST_JOA, userToken + "&section_mode=contest_free_award&offset=50"));
        wrapMillion.setOnClickListener(v -> goToBookPageEtc("천만 인증", API.HOME_LIST_JOA, userToken + "&section_mode=page_read_book&offset=50"));

        gotoBlog.setOnClickListener(v -> gotoURL("https://m.blog.naver.com/joarablog"));
        gotoFaceBook.setOnClickListener(v -> gotoURL("https://www.facebook.com/Joara.page/"));
        gotoInstagram.setOnClickListener(v -> gotoURL("https://www.instagram.com/joara_official/"));

        return root;
    }

    public void noticeAdapter(View root, RequestQueue queue, LinearLayout noticeList)
    {
        RecyclerView recyclerViewFestival = root.findViewById(R.id.Main_More_noticeList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewFestival.setLayoutManager(manager);
        recyclerViewFestival.setAdapter(noticeAdapter);
        noticeAdapter.setItems(new MainMoreNoticeData().getData(queue, noticeList));
        noticeAdapter.notifyDataSetChanged();
    }

    public void eventAdapter(View root, RequestQueue queue, LinearLayout eventList)
    {
        RecyclerView recyclerViewFestival = root.findViewById(R.id.Main_More_eventList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewFestival.setLayoutManager(manager);
        recyclerViewFestival.setAdapter(eventAdapter);
        eventAdapter.setItems(new MainMoreEventData().getData(queue, eventList));
        eventAdapter.notifyDataSetChanged();
    }
}