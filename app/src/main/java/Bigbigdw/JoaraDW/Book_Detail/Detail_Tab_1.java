package Bigbigdw.JoaraDW.Book_Detail;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import Bigbigdw.JoaraDW.BookList.Main_BookListAdapter_C;
import Bigbigdw.JoaraDW.Etc.HELPER;
import Bigbigdw.JoaraDW.Fragment_Fav.Main_BookList_Adapter_History;
import Bigbigdw.JoaraDW.Fragment_New.Book_Pagination;
import Bigbigdw.JoaraDW.JOARADW;
import Bigbigdw.JoaraDW.Main.Main_BookListData;
import Bigbigdw.JoaraDW.R;


public class Detail_Tab_1 extends Fragment {
    Detail_BookLIstAdapter BookChapterAdapter;
    private ArrayList<Detail_BookPageData> items = new ArrayList<>();
    String BookListImg;
    private RecyclerView recyclerView;
    String BOOKCODE, TOKEN, BookDetailURL;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.book_detail_tab_1, container, false);
        recyclerView = root.findViewById(R.id.BookDetail_1);

        JOARADW myApp = (JOARADW) getActivity().getApplicationContext();
        BOOKCODE = myApp.getBookCode();
        TOKEN = myApp.getToken();
        BookDetailURL = myApp.getAPI_URL();

        Log.d("BOOKCODE", BOOKCODE);
        Log.d("TOKEN", TOKEN);
        Log.d("BookDetailURL", BookDetailURL);

        populateData();
        initAdapter();

        return root;
    }

    void populateData() {
        try {
            FileReader fr = new FileReader(getActivity().getDataDir() + "/chapter.json");
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
            JSONObject BOOK = jsonObject.getJSONObject("book");
            BookListImg = BOOK.getString("book_img");
            JSONArray ChapterInfo = BOOK.getJSONArray("chapter");

            for (int i = ChapterInfo.length(); i >= 0 ; i--) {
                JSONObject jo = ChapterInfo.getJSONObject(ChapterInfo.length()-1);

                String ChapterTitle = jo.getString("sub_subject");
                String BookListRecommend = jo.getString("cnt_recom");
                String BookList_Num = String.valueOf(i);

                items.add(new Detail_BookPageData(BookList_Num,BookListImg,BookListRecommend,ChapterTitle));
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.d("USERINFO", "읽기 실패");
        }
    }

    private void initAdapter() {
        BookChapterAdapter = new Detail_BookLIstAdapter(items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(BookChapterAdapter);
    }
}


