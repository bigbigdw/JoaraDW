package Bigbigdw.JoaraDW.Fragment_New;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import Bigbigdw.JoaraDW.Main.Main_BookListData;
import Bigbigdw.JoaraDW.R;

public class New_Tab_ALL extends Fragment {
    private Main_BookListAdapter_New NewBookListAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Main_BookListData> items = new ArrayList<>();
    LinearLayout Wrap, Cover;
    String Store="";
    String TOKEN = "";
    ImageView Favon;
    ImageView Favoff;
    String ETC = "";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new_tab_all, container, false);

        try {
            FileReader fr = new FileReader(getActivity().getDataDir() + "/userInfo.json");
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
            Log.d("TOKEN", TOKEN);
            Log.d("USERINFO", "읽기 완료");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.d("USERINFO", "읽기 실패");
        }

        if(!TOKEN.equals("")){
            ETC = "&store=" + Store + "&orderby=redate&offset=25&page=" + 1 + "&token=" + TOKEN + "&class=";
        } else {
            ETC = "&store=" + Store + "&orderby=redate&offset=25&page=" + 1 + "&class=";
        }

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String API = "/v1/book/list.joa";

        recyclerView = root.findViewById(R.id.Main_NewBookList);
        Wrap = root.findViewById(R.id.Tab_NewAll);
        Cover = root.findViewById(R.id.LoadingLayout);
        Favon = root.findViewById(R.id.FavON);
        Favoff = root.findViewById(R.id.FavOff);

        New_Book_Pagination.populateData(API, ETC, queue, Wrap, items, Cover);
        initAdapter();
        New_Book_Pagination.initScrollListener(API, queue, Wrap, items, NewBookListAdapter, recyclerView, Store);
        
        NewBookListAdapter.setOnItemClicklistener((holder, view, position, Value) -> {
            Main_BookListData item = NewBookListAdapter.getItem(position);
            New_Book_Pagination.FavToggle(queue, item.getBookCode(), TOKEN);
        });


        return root;
    }

    private void initAdapter() {
        NewBookListAdapter = new Main_BookListAdapter_New(items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        NewBookListAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(NewBookListAdapter);
    }
}
