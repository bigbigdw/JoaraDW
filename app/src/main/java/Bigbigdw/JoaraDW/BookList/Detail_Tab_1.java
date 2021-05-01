package Bigbigdw.JoaraDW.BookList;

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

import Bigbigdw.JoaraDW.Fragment_New.Book_Pagination;
import Bigbigdw.JoaraDW.Main.Main_BookListData;
import Bigbigdw.JoaraDW.R;


public class Detail_Tab_1 extends Fragment {
    private Main_BookListAdapter_C NewBookListAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Main_BookListData> items = new ArrayList<>();
    LinearLayout Wrap, Cover;
    String Store="";
    String TOKEN = "";
    String ETC = "";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.book_detail_tab_1, container, false);

        try {
            FileReader fr = new FileReader(getActivity().getDataDir() + "/userInfo.json");
            Log.d("TEST", getActivity().getDataDir().toString());
            BufferedReader br = new BufferedReader(fr);
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line).append("\n");
                line = br.readLine();
            }
            br.close();
            String result = sb.toString();
            JSONObject jsonObject = new JSONObject(result);
            JSONObject UserInfo = jsonObject.getJSONObject("user");
            TOKEN = UserInfo.getString("token");
            Log.d("New_Tab_ALL", "읽기 완료");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.d("New_Tab_ALL", "읽기 실패");
        }

        if(!TOKEN.equals("")){
            ETC = "&store=" + Store + "&orderby=redate&offset=25&page=" + 1 + "&token=" + TOKEN + "&class=";
        } else {
            ETC = "&store=" + Store + "&orderby=redate&offset=25&page=" + 1 + "&class=";
        }

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String API = "/v1/book/list.joa";

        recyclerView = root.findViewById(R.id.BookDetail_1);
        Wrap = root.findViewById(R.id.Tab_NewAll);
        Cover = root.findViewById(R.id.LoadingLayout);

        Book_Pagination.populateData(API, ETC, queue, Wrap, items, Cover);
        initAdapter();
        Book_Pagination.initScrollListener(API, queue, Wrap, items, NewBookListAdapter, recyclerView, Store);

        NewBookListAdapter.setOnItemClicklistener((holder, view, position, Value) -> {
            Main_BookListData item = NewBookListAdapter.getItem(position);
            Book_Pagination.FavToggle(queue, item.getBookCode(), TOKEN);
        });


        return root;
    }

    private void initAdapter() {
        NewBookListAdapter = new Main_BookListAdapter_C(items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        NewBookListAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(NewBookListAdapter);
    }
}