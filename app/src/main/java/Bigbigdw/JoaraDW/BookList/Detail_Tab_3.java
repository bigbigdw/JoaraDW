package Bigbigdw.JoaraDW.BookList;

import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Bigbigdw.JoaraDW.Book_Detail.Book_Detail_Cover;
import Bigbigdw.JoaraDW.Config;
import Bigbigdw.JoaraDW.Book_Pagination;
import Bigbigdw.JoaraDW.JOARADW;
import Bigbigdw.JoaraDW.Main.Main_BookListData;
import Bigbigdw.JoaraDW.R;


public class Detail_Tab_3 extends Fragment {
    private Main_BookListAdapter_C Adapter;
    private RecyclerView recyclerView;
    private ArrayList<Main_BookListData> items = new ArrayList<>();
    LinearLayout Wrap, Cover, Blank;
    String Store="";
    String TOKEN = "";
    String ETC = "";
    String BOOKCODE;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.book_detail_tab_2, container, false);

        JOARADW myApp = (JOARADW) getActivity().getApplicationContext();
        BOOKCODE = myApp.getBookCode();

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

        ETC = "&recommend_type=book_code&offset=50" + "&token=" + TOKEN + "&book_code=" + BOOKCODE + "&model_type=all&chapter_cnt=1&finish=&adult=";

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String API = "/v1/book/recommend_list_api.joa";

        recyclerView = root.findViewById(R.id.BookDetail);
        Wrap = root.findViewById(R.id.TabWrap);
        Cover = root.findViewById(R.id.LoadingLayout);
        Blank = root.findViewById(R.id.BlankLayout);

        Book_Pagination.populateData(API, ETC, queue, Wrap, items, Cover, Blank, "");
        initAdapter();

        Adapter.setOnItemClicklistener((holder, view, position, Value) -> {
            Main_BookListData item = Adapter.getItem(position);
            if(Value.equals("FAV")){
                Book_Pagination.FavToggle(queue, item.getBookCode(), TOKEN);
            } else if (Value.equals("BookDetail")){
                Intent intent = new Intent(requireContext().getApplicationContext(), Book_Detail_Cover.class);
                intent.putExtra("BookCode",String.format("%s", item.getBookCode()));
                intent.putExtra("TOKEN",String.format("%s", TOKEN));
                startActivity(intent);
            }
        });

        return root;
    }

    private void initAdapter() {
        Adapter = new Main_BookListAdapter_C(items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        Adapter.notifyDataSetChanged();
        recyclerView.setAdapter(Adapter);
    }
}