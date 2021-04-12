package Bigbigdw.JoaraDW.Fragment_New;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

import java.util.ArrayList;

import Bigbigdw.JoaraDW.Main.Main_BookData;
import Bigbigdw.JoaraDW.Main.Main_BookData_JSON;
import Bigbigdw.JoaraDW.Main.Main_BookListData_A;
import Bigbigdw.JoaraDW.R;

public class New_Tab_ALL extends Fragment {
    private final Main_BookListAdapter_New NewBookListAdapter = new Main_BookListAdapter_New();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab_new_all, container, false);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        Number Page = 1;
        String API = "/v1/book/list.joa";
        String ETC = "&store=&orderby=redate&offset=25&page=" + Page + "&class=";
        RecyclerView recyclerView = root.findViewById(R.id.Main_NewBookList);
        NewBookList(root, API, ETC, Page, queue, recyclerView);

        return root;
    }

    public void NewBookList(View root, String API, String ETC, Number Num, RequestQueue queue, RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(NewBookListAdapter);
        LinearLayout wrap = root.findViewById(R.id.Tab_NewAll);
        NewBookListAdapter.setItems(new Main_BookData().getData(API, ETC, queue, wrap));
        NewBookListAdapter.notifyDataSetChanged();
    }
}
