package Bigbigdw.JoaraDW.Fragment_New;

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

import java.util.ArrayList;

import Bigbigdw.JoaraDW.Main.Main_BookListData;
import Bigbigdw.JoaraDW.R;

public class New_Tab_Nobless extends Fragment {
    private Main_BookListAdapter_New NewBookListAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Main_BookListData> items = new ArrayList<>();
    LinearLayout Wrap, Cover;
    String Store="nobless";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new_tab_nobless, container, false);

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String API = "/v1/book/list.joa";
        String ETC = "&store="+ Store + "&orderby=redate&offset=25&page=" + 1 + "&class=";
        recyclerView = root.findViewById(R.id.Main_NewBookList_Nobless);
        Wrap = root.findViewById(R.id.Tab_NewNoblessWrap);
        Cover = root.findViewById(R.id.LoadingLayout);

        New_Book_Pagination.populateData(API, ETC, queue, Wrap, items, Cover);
        initAdapter();
        New_Book_Pagination.initScrollListener(API, queue, Wrap, items, NewBookListAdapter, recyclerView, Store);

        return root;
    }

    private void initAdapter() {
        NewBookListAdapter = new Main_BookListAdapter_New(items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(NewBookListAdapter);
    }
}
