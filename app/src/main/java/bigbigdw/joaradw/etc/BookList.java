package bigbigdw.joaradw.etc;

import android.content.res.AssetManager;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;

import bigbigdw.joaradw.main.Main_BookListAdapter_D;
import bigbigdw.joaradw.main.MainBookListAdapterA;
import bigbigdw.joaradw.main.Main_BookListAdapter_C;
import bigbigdw.joaradw.main.Main_BookData;
import bigbigdw.joaradw.main.Main_BookData_JSON;
import bigbigdw.joaradw.main.Main_BookData_Webtoon;
import bigbigdw.joaradw.main.Main_BookListAdapter_B;


public interface BookList {

    static void bookListA(View root, String apiUrl, String etc, Integer recylerView, MainBookListAdapterA adapter, RequestQueue queue, Integer integer) {
        RecyclerView recyclerView = root.findViewById(recylerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        LinearLayout wrap = root.findViewById(integer);
        adapter.setItems(new Main_BookData().getData(apiUrl, etc, queue, wrap, ""));
        adapter.notifyDataSetChanged();
    }

    static void bookListB(View root, AssetManager assetManager, String bookType, Integer recylerView, Main_BookListAdapter_B adapter) {
        RecyclerView recyclerView = root.findViewById(recylerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setItems(new Main_BookData_JSON().getData(assetManager, bookType));
        adapter.notifyDataSetChanged();
    }

    static void bookListC(View root, String apiUrl, String etc, Integer recylerview, Main_BookListAdapter_C adapter, RequestQueue queue, Integer integer) {
        RecyclerView recyclerView = root.findViewById(recylerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        LinearLayout wrap = root.findViewById(integer);
        adapter.setItems(new Main_BookData().getData(apiUrl, etc, queue, wrap, ""));
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    static void bookListAWebToon(View root, String apiUrl, String etc, Integer recylerView, MainBookListAdapterA adapter, RequestQueue queue, Integer integer) {
        RecyclerView recyclerView = root.findViewById(recylerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        LinearLayout wrap = root.findViewById(integer);
        adapter.setItems(new Main_BookData_Webtoon().getData(apiUrl, etc, queue, wrap));
        adapter.notifyDataSetChanged();
    }

    static void bookListD(View root, String apiUrl, String etc, Integer recylerview, Main_BookListAdapter_D adapter, RequestQueue queue, Integer integer) {
        RecyclerView recyclerView = root.findViewById(recylerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        LinearLayout wrap = root.findViewById(integer);
        adapter.setItems(new Main_BookData().getData(apiUrl, etc, queue, wrap,""));
        adapter.notifyDataSetChanged();
    }

    static void initAdapterC(RecyclerView recyclerView, LinearLayoutManager linearLayoutManager, Main_BookListAdapter_C adapter) {
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

}
