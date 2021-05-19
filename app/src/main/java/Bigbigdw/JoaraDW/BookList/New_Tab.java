package Bigbigdw.JoaraDW.BookList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
import Bigbigdw.JoaraDW.Fragment_New.Fragment_New_ViewModel;
import Bigbigdw.JoaraDW.Main.Main_BookListData;
import Bigbigdw.JoaraDW.R;

public class New_Tab extends Fragment {
    private Main_BookListAdapter_C Adapter;
    private final ArrayList<Main_BookListData> items = new ArrayList<>();
    private static final String ARG_SECTION_NUMBER = "section_number";
    private Fragment_New_ViewModel Fragment_New_ViewModel;
    LinearLayout Wrap, Cover, Blank;
    String Store = "";
    String TOKEN = "", ETC = "", CLASS = "&class=";
    JSONObject GETUSERINFO;


    public static New_Tab newInstance(int index) {
        New_Tab fragment = new New_Tab();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment_New_ViewModel = new ViewModelProvider(this).get(Fragment_New_ViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        Fragment_New_ViewModel.setIndex(index);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new_tab, container, false);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String API = "/v1/book/list.joa";
        RecyclerView recyclerView = root.findViewById(R.id.Main_NewBookList);
        Wrap = root.findViewById(R.id.TabWrap);
        Cover = root.findViewById(R.id.LoadingLayout);
        Blank = root.findViewById(R.id.BlankLayout);
        Adapter = new Main_BookListAdapter_C(items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        if (Config.GETUSERINFO() != null) {
            GETUSERINFO = Config.GETUSERINFO();
            JSONObject UserInfo;
            try {
                UserInfo = GETUSERINFO.getJSONObject("user");
                TOKEN = UserInfo.getString("token");
            } catch (JSONException e) {
                e.printStackTrace();
                TOKEN = "";
            }
        }

        Fragment_New_ViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String TabNum) {
                if (TabNum.equals("TAB1")) {
                    Store = "";
                } else if (TabNum.equals("TAB2")) {
                    Store = "nobless";
                } else if (TabNum.equals("TAB3")) {
                    Store = "nobless";
                } else if (TabNum.equals("TAB4")) {
                    Store = "nobless";
                } else if (TabNum.equals("TAB5")) {
                    Store = "nobless";
                } else if (TabNum.equals("TAB6")) {
                    Store = "premium";
                } else if (TabNum.equals("TAB7")) {
                    Store = "series";
                } else if (TabNum.equals("TAB8")) {
                    Store = "finish";
                } else {
                    CLASS = "&class=short";
                }
                ETC = "&store=" + Store + "&orderby=redate&offset=25&page=" + 1 + "&token=" + TOKEN + CLASS;
                Log.d("ETC", ETC);
                Book_Pagination.populateData(API, ETC, queue, Wrap, items, Cover, Blank, "");
                BookList.initAdapter_C(recyclerView, linearLayoutManager, Adapter);
                Book_Pagination.ScrollListener(API, queue, Wrap, items, Adapter, recyclerView, ETC);
            }
        });


        Adapter.setOnItemClicklistener((holder, view, position, Value) -> {
            Main_BookListData item = Adapter.getItem(position);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
