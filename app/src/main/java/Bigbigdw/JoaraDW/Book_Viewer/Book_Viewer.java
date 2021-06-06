package Bigbigdw.JoaraDW.Book_Viewer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Bigbigdw.JoaraDW.Book_Detail.Book_Detail;
import Bigbigdw.JoaraDW.Book_Detail.Detail_BookPageData;
import Bigbigdw.JoaraDW.Etc.HELPER;
import Bigbigdw.JoaraDW.Login.Login_Main;
import Bigbigdw.JoaraDW.R;

public class Book_Viewer extends AppCompatActivity {
    private AppBarConfiguration AppBarConfiguration;
    Viewer_DrawerAdpater Adapter;
    private final ArrayList<Detail_BookPageData> items = new ArrayList<>();
    private RecyclerView recyclerView;
    String CID, TOKEN, BookCode, CryptKey_URL, SortNO, BookDetailURL;
    TextView Text_Title, Text_Writer, Text_Intro;
    ImageView Img_Book;
    View drawerContents;
    RequestQueue queue;
    LinearLayout TabWrap, LoadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_viewer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        CID = intent.getStringExtra("Cid");
        TOKEN = intent.getStringExtra("TOKEN");
        BookCode = intent.getStringExtra("BOOKCODE");
        SortNO = intent.getStringExtra("SORTNO");
        queue = Volley.newRequestQueue(this);

        CryptKey_URL = HELPER.API + "/v1/book/chapter_valid.joa" + HELPER.ETC + "&token=" + TOKEN + "&category=0";


        DrawerLayout drawer = findViewById(R.id.viewer_drawer);
        NavigationView navigationView = findViewById(R.id.viewer_navigation);


        AppBarConfiguration = new AppBarConfiguration.Builder(R.id.Fragment_Viewer).setDrawerLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, AppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        drawerContents = navigationView.getHeaderView(0);
        LoadingLayout = drawerContents.findViewById(R.id.LoadingLayout);
        Text_Title = drawerContents.findViewById(R.id.Text_Title);
        Text_Writer = drawerContents.findViewById(R.id.Text_Writer);
        Text_Intro = drawerContents.findViewById(R.id.Text_Intro);
        Img_Book = drawerContents.findViewById(R.id.Img_Book);
        recyclerView = drawerContents.findViewById(R.id.BookChapterList);
        TabWrap = drawerContents.findViewById(R.id.TabWrap);

        BookDetailURL = HELPER.API + "/v1/book/detail.joa" + HELPER.ETC + "&token=" + TOKEN + "&category=0&book_code=" + BookCode + "&promotion_code=";
        Adapter = new Viewer_DrawerAdpater(items);

        Handler handler = new Handler();


        final JsonObjectRequest bookDetail = new JsonObjectRequest(Request.Method.GET, BookDetailURL, null, response -> {
            try {

                JSONObject BOOK = response.getJSONObject("book");
                Text_Title.setText(BOOK.getString("subject"));
                Text_Writer.setText(BOOK.getString("writer_name"));
                Text_Intro.setText(BOOK.getString("intro"));
                String BookImg = BOOK.getString("book_img");
                Glide.with(this).load(BOOK.getString("book_img"))
                        .into(Img_Book);

                JSONArray ChapterInfo = BOOK.getJSONArray("chapter");

                for (int i = 0; i < ChapterInfo.length(); i++) {
                    JSONObject jo = ChapterInfo.getJSONObject(i);
                    String ChapterTitle = jo.getString("sub_subject");
                    String BookListRecommend = jo.getString("cnt_recom");
                    String BookListComment = jo.getString("cnt_comment");
                    String BookCid = jo.getString("cid");
                    String BookList_Num = String.valueOf(i + 1);

                    items.add(new Detail_BookPageData(BookList_Num, BookImg, BookListRecommend, ChapterTitle, BookCid, BookListComment));
                }

                handler.postDelayed(() -> {
                    LoadingLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }, 1000);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d("Book_Pagination", "에러!"));
        queue.add(bookDetail);
        initAdapter();
    }

    private void initAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(Adapter);
        Adapter.notifyDataSetChanged();

        Adapter.setOnItemClickListener((v, position, Value) -> {
            Detail_BookPageData item = Adapter.getItem(position);
            Intent intentViewer = new Intent(this.getApplicationContext(), Book_Viewer.class);
            intentViewer.putExtra("Cid", String.format("%s", item.getCid()));
            intentViewer.putExtra("TOKEN", String.format("%s", TOKEN));
            intentViewer.putExtra("BOOKCODE", String.format("%s", BookCode));
            intentViewer.putExtra("SORTNO", String.format("%s", item.getBookListNum()));
            intentViewer.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intentViewer);
        });
    }

    public void onClickGoDetail(View v) {
        Intent intent = new Intent(getApplicationContext(), Book_Detail.class);
        intent.putExtra("BookCode", String.format("%s", BookCode));
        intent.putExtra("TOKEN", String.format("%s", TOKEN));
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void onClickViewerSetting(View v) {
        Intent intent = new Intent(getApplicationContext(), Viewer_Setting.class);
        startActivityIfNeeded(intent, 0);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, AppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.viewer_menu, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
