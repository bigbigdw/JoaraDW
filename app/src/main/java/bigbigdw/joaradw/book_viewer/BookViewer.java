package bigbigdw.joaradw.book_viewer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bigbigdw.joaradw.JOARADW;
import bigbigdw.joaradw.base.BookBaseActivity;
import bigbigdw.joaradw.book_detail.BookDetail;
import bigbigdw.joaradw.book_detail.DetailBookPageData;
import bigbigdw.joaradw.etc.API;
import bigbigdw.joaradw.etc.HELPER;
import bigbigdw.joaradw.R;

public class BookViewer extends BookBaseActivity {
    private AppBarConfiguration appBarConfiguration;
    ViewerDrawerAdpater adpater;
    private final ArrayList<DetailBookPageData> items = new ArrayList<>();
    private RecyclerView recyclerView;
    String cid;
    String userToken;
    String bookCode;
    String cryptKeyURL;
    String sortNO;
    String bookDetailURL;
    TextView textTitle;
    TextView textWriter;
    TextView bookTitle;
    ImageView imgBook;
    View drawerContents;
    RequestQueue queue;
    LinearLayout tabWrap;
    LinearLayout loadinglayout;
    LinearLayout bookInfoWrap;
    LinearLayout setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_viewer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        cid = intent.getStringExtra("Cid");
        userToken = intent.getStringExtra("TOKEN");
        bookCode = intent.getStringExtra("BOOKCODE");
        sortNO = intent.getStringExtra("SORTNO");
        queue = Volley.newRequestQueue(this);

        JOARADW myApp = (JOARADW) getApplicationContext();
        myApp.setBookCode(bookCode);
        myApp.setCid(cid);
        myApp.setSortNo(sortNO);

        cryptKeyURL = HELPER.API + API.BOOK_CHAPTER_JOA + HELPER.ETC + "&token=" + userToken + "&category=0";

        DrawerLayout drawer = findViewById(R.id.viewer_drawer);
        NavigationView navigationView = findViewById(R.id.viewer_navigation);

        appBarConfiguration = new AppBarConfiguration.Builder(R.id.Fragment_Viewer).setOpenableLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_novel);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        drawerContents = navigationView.getHeaderView(0);
        loadinglayout = drawerContents.findViewById(R.id.LoadingLayout);
        textTitle = drawerContents.findViewById(R.id.Text_Title);
        textWriter = drawerContents.findViewById(R.id.Text_Writer);
        imgBook = drawerContents.findViewById(R.id.Img_Book);
        recyclerView = drawerContents.findViewById(R.id.BookChapterList);
        tabWrap = drawerContents.findViewById(R.id.TabWrap);
        bookInfoWrap = drawerContents.findViewById(R.id.BookInfoWrap);
        setting = findViewById(R.id.Setting);
        bookTitle = findViewById(R.id.BookTitle);

        setLayout();
    }

    public void setLayout(){
        bookDetailURL = HELPER.API + API.BOOK_DETAIL_JOA + HELPER.ETC + "&token=" + userToken + "&category=0&book_code=" + bookCode + "&promotion_code=";
        adpater = new ViewerDrawerAdpater(items);

        Handler handler = new Handler(Looper.myLooper());

        final JsonObjectRequest bookDetail = new JsonObjectRequest(Request.Method.GET, bookDetailURL, null, response -> {
            try {

                JSONObject book = response.getJSONObject("book");
                textTitle.setText(book.getString("subject"));
                bookTitle.setText(book.getString("subject"));
                textWriter.setText(book.getString("writer_name"));
                String bookImg = book.getString("book_img");
                Glide.with(this).load(book.getString("book_img"))
                        .into(imgBook);

                JSONArray chapterInfo = book.getJSONArray("chapter");

                for (int i = 0; i < chapterInfo.length(); i++) {
                    JSONObject jo = chapterInfo.getJSONObject(i);
                    String chapterTitle = jo.getString("sub_subject");
                    String bookListRecommend = jo.getString("cnt_recom");
                    String bookListComment = jo.getString("cnt_comment");
                    String bookcid = jo.getString("cid");
                    String bookListNum = String.valueOf(i + 1);

                    items.add(new DetailBookPageData(bookListNum, bookImg, bookListRecommend, chapterTitle, bookcid, bookListComment));
                }

                handler.postDelayed(() -> {
                    loadinglayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }, 1800);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d("Book_Pagination", "에러!"));
        queue.add(bookDetail);
        initAdapter();

        bookInfoWrap.setOnClickListener(v -> {
            Intent bookInfoIntent = new Intent(getApplicationContext(), BookDetail.class);
            bookInfoIntent.putExtra("BookCode", String.format("%s", bookCode));
            bookInfoIntent.putExtra("TOKEN", String.format("%s", userToken));
            bookInfoIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(bookInfoIntent);
        });

        setting.setOnClickListener(v -> {
            Intent settingIntent = new Intent(getApplicationContext(), ViewerSetting.class);
            startActivityIfNeeded(settingIntent, 0);
            finish();
        });
    }

    private void initAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adpater);
        adpater.notifyDataSetChanged();

        adpater.setOnItemClickListener((v, position, value) -> {
            DetailBookPageData item = adpater.getItem(position);
            adapterListenerBookViewer(item, userToken, bookCode);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_novel);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.viewer_menu, menu);
        return true;
    }
}
