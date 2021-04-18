package Bigbigdw.JoaraDW.Fragment_Finish;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


import Bigbigdw.JoaraDW.Main.Main_BookListAdapter_C;
import Bigbigdw.JoaraDW.R;

public class Finish_Tab_New extends Fragment {
    private final Main_BookListAdapter_Finish NewAdapter = new Main_BookListAdapter_Finish();
    private final Main_BookListAdapter_Finish NoblessAdapter = new Main_BookListAdapter_Finish();
    private final Main_BookListAdapter_Finish PremiumAdapter = new Main_BookListAdapter_Finish();

    private RequestQueue queue;
    String FinishType = "redate";
    LinearLayout LoadingLayout;
    NestedScrollView ContentsLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_finish_tab, container, false);
        queue = Volley.newRequestQueue(getActivity());

        LoadingLayout = root.findViewById(R.id.LoadingLayout);
        ContentsLayout = root.findViewById(R.id.ContentsLayout);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            LoadingLayout.setVisibility(View.GONE);
            ContentsLayout.setVisibility(View.VISIBLE);
        }, 500);

        BookFinish(root, "/v1/book/list.joa", "&category=0&store=finish&orderby=" + FinishType + "&offset=25&page=1", R.id.Finish_Tab_NewList, NewAdapter, queue, R.id.Finish_Tab_New);
        BookFinish(root, "/v1/book/list.joa", "&category=0&store=nobless_finish&orderby=" + FinishType + "&offset=25&page=1", R.id.Finish_Tab_NoblessList, NoblessAdapter, queue, R.id.Finish_Tab_Nobless);
        BookFinish(root, "/v1/book/list.joa", "&category=0&store=premium_finish&orderby=" + FinishType + "&offset=25&page=1", R.id.Finish_Tab_PremiumList, PremiumAdapter, queue, R.id.Finish_Tab_Premium);
        return root;
    }

    public void BookFinish(View root, String API_URL, String ETC, Integer RecylerView, Main_BookListAdapter_Finish Adapter, RequestQueue queue, Integer Wrap) {
        RecyclerView recyclerView = root.findViewById(RecylerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(Adapter);
        LinearLayout wrap = root.findViewById(Wrap);
        Adapter.setItems(new Finish_BookData().getData(API_URL, ETC, queue, wrap));
        Adapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("onDestroyView", "파괴!");
    }
}
