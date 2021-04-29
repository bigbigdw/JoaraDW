package Bigbigdw.JoaraDW.Book_Detail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import Bigbigdw.JoaraDW.Etc.HELPER;
import Bigbigdw.JoaraDW.R;

public class Book_Detail extends AppCompatActivity {

    private RequestQueue queue;
    String TOKEN = "";
    String BookDetailURL;
    TextView BookTitle, BookType, BookTitleUnder, BookWriter, Bar, Category, BookRead, BookRecommend, BookFav, BookComment, BookIntro;
    ImageView BookCoverHeader;
    AppBarLayout BookAppBar;
    FloatingActionButton BookDetailOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookdetail);
        queue = Volley.newRequestQueue(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String BookCode = intent.getStringExtra("BookCode");

        TOKEN = intent.getStringExtra("TOKEN");
        Log.d("Book_Detail", TOKEN);

        BookDetailURL = HELPER.API + "/v1/book/detail.joa" + HELPER.ETC + TOKEN + "&category=0&book_code=" + BookCode + "&promotion_code=";

        Log.d("BookDetailURL", BookDetailURL);

        BookTitle = findViewById(R.id.BookTitle);
        BookCoverHeader = findViewById(R.id.BookCoverHeader);
        BookType = findViewById(R.id.BookType);
        BookTitleUnder = findViewById(R.id.BookTitleUnder);
        BookWriter = findViewById(R.id.BookWriter);
        Bar = findViewById(R.id.Bar);
        Category = findViewById(R.id.Category);
        BookRead = findViewById(R.id.BookRead);
        BookRecommend = findViewById(R.id.BookRecommend);
        BookFav = findViewById(R.id.BookFav);
        BookComment = findViewById(R.id.BookComment);
        BookIntro = findViewById(R.id.BookIntro);
        BookAppBar = findViewById(R.id.BookAppBar);
        BookDetailOption = findViewById(R.id.BookDetailOption);

        BookAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (Math.abs(verticalOffset)-appBarLayout.getTotalScrollRange() == 0)
                {
                    BookDetailOption.setVisibility(View.VISIBLE);
                }
                else
                {
                    BookDetailOption.setVisibility(View.GONE);
                }
            }
        });

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, BookDetailURL, null, response -> {
            System.out.println(response);
            try {
                JSONObject book = response.getJSONObject("book");

                BookTitle.setText(book.getString("subject"));
                Glide.with(this).load(book.getString("book_img"))
                        .into(BookCoverHeader);

                if (book.getString("is_nobless").equals("TRUE") && book.getString("is_adult").equals("FALSE")) {
                    BookType.setText(R.string.NOBLESS);
                    BookType.setTextColor(0xAAa5c500);
                    Bar.setTextColor(0xAAa5c500);
                    Category.setTextColor(0xAAa5c500);
                } else if (book.getString("is_premium").equals("TRUE") && book.getString("is_adult").equals("FALSE")) {
                    BookType.setText(R.string.PREMIUM);
                    BookType.setTextColor(0xAA4971EF);
                    Bar.setTextColor(0xAA4971EF);
                    Category.setTextColor(0xAA4971EF);
                } else if (book.getString("is_finish").equals("TRUE") && book.getString("is_adult").equals("FALSE")) {
                    BookType.setText(R.string.FINISH);
                    BookType.setTextColor(0xAA767676);
                    Bar.setTextColor(0xAA767676);
                    Category.setTextColor(0xAA767676);
                } else if (book.getString("is_nobless").equals("TRUE") && book.getString("is_adult").equals("TRUE")) {
                    BookType.setText(R.string.ADULT_NOBLESS);
                    BookType.setTextColor(0xAAF44336);
                    Bar.setTextColor(0xAAF44336);
                    Category.setTextColor(0xAAF44336);
                } else if (book.getString("is_premium").equals("TRUE") && book.getString("is_adult").equals("TRUE")) {
                    BookType.setText(R.string.ADULT_PREMIUM);
                    BookType.setTextColor(0xAA4971EF);
                    Bar.setTextColor(0xAA4971EF);
                    Category.setTextColor(0xAA4971EF);
                } else if (book.getString("is_finish").equals("TRUE") && book.getString("is_adult").equals("TRUE")) {
                    BookType.setText(R.string.ADULT_FINISH);
                    BookType.setTextColor(0xAA767676);
                    Bar.setTextColor(0xAA767676);
                    Category.setTextColor(0xAA767676);
                }

                BookTitleUnder.setText(book.getString("subject"));
                BookWriter.setText(book.getString("writer_name"));
                Category.setText(book.getString("category_ko_name"));
                BookRead.setText(book.getString("cnt_page_read"));
                BookRecommend.setText(book.getString("cnt_recom"));
                BookFav.setText(book.getString("cnt_favorite"));
                BookComment.setText(book.getString("cnt_total_comment"));
                BookIntro.setText(book.getString("intro"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("Book_Detail", "완료!");
        }, error -> Log.d("Book_Detail", "에러!"));

        queue.add(jsonRequest);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.bookdetail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
