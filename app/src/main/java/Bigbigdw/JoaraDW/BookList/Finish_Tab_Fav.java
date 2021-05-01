package Bigbigdw.JoaraDW.BookList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
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

import Bigbigdw.JoaraDW.Book_Detail.Book_Detail_Cover;
import Bigbigdw.JoaraDW.Fragment_Finish.Finish_BookData;
import Bigbigdw.JoaraDW.Fragment_New.Book_Pagination;
import Bigbigdw.JoaraDW.BookList.Main_BookListAdapter_C;
import Bigbigdw.JoaraDW.Main.Main_BookListData;
import Bigbigdw.JoaraDW.R;


public class Finish_Tab_Fav extends Fragment {
    private ArrayList<Main_BookListData> items = new ArrayList<>();
    private RequestQueue queue;
    String FinishType = "cnt_favorite";
    LinearLayout LoadingLayout;
    NestedScrollView ContentsLayout;
    String TOKEN = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_finish_tab, container, false);
        queue = Volley.newRequestQueue(getActivity());

        LoadingLayout = root.findViewById(R.id.LoadingLayout);
        ContentsLayout = root.findViewById(R.id.ContentsLayout);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            LoadingLayout.setVisibility(View.GONE);
            ContentsLayout.setVisibility(View.VISIBLE);
        }, 500);

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
            Log.d("USERINFO", "읽기 완료");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.d("USERINFO", "읽기 실패");
        }

        Main_BookListAdapter_C newAdapter = new Main_BookListAdapter_C(items);
        Main_BookListAdapter_C noblessAdapter = new Main_BookListAdapter_C(items);
        Main_BookListAdapter_C premiumAdapter = new Main_BookListAdapter_C(items);


        if(!TOKEN.equals("")){
            BookFinish(root, "/v1/book/list.joa", "&category=0&store=finish&orderby=" + FinishType + "&offset=25&page=1" + "&token=" + TOKEN, R.id.Finish_Tab_NewList, newAdapter, queue, R.id.Finish_Tab_New);
            BookFinish(root, "/v1/book/list.joa", "&category=0&store=nobless_finish&orderby=" + FinishType + "&offset=25&page=1" + "&token=" + TOKEN, R.id.Finish_Tab_NoblessList, noblessAdapter, queue, R.id.Finish_Tab_Nobless);
            BookFinish(root, "/v1/book/list.joa", "&category=0&store=premium_finish&orderby=" + FinishType + "&offset=25&page=1" + "&token=" + TOKEN, R.id.Finish_Tab_PremiumList, premiumAdapter, queue, R.id.Finish_Tab_Premium);
        } else {
            BookFinish(root, "/v1/book/list.joa", "&category=0&store=finish&orderby=" + FinishType + "&offset=25&page=1", R.id.Finish_Tab_NewList, newAdapter, queue, R.id.Finish_Tab_New);
            BookFinish(root, "/v1/book/list.joa", "&category=0&store=nobless_finish&orderby=" + FinishType + "&offset=25&page=1", R.id.Finish_Tab_NoblessList, noblessAdapter, queue, R.id.Finish_Tab_Nobless);
            BookFinish(root, "/v1/book/list.joa", "&category=0&store=premium_finish&orderby=" + FinishType + "&offset=25&page=1", R.id.Finish_Tab_PremiumList, premiumAdapter, queue, R.id.Finish_Tab_Premium);
        }

        return root;
    }

    public void BookFinish(View root, String API_URL, String ETC, Integer RecylerView, Main_BookListAdapter_C Adapter, RequestQueue queue, Integer Wrap) {
        Adapter.setOnItemClicklistener((holder, view, position, Value) -> {
            Main_BookListData item = Adapter.getItem(position);
            Log.d("TESTT", item.getBookCode());
            Book_Pagination.FavToggle(queue, item.getBookCode(), TOKEN);
        });
        RecyclerView recyclerView = root.findViewById(RecylerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        LinearLayout wrap = root.findViewById(Wrap);
        Adapter.setItems(new Finish_BookData().getData(API_URL, ETC, queue, wrap));
        Adapter.notifyDataSetChanged();
        recyclerView.setAdapter(Adapter);

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
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("onDestroyView", "파괴!");
    }
}