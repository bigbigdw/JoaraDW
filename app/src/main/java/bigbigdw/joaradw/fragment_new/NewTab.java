package bigbigdw.joaradw.fragment_new;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

import bigbigdw.joaradw.BookPagination;
import bigbigdw.joaradw.JOARADW;
import bigbigdw.joaradw.base.BookBaseFragment;
import bigbigdw.joaradw.etc.API;
import bigbigdw.joaradw.etc.BookList;
import bigbigdw.joaradw.main.MainBookListAdapterC;
import bigbigdw.joaradw.main.MainBookDataJSON;
import bigbigdw.joaradw.main.TabViewModel;
import bigbigdw.joaradw.main.MainBookListData;
import bigbigdw.joaradw.R;

public class NewTab extends BookBaseFragment {
    private MainBookListAdapterC adapter;
    private final ArrayList<MainBookListData> items = new ArrayList<>();
    private static final String ARG_SECTION_NUMBER = "section_number";
    private TabViewModel tabviewmodel;
    LinearLayout wrap;
    LinearLayout cover;
    LinearLayout blank;
    String store = "";
    String userToken;
    String etc = "";
    String classes = "&class=";
    JSONObject getuserinfo;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    RequestQueue queue;

    public static NewTab newInstance(int index) {
        NewTab fragment = new NewTab();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabviewmodel = new ViewModelProvider(this).get(TabViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        tabviewmodel.setIndex(index);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new_tab, container, false);
        queue = Volley.newRequestQueue(requireActivity());
        recyclerView  = root.findViewById(R.id.Main_NewBookList);
        wrap = root.findViewById(R.id.TabWrap);
        cover = root.findViewById(R.id.LoadingLayout);
        blank = root.findViewById(R.id.BlankLayout);
        adapter = new MainBookListAdapterC(items);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        checkToken();
        JOARADW app = (JOARADW) requireActivity().getApplicationContext();
        userToken = app.getToken();

        AssetManager assetManager = requireActivity().getAssets();

        tabviewmodel.getText().observe(getViewLifecycleOwner(), tabNum -> {
            switch (tabNum) {
                case "TAB1":
                    store = "";
                    newBookList();
                    break;
                case "TAB2":
                    newBookListJSON(root, assetManager, "Main_Tab_Best_77FES.json");
                    break;
                case "TAB3":
                    newBookListJSON(root, assetManager, "Main_KidamuBookList.json");
                    break;
                case "TAB4":
                    newBookListJSON(root, assetManager, "Main_PromisedBookList.json");
                    break;
                case "TAB5":
                    store = "nobless";
                    newBookList();
                    break;
                case "TAB6":
                    store = "premium";
                    newBookList();
                    break;
                case "TAB7":
                    store = "series";
                    newBookList();
                    break;
                case "TAB8":
                    store = "finish";
                    newBookList();
                    break;
                default:
                    classes = "&class=short";
                    newBookList();
                    break;
            }
        });

        return root;
    }
    public void newBookList(){
        etc = "&store=" + store + "&orderby=redate&offset=25&page=" + 1 + "&token=" + userToken + classes;
        BookPagination.populateData(API.BOOK_LIST_JOA, etc, queue, wrap, items, cover, blank);
        BookList.initAdapterC(recyclerView, linearLayoutManager, adapter);
        BookPagination.scrollListener(API.BOOK_LIST_JOA, queue, wrap, items, adapter, recyclerView, etc);

        adapter.setOnItemClickListener((v, position, value) -> {
            MainBookListData item = adapter.getItem(position);
            adapterListener(item, value, queue);
        });
    }


    public void newBookListJSON(View root, AssetManager assetManager, String bookType){
        wrap.setVisibility(View.VISIBLE);
        cover.setVisibility(View.GONE);
        blank.setVisibility(View.GONE);

        RecyclerView recyclerViewJSON = root.findViewById(R.id.Main_NewBookList);
        LinearLayoutManager linearLayoutManagerJSON = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        recyclerViewJSON.setLayoutManager(linearLayoutManagerJSON);
        recyclerViewJSON.setAdapter(adapter);
        adapter.setItems(new MainBookDataJSON().getData(assetManager, bookType));
        adapter.notifyDataSetChanged();
    }
}
