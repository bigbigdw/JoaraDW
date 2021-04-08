package Bigbigdw.JoaraDW.Fragment_Main;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Queue;

import Bigbigdw.JoaraDW.R;

public class Fragment_More extends Fragment {

    private final Main_More_Aadapter NoticeAdapter = new Main_More_Aadapter();
    private final Main_More_Aadapter EventAdapter = new Main_More_Aadapter();
    private RequestQueue queue;
    LinearLayout NoticeList, EventList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main_more, container, false);
        NoticeList = root.findViewById(R.id.NoticeList);
        EventList = root.findViewById(R.id.EventList);
        queue = Volley.newRequestQueue(getActivity());

        NoticeAdapter(root, queue, NoticeList);
        EventAdapter(root, queue, EventList);

        return root;
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