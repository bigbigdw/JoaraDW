package Bigbigdw.JoaraDW.Fragment_Best;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import Bigbigdw.JoaraDW.Main.Main_BookData;
import Bigbigdw.JoaraDW.Main.Main_BookData_JSON;
import Bigbigdw.JoaraDW.Main.Main_BookData_Webtoon;
import Bigbigdw.JoaraDW.Main.Main_BookListAdapter_A;
import Bigbigdw.JoaraDW.Main.Main_BookListAdapter_B;
import Bigbigdw.JoaraDW.Main.Main_BookListAdapter_C;
import Bigbigdw.JoaraDW.Main.Main_BookListAdapter_D;
import Bigbigdw.JoaraDW.R;

public class Best_Tab_Alltime extends Fragment {
    private final Main_BookListAdapter_Best AllAdapter = new Main_BookListAdapter_Best();
    private final Main_BookListAdapter_Best NoblessAdapter = new Main_BookListAdapter_Best();
    private final Main_BookListAdapter_Best PremiumAdapter = new Main_BookListAdapter_Best();
    private final Main_BookListAdapter_Best FreeAdapter = new Main_BookListAdapter_Best();
    private final Main_BookListAdapter_Best SeriesAdapter = new Main_BookListAdapter_Best();
    private final Main_BookListAdapter_Best FinishAdapter = new Main_BookListAdapter_Best();
    private final Main_BookListAdapter_Best NoblessClassicAdapter = new Main_BookListAdapter_Best();
    private RequestQueue queue;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_best_tab_alltime, container, false);
        queue = Volley.newRequestQueue(getActivity());

        BookBest(root, "/v1/best/book.joa", "&best=today&store=&orderby=cnt_best&offset=25&page=1", R.id.Best_Tab_AllList, AllAdapter, queue, R.id.Best_Tab_All);
        BookBest(root, "/v1/best/book.joa", "&best=today&store=nobless&orderby=cnt_best&offset=25&page=1", R.id.Best_Tab_NoblessList, NoblessAdapter, queue, R.id.Best_Tab_Nobless);
        BookBest(root, "/v1/best/book.joa", "&best=today&store=premium&orderby=cnt_best&offset=25&page=1", R.id.Best_Tab_PremiumList, PremiumAdapter, queue, R.id.Best_Tab_Premium);
        BookBest(root, "/v1/best/book.joa", "&best=today&store=series&orderby=cnt_best&offset=25&page=1", R.id.Best_Tab_FreeList, FreeAdapter, queue, R.id.Best_Tab_Free);
        BookBest(root, "/v1/best/book.joa", "&best=today&store=lately&orderby=cnt_best&offset=25&page=1", R.id.Best_Tab_SeriesList, SeriesAdapter, queue, R.id.Best_Tab_Series);
        BookBest(root, "/v1/best/book.joa", "&best=today&store=finish&orderby=cnt_best&offset=25&page=1", R.id.Best_Tab_FinishList, FinishAdapter, queue, R.id.Best_Tab_Finish);
        BookBest(root, "/v1/best/book.joa", "&best=today&store=nobless_classic&orderby=cnt_best&offset=25&page=1", R.id.Best_Tab_NoblessClassicList, NoblessClassicAdapter, queue, R.id.Best_Tab_NoblessClassic);


        return root;
    }

    public void BookBest(View root, String API_URL, String ETC, Integer RecylerView, Main_BookListAdapter_Best Adapter, RequestQueue queue, Integer Wrap) {
        RecyclerView recyclerView = root.findViewById(RecylerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(Adapter);
        LinearLayout wrap = root.findViewById(Wrap);
        Adapter.setItems(new Best_BookData().getData(API_URL, ETC, queue, wrap));
        Log.d("TEST", "불림!");
        Adapter.notifyDataSetChanged();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("onDestroyView", "파괴!");
    }
}
