package Bigbigdw.JoaraDW.BookList;

import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Bigbigdw.JoaraDW.BookList.Main_BookListAdapter_C;
import Bigbigdw.JoaraDW.Book_Detail.Book_Detail_Cover;
import Bigbigdw.JoaraDW.Config;
import Bigbigdw.JoaraDW.Fragment_New.Book_Pagination;
import Bigbigdw.JoaraDW.JOARADW;
import Bigbigdw.JoaraDW.Main.Main_BookListData;
import Bigbigdw.JoaraDW.R;

public class New_Tab_Finished extends Fragment {
    private Main_BookListAdapter_C NewBookListAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Main_BookListData> items = new ArrayList<>();
    LinearLayout Wrap, Cover, Blank;
    String Store = "finish";
    String TOKEN = "";
    String ETC = "";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new_tab, container, false);

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String API = "/v1/book/list.joa";
        if(Config.GETUSERINFO() != null){
            JSONObject GETUSERINFO = Config.GETUSERINFO();
            JSONObject UserInfo;
            try {
                UserInfo = GETUSERINFO.getJSONObject("user");
                TOKEN = UserInfo.getString("token");
            } catch (JSONException e) {
                e.printStackTrace();
                TOKEN = "";
            }
        }

        ETC = "&store=" + Store + "&orderby=redate&offset=25&page=" + 1 + "&token=" + TOKEN + "&class=";


        recyclerView = root.findViewById(R.id.Main_NewBookList);
        Wrap = root.findViewById(R.id.TabWrap);
        Cover = root.findViewById(R.id.LoadingLayout);
        Blank = root.findViewById(R.id.BlankLayout);

        Book_Pagination.populateData(API, ETC, queue, Wrap, items, Cover, Blank, "");
        initAdapter();
        Book_Pagination.ScrollListener(API, queue, Wrap, items, NewBookListAdapter, recyclerView, ETC);

        NewBookListAdapter.setOnItemClicklistener((holder, view, position, Value) -> {
            Main_BookListData item = NewBookListAdapter.getItem(position);
            if (Value.equals("FAV")) {
                Book_Pagination.FavToggle(queue, item.getBookCode(), TOKEN);
            } else if (Value.equals("BookDetail")) {
                Intent intent = new Intent(requireContext().getApplicationContext(), Book_Detail_Cover.class);
                intent.putExtra("BookCode", String.format("%s", item.getBookCode()));
                intent.putExtra("TOKEN", String.format("%s", TOKEN));
                startActivity(intent);
            }
        });

        return root;
    }

    private void initAdapter() {
        NewBookListAdapter = new Main_BookListAdapter_C(items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(NewBookListAdapter);
    }
}
