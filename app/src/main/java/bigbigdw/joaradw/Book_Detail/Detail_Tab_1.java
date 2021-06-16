package bigbigdw.joaradw.Book_Detail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bigbigdw.joaradw.Book_Viewer.Book_Viewer;
import bigbigdw.joaradw.JOARADW;
import bigbigdw.joaradw.R;


public class Detail_Tab_1 extends Fragment {
    private final ArrayList<Detail_BookPageData> items = new ArrayList<>();
    private RecyclerView recyclerView;
    private RequestQueue queue;
    Detail_BookListAdapter Adapter;
    String BookListImg, BOOKCODE, TOKEN, BookDetailURL;
    LinearLayout LoadingLayout, TabWrap;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.book_detail_tab_1, container, false);
        recyclerView = root.findViewById(R.id.BookDetail);
        LoadingLayout = root.findViewById(R.id.LoadingLayout);
        TabWrap = root.findViewById(R.id.TabWrap);

        JOARADW myApp = (JOARADW) requireActivity().getApplicationContext();
        BOOKCODE = myApp.getBookCode();
        TOKEN = myApp.getToken();
        BookDetailURL = myApp.getApiUrl();
        queue = Volley.newRequestQueue(requireActivity());

        populateData();
        initAdapter();

        Adapter.setOnItemClickListener((v, position, Value) -> {
            Detail_BookPageData item = Adapter.getItem(position);
            Intent intent = new Intent(requireContext().getApplicationContext(), Book_Viewer.class);
            intent.putExtra("Cid",String.format("%s", item.getCid()));
            intent.putExtra("TOKEN",String.format("%s", TOKEN));
            intent.putExtra("BOOKCODE",String.format("%s", BOOKCODE));
            intent.putExtra("SORTNO",String.format("%s", item.getBookListNum()));
            startActivity(intent);
        });

        return root;
    }

    void populateData() {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, BookDetailURL, null, response -> {
            try {
                JSONObject BOOK = response.getJSONObject("book");
                JSONArray ChapterInfo = BOOK.getJSONArray("chapter");
                BookListImg = BOOK.getString("book_img");
                LoadingLayout.setVisibility(View.GONE);
                TabWrap.setVisibility(View.VISIBLE);

                for (int i = 0; i < ChapterInfo.length(); i++) {
                    JSONObject jo = ChapterInfo.getJSONObject(i);
                    String ChapterTitle = jo.getString("sub_subject");
                    String BookListRecommend = jo.getString("cnt_recom");
                    String BookListComment = jo.getString("cnt_comment");
                    String BookCid = jo.getString("cid");
                    String BookList_Num = String.valueOf(i+1);

                    items.add(new Detail_BookPageData(BookList_Num, BookListImg, BookListRecommend, ChapterTitle,BookCid, BookListComment));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d("Detail_Tab_1", "에러!"));

        queue.add(jsonRequest);
    }

    private void initAdapter() {
        Adapter = new Detail_BookListAdapter(items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(Adapter);
    }
}


