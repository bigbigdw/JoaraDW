package bigbigdw.joaradw.Book_Detail;

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

import bigbigdw.joaradw.Config;
import bigbigdw.joaradw.BookPagination;
import bigbigdw.joaradw.main.MainBookListAdapterC;
import bigbigdw.joaradw.JOARADW;
import bigbigdw.joaradw.main.MainBookListData;
import bigbigdw.joaradw.R;


public class Detail_Tab_2 extends Fragment {
    private MainBookListAdapterC Adapter;
    private RecyclerView recyclerView;
    private ArrayList<MainBookListData> items = new ArrayList<>();
    LinearLayout Wrap, Cover, Blank;
    String TOKEN = "" , ETC = "", BOOKCODE = "", API = "/v1/book/other.joa";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.book_detail_tab_2, container, false);

        JOARADW myApp = (JOARADW) getActivity().getApplicationContext();
        BOOKCODE = myApp.getBookCode();

        recyclerView = root.findViewById(R.id.BookDetail);
        Wrap = root.findViewById(R.id.TabWrap);
        Cover = root.findViewById(R.id.LoadingLayout);
        Blank = root.findViewById(R.id.BlankLayout);

        if(Config.getuserinfo() != null){
            JSONObject GETUSERINFO = Config.getuserinfo();
            JSONObject UserInfo;
            try {
                UserInfo = GETUSERINFO.getJSONObject("user");
                TOKEN = UserInfo.getString("token");
            } catch (JSONException e) {
                e.printStackTrace();
                TOKEN = "";
            }
        }

        ETC = "&book_code=" + BOOKCODE + "&token=" + TOKEN + "&offset=25";

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        BookPagination.populateData(API, ETC, queue, Wrap, items, Cover, Blank);
        initAdapter();

        Adapter.setOnItemClickListener((v, position, Value) -> {
            MainBookListData item = Adapter.getItem(position);
            if(Value.equals("FAV")){
                BookPagination.favToggle(queue, item.getBookCode(), TOKEN);
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
        Adapter = new MainBookListAdapterC(items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        Adapter.notifyDataSetChanged();
        recyclerView.setAdapter(Adapter);
    }
}