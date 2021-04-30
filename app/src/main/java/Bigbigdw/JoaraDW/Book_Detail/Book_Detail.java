package Bigbigdw.JoaraDW.Book_Detail;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import Bigbigdw.JoaraDW.Etc.HELPER;
import Bigbigdw.JoaraDW.Main.Main;
import Bigbigdw.JoaraDW.R;

public class Book_Detail extends AppCompatActivity {

    private RequestQueue queue;
    String TOKEN = "";
    String BookDetailURL;
    TextView BookTitle, BookType, BookTitleUnder, BookWriter, Bar, Category, BookRead, BookRecommend, BookFav, BookComment, BookIntro;
    TextView BookTypeBody, CategoryBody, BookTitleBody, BookWriterBody, BookReadBody, BookRecommendBody, BookFavBody, BookCommentBody, BarBody;
    ImageView BookCoverHeader, BookReadImg, BookRecommedImg, BookFavImg, BookCommentImg;
    AppBarLayout BookAppBar;
    FloatingActionButton BookDetailOption;
    LinearLayout BookLabel, LoadingLayout;
    Button BookDetailHeader2;

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
        BookLabel = findViewById(R.id.BookLabel);
        BookDetailHeader2 = findViewById(R.id.BookDetailHeader2);

        BookTypeBody = findViewById(R.id.BookTypeBody);
        CategoryBody = findViewById(R.id.CategoryBody);
        BookLabel = findViewById(R.id.BookLabel);
        BookTitleBody = findViewById(R.id.BookTitleBody);
        BookWriterBody = findViewById(R.id.BookWriterBody);
        BookReadBody = findViewById(R.id.BookReadBody);
        BookRecommendBody = findViewById(R.id.BookRecommendBody);
        BookFavBody = findViewById(R.id.BookFavBody);
        BookCommentBody = findViewById(R.id.BookCommentBody);
        BarBody = findViewById(R.id.BarBody);

        BookReadImg = findViewById(R.id.BookReadImg);
        BookRecommedImg = findViewById(R.id.BookRecommedImg);
        BookFavImg = findViewById(R.id.BookFavImg);
        BookCommentImg = findViewById(R.id.BookCommentImg);
        LoadingLayout= findViewById(R.id.LoadingLayout);

        BookAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (Math.abs(verticalOffset)-appBarLayout.getTotalScrollRange() == 0)
                {
                    BookDetailOption.setVisibility(View.VISIBLE);
                    BookDetailOption.animate().alpha(1.0f);
                }
                else
                {
                    BookDetailOption.setVisibility(View.GONE);
                    BookDetailOption.animate().alpha(0.0f);
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
                    BookTypeBody.setText(R.string.NOBLESS);
                    BookLabel.setBackgroundColor(0xAAa5c500);
                    BookTypeBody.setTextColor(0xAAa5c500);
                    CategoryBody.setTextColor(0xAAa5c500);
                    BarBody.setTextColor(0xAAa5c500);
                } else if (book.getString("is_premium").equals("TRUE") && book.getString("is_adult").equals("FALSE")) {
                    BookType.setText(R.string.PREMIUM);
                    BookTypeBody.setText(R.string.PREMIUM);
                    BookLabel.setBackgroundColor(0xAA4971EF);
                    BookTypeBody.setTextColor(0xAA4971EF);
                    CategoryBody.setTextColor(0xAA4971EF);
                    BarBody.setTextColor(0xAA4971EF);
                } else if (book.getString("is_finish").equals("TRUE") && book.getString("is_adult").equals("FALSE")) {
                    BookType.setText(R.string.FINISH);
                    BookTypeBody.setText(R.string.FINISH);
                    BookLabel.setBackgroundColor(0xAAa5c500);
                    BookTypeBody.setTextColor(0xAAa5c500);
                    CategoryBody.setTextColor(0xAAa5c500);
                    BarBody.setTextColor(0xAAa5c500);
                } else if (book.getString("is_nobless").equals("TRUE") && book.getString("is_adult").equals("TRUE")) {
                    BookType.setText(R.string.ADULT_NOBLESS);
                    BookTypeBody.setText(R.string.ADULT_NOBLESS);
                    BookLabel.setBackgroundColor(0xAAF44336);
                    BookTypeBody.setTextColor(0xAAF44336);
                    CategoryBody.setTextColor(0xAAF44336);
                    BarBody.setTextColor(0xAAF44336);
                } else if (book.getString("is_premium").equals("TRUE") && book.getString("is_adult").equals("TRUE")) {
                    BookType.setText(R.string.ADULT_PREMIUM);
                    BookTypeBody.setText(R.string.ADULT_PREMIUM);
                    BookLabel.setBackgroundColor(0xAAF44336);
                    BookTypeBody.setTextColor(0xAA4971EF);
                    CategoryBody.setTextColor(0xAA4971EF);
                    BarBody.setTextColor(0xAA4971EF);
                } else if (book.getString("is_finish").equals("TRUE") && book.getString("is_adult").equals("TRUE")) {
                    BookType.setText(R.string.ADULT_FINISH);
                    BookTypeBody.setText(R.string.ADULT_FINISH);
                    BookLabel.setBackgroundColor(0xAAF44336);
                    BookTypeBody.setTextColor(0xAA767676);
                    CategoryBody.setTextColor(0xAA767676);
                    BarBody.setTextColor(0xAA767676);
                } else {
                    BookType.setText(R.string.FREE);
                    BookTypeBody.setText(R.string.FREE);
                }

                BookTitleUnder.setText(book.getString("subject"));
                BookTitleBody.setText(book.getString("subject"));
                BookWriter.setText(book.getString("writer_name"));
                BookWriterBody.setText(book.getString("writer_name"));
                Category.setText(book.getString("category_ko_name"));
                CategoryBody.setText(book.getString("category_ko_name"));
                BookRead.setText(book.getString("cnt_page_read"));
                BookReadBody.setText(book.getString("cnt_page_read"));
                BookRecommend.setText(book.getString("cnt_recom"));
                BookRecommendBody.setText(book.getString("cnt_recom"));
                BookFav.setText(book.getString("cnt_favorite"));
                BookFavBody.setText(book.getString("cnt_favorite"));
                BookComment.setText(book.getString("cnt_total_comment"));
                BookCommentBody.setText(book.getString("cnt_total_comment"));
                BookIntro.setText(book.getString("intro"));

                Bar.setVisibility(View.VISIBLE);
                BookReadImg.setVisibility(View.VISIBLE);
                BookRecommedImg.setVisibility(View.VISIBLE);
                BookFavImg.setVisibility(View.VISIBLE);
                BookCommentImg.setVisibility(View.VISIBLE);
                LoadingLayout.setVisibility(View.GONE);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("Book_Detail", "완료!");
        }, error -> Log.d("Book_Detail", "에러!"));

        queue.add(jsonRequest);
    }

    public void BookDetailHeaderOff(View v) {
        BookAppBar.setExpanded(false);
    }

    public void BookDetailHeaderOn(View v) {
        BookAppBar.setExpanded(true);
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
