package Bigbigdw.JoaraDW.Fragment_Main;

import android.content.Intent;
import android.content.res.AssetManager;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Queue;

import Bigbigdw.JoaraDW.BookList.Fragment_Main;
import Bigbigdw.JoaraDW.Main.Main;
import Bigbigdw.JoaraDW.R;

public class Fragment_More extends Fragment {

    private final Main_More_Aadapter NoticeAdapter = new Main_More_Aadapter();
    private final Main_More_Aadapter EventAdapter = new Main_More_Aadapter();
    private RequestQueue queue;
    LinearLayout NoticeList, EventList;
    Bundle bundle;
    LinearLayout Wrap77Fes, WrapKidamu, WrapNOTY, WrapPromised;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main_more, container, false);
        NoticeList = root.findViewById(R.id.NoticeList);
        EventList = root.findViewById(R.id.EventList);
        queue = Volley.newRequestQueue(getActivity());

        NoticeAdapter(root, queue, NoticeList);
        EventAdapter(root, queue, EventList);

        Wrap77Fes = root.findViewById(R.id.Wrap77Fes);
        WrapKidamu = root.findViewById(R.id.WrapKidamu);
        WrapNOTY = root.findViewById(R.id.WrapNOTY);
        WrapPromised = root.findViewById(R.id.WrapPromised);

        bundle = new Bundle();

        Wrap77Fes.setOnClickListener(v -> {
            bundle.putInt("TabNum", 1);
            NavHostFragment.findNavController(Fragment_More.this)
                    .navigate(R.id.action_Fragment_More_to_Fragment_New, bundle);
        });

        WrapKidamu.setOnClickListener(v -> {
            bundle.putInt("TabNum", 2);
            NavHostFragment.findNavController(Fragment_More.this)
                    .navigate(R.id.action_Fragment_More_to_Fragment_New, bundle);
        });

        WrapNOTY.setOnClickListener(v -> {
            bundle.putInt("TabNum", 3);
            NavHostFragment.findNavController(Fragment_More.this)
                    .navigate(R.id.action_Fragment_More_to_Fragment_New, bundle);
        });

        WrapPromised.setOnClickListener(v -> {
            bundle.putInt("TabNum", 4);
            NavHostFragment.findNavController(Fragment_More.this)
                    .navigate(R.id.action_Fragment_More_to_Fragment_New, bundle);
        });

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