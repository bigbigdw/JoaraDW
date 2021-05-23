package Bigbigdw.JoaraDW.Book_Detail;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import Bigbigdw.JoaraDW.Config;
import Bigbigdw.JoaraDW.Etc.HELPER;
import Bigbigdw.JoaraDW.Book_Pagination;
import Bigbigdw.JoaraDW.R;


public class Book_Detail_Cover extends AppCompatActivity {

    private RequestQueue queue;
    String STATUS = "", TOKEN = "", BookDetailURL, BookCode = "", BookTitleText = "";
    TextView BookType, BookTitleUnder, BookWriter, Bar, Category, BookRead, BookRecommend, BookFav, BookComment, BookIntro;
    ImageView BookReadImg, BookRecommedImg, BookFavImg, BookCommentImg, BookCoverHeader;
    AppBarLayout BookAppBar;
    LinearLayout BookLabel, LoadingLayout, BookCoverWrap;
    Button BookDetailHeader2;
    JSONObject Book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail_cover);
        queue = Volley.newRequestQueue(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        BookCode = intent.getStringExtra("BookCode");
        TOKEN = intent.getStringExtra("TOKEN");
        BookDetailURL = HELPER.API + "/v1/book/detail.joa" + HELPER.ETC + "&token=" + TOKEN + "&category=0&book_code=" + BookCode + "&promotion_code=";

        BookCoverHeader = findViewById(R.id.BookCoverHeader);
        BookType = findViewById(R.id.BookType);
        BookWriter = findViewById(R.id.BookWriter);
        Bar = findViewById(R.id.Bar);
        Category = findViewById(R.id.Category);
        BookRead = findViewById(R.id.BookRead);
        BookRecommend = findViewById(R.id.BookRecommend);
        BookFav = findViewById(R.id.BookFav);
        BookComment = findViewById(R.id.BookComment);
        BookIntro = findViewById(R.id.BookIntro);
        BookAppBar = findViewById(R.id.BookAppBar);
        BookLabel = findViewById(R.id.BookLabel);
        LoadingLayout = findViewById(R.id.LoadingLayout);
        BookTitleUnder = findViewById(R.id.BookTitleUnder);
        BookReadImg = findViewById(R.id.BookReadImg);
        BookRecommedImg = findViewById(R.id.BookRecommedImg);
        BookFavImg = findViewById(R.id.BookFavImg);
        BookCommentImg = findViewById(R.id.BookCommentImg);
        BookDetailHeader2 = findViewById(R.id.BookDetailHeader2);
        LoadingLayout = findViewById(R.id.LoadingLayout);
        BookCoverWrap = findViewById(R.id.BookCoverWrap);

        Bundle arguments = new Bundle();
        arguments.putString("BookCode", BookCode);

        Detail_Tab_1 fragment = new Detail_Tab_1();
        fragment.setArguments(arguments);
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
                .commit();

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, BookDetailURL, null, response -> {
            try {
                Book = response.getJSONObject("book");

                Glide.with(this).load(Book.getString("book_img"))
                        .into(BookCoverHeader);

                if (Book.getString("is_nobless").equals("TRUE") && Book.getString("is_adult").equals("FALSE")) {
                    TextColor(R.string.NOBLESS, 0xAAa5c500);
                } else if (Book.getString("is_premium").equals("TRUE") && Book.getString("is_adult").equals("FALSE")) {
                    TextColor(R.string.PREMIUM, 0xAA4971EF);
                } else if (Book.getString("is_finish").equals("TRUE") && Book.getString("is_adult").equals("FALSE")) {
                    TextColor(R.string.FINISH, 0xAAa5c500);
                } else if (Book.getString("is_nobless").equals("TRUE") && Book.getString("is_adult").equals("TRUE")) {
                    TextColor(R.string.ADULT_NOBLESS, 0xAAF44336);
                } else if (Book.getString("is_premium").equals("TRUE") && Book.getString("is_adult").equals("TRUE")) {
                    TextColor(R.string.ADULT_PREMIUM, 0xAAF44336);
                } else if (Book.getString("is_finish").equals("TRUE") && Book.getString("is_adult").equals("TRUE")) {
                    TextColor(R.string.ADULT_FINISH, 0xAAF44336);
                } else {
                    BookType.setText(R.string.FREE);
                }

                String isFavorate = Book.getString("is_favorite");

                BookTitleUnder.setText(Book.getString("subject"));
                BookWriter.setText(Book.getString("writer_name"));
                Category.setText(Book.getString("category_ko_name"));
                BookRead.setText(Book.getString("cnt_page_read"));
                BookRecommend.setText(Book.getString("cnt_recom"));
                BookFav.setText(Book.getString("cnt_favorite"));
                BookComment.setText(Book.getString("cnt_total_comment"));
                BookIntro.setText(Book.getString("intro"));
                BookTitleText = Book.getString("subject");

                Bar.setVisibility(View.VISIBLE);
                BookReadImg.setVisibility(View.VISIBLE);
                BookRecommedImg.setVisibility(View.VISIBLE);
                BookFavImg.setVisibility(View.VISIBLE);
                BookCommentImg.setVisibility(View.VISIBLE);
                LoadingLayout.setVisibility(View.GONE);

                BookCoverWrap.setBackgroundColor(Color.parseColor("#80000000"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("Book_Detail", "완료!");
        }, error -> Log.d("Book_Detail", "에러!"));

        queue.add(jsonRequest);

        if (Config.GETUSERINFO() != null) {
            JSONObject GETUSERINFO = Config.GETUSERINFO();
            JSONObject UserInfo;
            try {
                UserInfo = GETUSERINFO.getJSONObject("user");
                TOKEN = UserInfo.getString("token");
                STATUS = GETUSERINFO.getString("status");

            } catch (JSONException e) {
                e.printStackTrace();
                TOKEN = "";
            }
        }
    }

    public void TextColor(int Text, int Color) {
        BookType.setText(Text);
        BookLabel.setBackgroundColor(Color);
    }

    public void BookDetailHeaderOff(View v) {
        Intent intent = new Intent(getApplicationContext(), Book_Detail.class);
        intent.putExtra("BookCode",String.format("%s", BookCode));
        intent.putExtra("TOKEN",String.format("%s", TOKEN));
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.bookdetail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
