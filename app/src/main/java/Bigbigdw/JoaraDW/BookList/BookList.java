package Bigbigdw.JoaraDW.BookList;

import android.content.Intent;
import android.content.res.AssetManager;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;

import Bigbigdw.JoaraDW.Book_Detail.Book_Detail_Cover;
import Bigbigdw.JoaraDW.Main.Main_BookData;
import Bigbigdw.JoaraDW.Main.Main_BookData_JSON;
import Bigbigdw.JoaraDW.Main.Main_BookData_Webtoon;
import Bigbigdw.JoaraDW.Main.Main_BookListAdapter_B;
import Bigbigdw.JoaraDW.Main.Main_BookListData;


public interface BookList {

    static void BookList_A(View root, String API_URL, String ETC, Integer RecylerView, Main_BookListAdapter_A Adapter, RequestQueue queue, Integer Wrap) {
        RecyclerView recyclerView = root.findViewById(RecylerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(Adapter);
        LinearLayout wrap = root.findViewById(Wrap);
        Adapter.setItems(new Main_BookData().getData(API_URL, ETC, queue, wrap, ""));
        Adapter.notifyDataSetChanged();
    }

    static void BookList_B(View root, AssetManager assetManager, String BookType, Integer RecylerView, Main_BookListAdapter_B Adapter) {
        RecyclerView recyclerView = root.findViewById(RecylerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(Adapter);
        Adapter.setItems(new Main_BookData_JSON().getData(assetManager, BookType));
        Adapter.notifyDataSetChanged();
    }

    static void BookList_A_WebToon(View root, String API_URL, String ETC, Integer RecylerView, Main_BookListAdapter_A Adapter, RequestQueue queue, Integer Wrap) {
        RecyclerView recyclerView = root.findViewById(RecylerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(Adapter);
        LinearLayout wrap = root.findViewById(Wrap);
        Adapter.setItems(new Main_BookData_Webtoon().getData(API_URL, ETC, queue, wrap));
        Adapter.notifyDataSetChanged();
    }

    static void BookList_C(View root, String API_URL, String ETC, Integer RecylerView, Main_BookListAdapter_C Adapter, RequestQueue queue, Integer Wrap) {
        RecyclerView recyclerView = root.findViewById(RecylerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        LinearLayout wrap = root.findViewById(Wrap);
        Adapter.setItems(new Main_BookData().getData(API_URL, ETC, queue, wrap,""));
        Adapter.notifyDataSetChanged();
        recyclerView.setAdapter(Adapter);
    }

    static void BookList_D(View root, String API_URL, String ETC, Integer RecylerView, Main_BookListAdapter_D Adapter, RequestQueue queue, Integer Wrap) {
        RecyclerView recyclerView = root.findViewById(RecylerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(Adapter);
        LinearLayout wrap = root.findViewById(Wrap);
        Adapter.setItems(new Main_BookData().getData(API_URL, ETC, queue, wrap,""));
        Adapter.notifyDataSetChanged();

    }

}
