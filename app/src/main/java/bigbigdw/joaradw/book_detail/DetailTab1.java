package bigbigdw.joaradw.book_detail;

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

import bigbigdw.joaradw.book_viewer.BookViewer;
import bigbigdw.joaradw.JOARADW;
import bigbigdw.joaradw.R;
import bigbigdw.joaradw.etc.API;
import bigbigdw.joaradw.etc.HELPER;


public class DetailTab1 extends Fragment {
    private final ArrayList<DetailBookPageData> items = new ArrayList<>();
    private RecyclerView recyclerView;
    private RequestQueue queue;
    DetailBookListAdapter adapter;
    String bookListImg;
    String bookcode;
    String token;
    String bookDetailURL;
    LinearLayout loadingLayout;
    LinearLayout tabWrap;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.book_detail_tab_1, container, false);
        recyclerView = root.findViewById(R.id.BookDetail);
        loadingLayout = root.findViewById(R.id.LoadingLayout);
        tabWrap = root.findViewById(R.id.TabWrap);

        JOARADW myApp = (JOARADW) requireActivity().getApplicationContext();
        bookcode = myApp.getBookCode();
        token = myApp.getToken();
        bookDetailURL = HELPER.API + API.BOOK_DETAIL_JOA + HELPER.ETC + "&book_code=" + bookcode;
        queue = Volley.newRequestQueue(requireActivity());

        populateData();
        initAdapter();

        adapter.setOnItemClickListener((v, position, value) -> {
            DetailBookPageData item = adapter.getItem(position);
            Intent intent = new Intent(requireContext().getApplicationContext(), BookViewer.class);
            intent.putExtra("Cid",String.format("%s", item.getCid()));
            intent.putExtra("TOKEN",String.format("%s", token));
            intent.putExtra("BOOKCODE",String.format("%s", bookcode));
            intent.putExtra("SORTNO",String.format("%s", item.getBookListNum()));
            startActivity(intent);
        });

        return root;
    }

    void populateData() {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, bookDetailURL, null, response -> {
            try {
                JSONObject book = response.getJSONObject("book");
                JSONArray chapterInfo = book.getJSONArray("chapter");
                bookListImg = book.getString("book_img");
                loadingLayout.setVisibility(View.GONE);
                tabWrap.setVisibility(View.VISIBLE);

                for (int i = 0; i < chapterInfo.length(); i++) {
                    JSONObject jo = chapterInfo.getJSONObject(i);
                    String chapterTitle = jo.getString("sub_subject");
                    String bookListRecommend = jo.getString("cnt_recom");
                    String bookListComment = jo.getString("cnt_comment");
                    String bookCid = jo.getString("cid");
                    String bookListNum = String.valueOf(i+1);

                    items.add(new DetailBookPageData(bookListNum, bookListImg, bookListRecommend, chapterTitle,bookCid, bookListComment));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d("Detail_Tab_1", "에러!"));

        queue.add(jsonRequest);
    }

    private void initAdapter() {
        adapter = new DetailBookListAdapter(items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}


