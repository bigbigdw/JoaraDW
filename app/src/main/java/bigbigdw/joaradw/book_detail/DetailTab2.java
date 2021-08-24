package bigbigdw.joaradw.book_detail;

import android.content.Intent;
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


import java.util.ArrayList;
import bigbigdw.joaradw.BookPagination;
import bigbigdw.joaradw.base.BookBaseFragment;
import bigbigdw.joaradw.etc.API;
import bigbigdw.joaradw.main.OLD_MainBookListAdapterC;
import bigbigdw.joaradw.JOARADW;
import bigbigdw.joaradw.main.OLD_MainBookListData;
import bigbigdw.joaradw.R;


public class DetailTab2 extends BookBaseFragment {
    private OLD_MainBookListAdapterC adapter;
    private RecyclerView recyclerView;
    private final ArrayList<OLD_MainBookListData> items = new ArrayList<>();
    LinearLayout wrap;
    LinearLayout cover;
    LinearLayout blank;
    String userToken = "";
    String etc = "";
    String bookcode = "";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.book_detail_tab_2, container, false);

        JOARADW myApp = (JOARADW) requireActivity().getApplicationContext();
        bookcode = myApp.getBookCode();

        recyclerView = root.findViewById(R.id.BookDetail);
        wrap = root.findViewById(R.id.TabWrap);
        cover = root.findViewById(R.id.LoadingLayout);
        blank = root.findViewById(R.id.BlankLayout);

        checkToken();
        JOARADW app = (JOARADW) requireActivity().getApplicationContext();
        userToken = "&token=" + app.getToken();

        etc = "&book_code=" + bookcode + "&token=" + userToken + "&offset=25";

        RequestQueue queue = Volley.newRequestQueue(requireActivity());

        BookPagination.populateData(API.BOOK_OTHER_JOA, etc, queue, wrap, items, cover, blank);
        initAdapter();

        adapter.setOnItemClickListener((v, position, value) -> {
            OLD_MainBookListData item = adapter.getItem(position);
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

    private void initAdapter() {
        adapter = new OLD_MainBookListAdapterC(items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
}