package bigbigdw.joaradw.Fragment_New;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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

import bigbigdw.joaradw.Book_Detail.Book_Detail_Cover;
import bigbigdw.joaradw.Config;
import bigbigdw.joaradw.BookPagination;
import bigbigdw.joaradw.etc.BookList;
import bigbigdw.joaradw.main.Main_BookListAdapter_C;
import bigbigdw.joaradw.main.Main_BookData_JSON;
import bigbigdw.joaradw.main.Tab_ViewModel;
import bigbigdw.joaradw.main.Main_BookListData;
import bigbigdw.joaradw.R;

public class New_Tab extends Fragment {
    private Main_BookListAdapter_C Adapter;
    private final ArrayList<Main_BookListData> items = new ArrayList<>();
    private static final String ARG_SECTION_NUMBER = "section_number";
    private Tab_ViewModel Tab_ViewModel;
    LinearLayout Wrap, Cover, Blank;
    String Store = "";
    String TOKEN = "", ETC = "", CLASS = "&class=";
    JSONObject GETUSERINFO;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    RequestQueue queue;
    String API = "/v1/book/list.joa";

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
        Tab_ViewModel = new ViewModelProvider(this).get(Tab_ViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        Tab_ViewModel.setIndex(index);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new_tab, container, false);
        queue = Volley.newRequestQueue(getActivity());
        recyclerView  = root.findViewById(R.id.Main_NewBookList);
        Wrap = root.findViewById(R.id.TabWrap);
        Cover = root.findViewById(R.id.LoadingLayout);
        Blank = root.findViewById(R.id.BlankLayout);
        Adapter = new Main_BookListAdapter_C(items);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        if (Config.getuserinfo() != null) {
            GETUSERINFO = Config.getuserinfo();
            JSONObject UserInfo;
            try {
                UserInfo = GETUSERINFO.getJSONObject("user");
                TOKEN = UserInfo.getString("token");
            } catch (JSONException e) {
                e.printStackTrace();
                TOKEN = "";
            }
        }

        AssetManager assetManager = getActivity().getAssets();

        Tab_ViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String TabNum) {
                if (TabNum.equals("TAB1")) {
                    Store = "";
                    NewBookList();
                } else if (TabNum.equals("TAB2")) {
                    NewBookListJSON(root, assetManager, "Main_Tab_Best_77FES.json");
                } else if (TabNum.equals("TAB3")) {
                    NewBookListJSON(root, assetManager, "Main_KidamuBookList.json");
                } else if (TabNum.equals("TAB4")) {
                    NewBookListJSON(root, assetManager, "Main_PromisedBookList.json");
                } else if (TabNum.equals("TAB5")) {
                    Store = "nobless";
                    NewBookList();
                } else if (TabNum.equals("TAB6")) {
                    Store = "premium";
                    NewBookList();
                } else if (TabNum.equals("TAB7")) {
                    Store = "series";
                    NewBookList();
                } else if (TabNum.equals("TAB8")) {
                    Store = "finish";
                    NewBookList();
                } else {
                    CLASS = "&class=short";
                    NewBookList();
                }
            }
        });

        return root;
    }
    public void NewBookList(){
        ETC = "&store=" + Store + "&orderby=redate&offset=25&page=" + 1 + "&token=" + TOKEN + CLASS;
        BookPagination.populateData(API, ETC, queue, Wrap, items, Cover, Blank, "");
        BookList.initAdapterC(recyclerView, linearLayoutManager, Adapter);
        BookPagination.ScrollListener(API, queue, Wrap, items, Adapter, recyclerView, ETC);

        Adapter.setOnItemClickListener((v, position, Value) -> {
            Main_BookListData item = Adapter.getItem(position);
            if (Value.equals("FAV")) {
                BookPagination.FavToggle(queue, item.getBookCode(), TOKEN);
            } else if (Value.equals("BookDetail")) {
                Intent intent = new Intent(requireContext().getApplicationContext(), Book_Detail_Cover.class);
                intent.putExtra("BookCode", String.format("%s", item.getBookCode()));
                intent.putExtra("TOKEN", String.format("%s", TOKEN));
                startActivity(intent);
            }
        });
    }


    public void NewBookListJSON(View root, AssetManager assetManager, String BookType){
        Wrap.setVisibility(View.VISIBLE);
        Cover.setVisibility(View.GONE);
        Blank.setVisibility(View.GONE);

        RecyclerView recyclerView = root.findViewById(R.id.Main_NewBookList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(Adapter);
        Adapter.setItems(new Main_BookData_JSON().getData(assetManager, BookType));
        Adapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
