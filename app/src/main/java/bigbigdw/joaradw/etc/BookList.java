package bigbigdw.joaradw.etc;

import android.content.res.AssetManager;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;

import bigbigdw.joaradw.main.OLD_MainBookListAdapterD;
import bigbigdw.joaradw.main.OLD_MainBookListAdapterA;
import bigbigdw.joaradw.main.OLD_MainBookListAdapterC;
import bigbigdw.joaradw.main.OLD_MainBookData;
import bigbigdw.joaradw.main.MainBookDataJSON;
import bigbigdw.joaradw.main.MainBookDataWebtoon;
import bigbigdw.joaradw.main.OLD_MainBookListAdapterB;


public interface BookList {

    static void bookListA(View root, String apiUrl, String etc, Integer recylerView, OLD_MainBookListAdapterA adapter, RequestQueue queue, Integer integer) {
        RecyclerView recyclerView = root.findViewById(recylerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        LinearLayout wrap = root.findViewById(integer);
        adapter.setItems(new OLD_MainBookData().getData(apiUrl, etc, queue, wrap, ""));
        adapter.notifyDataSetChanged();
    }

    static void bookListC(View root, String apiUrl, String etc, Integer recylerview, OLD_MainBookListAdapterC adapter, RequestQueue queue, Integer integer) {
        RecyclerView recyclerView = root.findViewById(recylerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        LinearLayout wrap = root.findViewById(integer);
        adapter.setItems(new OLD_MainBookData().getData(apiUrl, etc, queue, wrap, ""));
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    static void bookListD(View root, String apiUrl, String etc, Integer recylerview, OLD_MainBookListAdapterD adapter, RequestQueue queue, Integer integer) {
        RecyclerView recyclerView = root.findViewById(recylerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        LinearLayout wrap = root.findViewById(integer);
        adapter.setItems(new OLD_MainBookData().getData(apiUrl, etc, queue, wrap,""));
        adapter.notifyDataSetChanged();
    }

}
