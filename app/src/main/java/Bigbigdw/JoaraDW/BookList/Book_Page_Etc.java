package Bigbigdw.JoaraDW.BookList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Bigbigdw.JoaraDW.Book_Detail.Book_Detail_Cover;
import Bigbigdw.JoaraDW.Config;
import Bigbigdw.JoaraDW.Etc.HELPER;
import Bigbigdw.JoaraDW.Fragment_New.Book_Pagination;
import Bigbigdw.JoaraDW.JOARADW;
import Bigbigdw.JoaraDW.Main.Main_BookListData;
import Bigbigdw.JoaraDW.R;

public class Book_Page_Etc extends AppCompatActivity {

    private Main_BookListAdapter_C Adapter;
    private Main_BookListAdapter_Best BestAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Main_BookListData> items = new ArrayList<>();
    LinearLayout Wrap, Cover, Blank;
    String TOKEN = "";
    String Title, ETC_URL, API_URL;
    String TYPE = "";
    TextView BookTitle;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booklist_etc);

        BookTitle = findViewById(R.id.BookTitle);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Config.GETUSERINFO() != null) {
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

        Intent PageIntent = getIntent();
        Title = PageIntent.getStringExtra("Title");
        BookTitle.setText(Title);
        API_URL = PageIntent.getStringExtra("API_URL");
        ETC_URL = PageIntent.getStringExtra("ETC_URL");

        if(PageIntent.getStringExtra("TYPE") != null){
            TYPE = PageIntent.getStringExtra("TYPE");
            Log.d("TYPE", TYPE);
        } else {
            TYPE = "";
        }

        queue = Volley.newRequestQueue(this);

        recyclerView = findViewById(R.id.BookDetail);

        Wrap = findViewById(R.id.TabWrap);
        Cover = findViewById(R.id.LoadingLayout);
        Blank = findViewById(R.id.BlankLayout);

        Book_Pagination.populateData(API_URL, ETC_URL + "&page=1", queue, Wrap, items, Cover, Blank, TYPE);
        initAdapter();
//        Book_Pagination.ScrollListener(API_URL, queue, Wrap, items, Adapter, recyclerView, ETC_URL);

    }

    private void initAdapter() {
        if (TYPE.equals("BEST")) {
            Main_BookListAdapter_Best BestAdapter = new Main_BookListAdapter_Best(items);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            BestAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(BestAdapter);

            BestAdapter.setOnItemClicklistener((holder, view, position, Value) -> {
                Main_BookListData item = BestAdapter.getItem(position);
                if (Value.equals("FAV")) {
                    Book_Pagination.FavToggle(queue, item.getBookCode(), TOKEN);
                } else if (Value.equals("BookDetail")) {
                    Intent intent = new Intent(this.getApplicationContext(), Book_Detail_Cover.class);
                    intent.putExtra("BookCode", String.format("%s", item.getBookCode()));
                    intent.putExtra("TOKEN", String.format("%s", TOKEN));
                    startActivity(intent);
                }
            });
        } else {
            Main_BookListAdapter_C Adapter = new Main_BookListAdapter_C(items);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            Adapter.notifyDataSetChanged();
            recyclerView.setAdapter(Adapter);

            Adapter.setOnItemClicklistener((holder, view, position, Value) -> {
                Main_BookListData item = Adapter.getItem(position);
                if (Value.equals("FAV")) {
                    Book_Pagination.FavToggle(queue, item.getBookCode(), TOKEN);
                } else if (Value.equals("BookDetail")) {
                    Intent intent = new Intent(this.getApplicationContext(), Book_Detail_Cover.class);
                    intent.putExtra("BookCode", String.format("%s", item.getBookCode()));
                    intent.putExtra("TOKEN", String.format("%s", TOKEN));
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
