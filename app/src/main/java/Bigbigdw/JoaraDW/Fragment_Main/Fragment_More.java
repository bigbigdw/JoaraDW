package Bigbigdw.JoaraDW.Fragment_Main;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import Bigbigdw.JoaraDW.R;

public class Fragment_More extends Fragment {

    private final Main_More_Aadapter NoticeAdapter = new Main_More_Aadapter();
    private final Main_More_Aadapter EventAdapter = new Main_More_Aadapter();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main_more, container, false);
        AssetManager assetManager = getActivity().getAssets();

        EventAdapter(root, assetManager, "Event_List.json");
        NoticeAdapter(root, assetManager, "Notice_List.json");

        return root;
    }

    public void NoticeAdapter(View root, AssetManager assetManager, String Data)
    {
        RecyclerView recyclerViewFestival = root.findViewById(R.id.Main_More_noticeList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewFestival.setLayoutManager(manager);
        recyclerViewFestival.setAdapter(NoticeAdapter);
        NoticeAdapter.setItems(new Main_More_NoticeData().getData(assetManager, Data));
        NoticeAdapter.notifyDataSetChanged();
    }

    public void EventAdapter(View root, AssetManager assetManager, String Data)
    {
        RecyclerView recyclerViewFestival = root.findViewById(R.id.Main_More_eventList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewFestival.setLayoutManager(manager);
        recyclerViewFestival.setAdapter(EventAdapter);
        EventAdapter.setItems(new Main_More_EventData().getData(assetManager, Data));
        EventAdapter.notifyDataSetChanged();
    }
}